import java.util.*;

// Aggregate Gift Card Data — YOUR attempt, clean slate.
// Implement the methods below, then copy them into GiftCards.java
// (replacing the TODOs) to run the built-in tests.
public class GiftCardsAttempt {

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

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, List<GiftCard>> cardsByUser = new HashMap<>();
    private int seq = 0;

    public User createUser(String name) {
        User u = new User("u" + (++seq), name);
        users.put(u.id(), u);
        cardsByUser.put(u.id(), new ArrayList<>());
        return u;
    }

    public GiftCard issueGiftCard(String userId, double amount) {
        GiftCard c = new GiftCard("g" + (++seq), userId, amount);
        cardsByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(c);
        return c;
    }

    public List<GiftCard> cardsOf(String userId) {
        return List.copyOf(cardsByUser.getOrDefault(userId, List.of()));
    }

    public double totalBalance(String userId) {
        return 0.0;
    }

    public double totalSpent(String userId) {
        return 0.0;
    }

    public int activeCardCount(String userId) {
        return 0;
    }

    public boolean pay(String userId, double amount) {
        return false;
    }

    public double totalRemainingAllUsers() {
        return 0.0;
    }
}
