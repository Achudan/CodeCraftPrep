import java.util.*;

// Aggregate Gift Card Data — REFERENCE SOLUTION (all tests pass).
// Run: javac GiftCardsSolution.java && java GiftCardsSolution
// The exercise version is GiftCards.java (same tests, TODOs for you).
public class GiftCardsSolution {

    public record User(String id, String name) {}

    public static final class GiftCard {
        public final String id;
        public final String userId;
        public final double initialAmount;
        public double balance;

        GiftCard(String id, String userId, double amount) {
            this.id = id;
            this.userId = userId;
            this.initialAmount = amount;
            this.balance = amount;
        }
    }

    // ---- In-memory store (scaffolding is done; this is not the drill) ----
    private final Map<String, User> users = new HashMap<>();
    // Per-user cards kept in issued order — payment order depends on this.
    private final Map<String, List<GiftCard>> cardsByUser = new HashMap<>();
    private int seq = 0;

    public User createUser(String name) {
        User u = new User("u" + (++seq), name);
        users.put(u.id(), u);
        cardsByUser.put(u.id(), new ArrayList<>());
        return u;
    }

    // Issues a new card for the user. Assume amount > 0 (state that out loud).
    public GiftCard issueGiftCard(String userId, double amount) {
        GiftCard c = new GiftCard("g" + (++seq), userId, amount);
        cardsByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(c);
        return c;
    }

    public List<GiftCard> cardsOf(String userId) {
        return List.copyOf(cardsByUser.getOrDefault(userId, List.of()));
    }

    // ---- The drill ----

    // Aggregated remaining balance across all of the user's cards.
    // Unknown user -> 0.0.
    public double totalBalance(String userId) {
        double sum = 0.0;
        for (GiftCard c : cardsOf(userId)) sum += c.balance;
        return sum;
    }

    // Aggregated amount spent so far = sum(initialAmount - balance).
    // Unknown user -> 0.0.
    public double totalSpent(String userId) {
        double sum = 0.0;
        for (GiftCard c : cardsOf(userId)) sum += c.initialAmount - c.balance;
        return sum;
    }

    // How many of the user's cards still hold money (balance > 0).
    public int activeCardCount(String userId) {
        int n = 0;
        for (GiftCard c : cardsOf(userId)) if (c.balance > 0) n++;
        return n;
    }

    // Pays `amount` for the user, drawing from cards in issued order (FIFO):
    // drain the earliest card first, then the next, until covered.
    // Stated rule (say it before coding): all-or-nothing — if the user's total
    // balance is less than `amount` (or amount <= 0), return false and leave
    // every card balance unchanged.
    public boolean pay(String userId, double amount) {
        // All-or-nothing: check the total FIRST, before touching any card.
        if (amount <= 0 || totalBalance(userId) < amount) return false;
        double remaining = amount;
        for (GiftCard c : cardsByUser.getOrDefault(userId, List.of())) {
            if (remaining <= 0) break;
            double take = Math.min(c.balance, remaining);
            c.balance -= take;
            remaining -= take;
        }
        return true;
    }

    // System-wide aggregate: remaining balance across every user.
    public double totalRemainingAllUsers() {
        double sum = 0.0;
        for (String userId : users.keySet()) sum += totalBalance(userId);
        return sum;
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        GiftCardsSolution svc = new GiftCardsSolution();
        User achu = svc.createUser("Achu");
        User sam = svc.createUser("Sam");

        check("unknown user balance -> 0.0", svc.totalBalance("nope"), 0.0);
        check("no cards yet -> 0.0", svc.totalBalance(achu.id()), 0.0);

        GiftCard g1 = svc.issueGiftCard(achu.id(), 50.0);
        GiftCard g2 = svc.issueGiftCard(achu.id(), 30.0);
        GiftCard g3 = svc.issueGiftCard(sam.id(), 20.0);

        check("achu total 50+30 -> 80.0", svc.totalBalance(achu.id()), 80.0);
        check("sam total -> 20.0 (isolated)", svc.totalBalance(sam.id()), 20.0);
        check("achu active cards -> 2", svc.activeCardCount(achu.id()), 2);
        check("nothing spent yet -> 0.0", svc.totalSpent(achu.id()), 0.0);
        check("system remaining 80+20 -> 100.0", svc.totalRemainingAllUsers(), 100.0);

        // Pay $60: drains g1 ($50) fully, then takes $10 from g2.
        check("pay 60 -> true", svc.pay(achu.id(), 60.0), true);
        check("g1 drained -> 0.0", g1.balance, 0.0);
        check("g2 left -> 20.0", g2.balance, 20.0);
        check("achu total now -> 20.0", svc.totalBalance(achu.id()), 20.0);
        check("achu spent -> 60.0", svc.totalSpent(achu.id()), 60.0);
        check("achu active cards -> 1", svc.activeCardCount(achu.id()), 1);
        check("sam untouched -> 20.0", g3.balance, 20.0);

        // All-or-nothing: $25 > $20 remaining, so nothing may change.
        check("pay 25 > balance -> false", svc.pay(achu.id(), 25.0), false);
        check("balance unchanged -> 20.0", svc.totalBalance(achu.id()), 20.0);
        check("g2 unchanged -> 20.0", g2.balance, 20.0);

        check("pay 0 -> false", svc.pay(achu.id(), 0.0), false);
        check("pay exact remaining 20 -> true", svc.pay(achu.id(), 20.0), true);
        check("achu total now -> 0.0", svc.totalBalance(achu.id()), 0.0);
        check("achu active cards -> 0", svc.activeCardCount(achu.id()), 0);
        check("system remaining -> 20.0 (sam only)", svc.totalRemainingAllUsers(), 20.0);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop.");
    }
}
