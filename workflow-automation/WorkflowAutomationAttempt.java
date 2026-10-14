import java.util.*;

// Workflow Automation Engine — YOUR attempt, clean slate.
// Implement the methods below, then copy them into WorkflowAutomation.java
// (replacing the TODOs) to run the built-in tests.
public class WorkflowAutomationAttempt {

    public enum OrderStatus { OPEN, DELIVERED, CANCELLED }

    public record User(String id, String name) {}
    public record Restaurant(String id, String name) {}

    public static final class Order {
        public final String id;
        public final String userId;
        public final String restaurantId;
        public final double amount;
        public OrderStatus status;

        Order(String id, String userId, String restaurantId, double amount) {
            this.id = id;
            this.userId = userId;
            this.restaurantId = restaurantId;
            this.amount = amount;
            this.status = OrderStatus.OPEN;
        }
    }

    public record RefundContext(int lateMinutes, boolean prepStarted) {}

    public interface RefundPolicy {
        int refundPercent(Order order, RefundContext ctx);
    }

    public static final class LateDeliveryRefund implements RefundPolicy {
        @Override public int refundPercent(Order order, RefundContext ctx) {
            return 0;
        }
    }

    public static final class CancelBeforePrepRefund implements RefundPolicy {
        @Override public int refundPercent(Order order, RefundContext ctx) {
            return 0;
        }
    }

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Restaurant> restaurants = new HashMap<>();
    private final Map<String, Order> orders = new HashMap<>();
    private int seq = 0;

    public User createUser(String name) {
        User u = new User("u" + (++seq), name);
        users.put(u.id(), u);
        return u;
    }

    public Restaurant createRestaurant(String name) {
        Restaurant r = new Restaurant("r" + (++seq), name);
        restaurants.put(r.id(), r);
        return r;
    }

    public Order createOrder(String userId, String restaurantId, double amount) {
        Order o = new Order("o" + (++seq), userId, restaurantId, amount);
        orders.put(o.id, o);
        return o;
    }

    public Optional<Order> getOrder(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public boolean updateStatus(String orderId, OrderStatus newStatus) {
        return false;
    }

    public static double refundAmount(Order order, RefundPolicy policy, RefundContext ctx) {
        return 0.0;
    }
}
