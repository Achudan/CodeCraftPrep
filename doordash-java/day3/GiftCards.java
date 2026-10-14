import java.util.*;

// DoorDash CodeCraft Day 3 — Aggregate Gift Card Data (Java starter)
// Run: javac GiftCards.java && java GiftCards
// Implement the TODOs. Tests in main() should all pass when you're done.
// Focus of the drill: (1) clean entity/manager design, (2) aggregates per user,
// (3) one payment that can span multiple cards, in issued order (FIFO).
// Prep reconstruction from the locked plan notes, not a verbatim CodeZym spec —
// confirm the real method signatures at the CodeZym link before the interview.
public class GiftCards {

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
        // TODO: sum balances over cardsOf(userId)
        return 0.0;
    }

    // Aggregated amount spent so far = sum(initialAmount - balance).
    // Unknown user -> 0.0.
    public double totalSpent(String userId) {
        // TODO: sum (initialAmount - balance) over the user's cards
        return 0.0;
    }

    // How many of the user's cards still hold money (balance > 0).
    public int activeCardCount(String userId) {
        // TODO: count cards with balance > 0
        return 0;
    }

    // Pays `amount` for the user, drawing from cards in issued order (FIFO):
    // drain the earliest card first, then the next, until covered.
    // Stated rule (say it before coding): all-or-nothing — if the user's total
    // balance is less than `amount` (or amount <= 0), return false and leave
    // every card balance unchanged.
    public boolean pay(String userId, double amount) {
        // TODO: check total first, then sweep cards in order deducting
        // min(card.balance, remaining) until remaining is 0.
        return false;
    }

    // System-wide aggregate: remaining balance across every user.
    public double totalRemainingAllUsers() {
        // TODO: sum totalBalance over all known users
        return 0.0;
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        GiftCards svc = new GiftCards();
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
