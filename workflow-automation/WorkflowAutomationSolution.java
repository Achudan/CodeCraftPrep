import java.util.*;

// Workflow Automation Engine — REFERENCE SOLUTION (all tests pass).
// Run: javac WorkflowAutomationSolution.java && java WorkflowAutomationSolution
// The exercise version is WorkflowAutomation.java (same tests, TODOs for you).
public class WorkflowAutomationSolution {

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

    // Context a refund rule may need. Say your thresholds out loud before coding.
    public record RefundContext(int lateMinutes, boolean prepStarted) {}

    public interface RefundPolicy {
        int refundPercent(Order order, RefundContext ctx);
    }

    // Part 2a: late delivery -> 0% / 50% / 100% by how late.
    // Suggested thresholds to state out loud: <=10 min -> 0, 11-30 -> 50, >30 -> 100.
    public static final class LateDeliveryRefund implements RefundPolicy {
        @Override public int refundPercent(Order order, RefundContext ctx) {
            int late = ctx.lateMinutes();
            if (late <= 10) return 0;
            if (late <= 30) return 50;
            return 100;
        }
    }

    // Part 2b: cancel before prep started -> 95%.
    public static final class CancelBeforePrepRefund implements RefundPolicy {
        @Override public int refundPercent(Order order, RefundContext ctx) {
            return ctx.prepStarted() ? 0 : 95;
        }
    }

    // ---- In-memory store (scaffolding is done; this is not the drill) ----
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

    // ---- The drill ----
    // Returns true if the transition was applied.
    // Rules: only OPEN orders can move; DELIVERED/CANCELLED are closed and
    // ignore further status updates; unknown id -> false, no crash.
    public boolean updateStatus(String orderId, OrderStatus newStatus) {
        Order o = orders.get(orderId);
        if (o == null || o.status != OrderStatus.OPEN) return false;
        o.status = newStatus;
        return true;
    }

    // Refund amount = order.amount * policy percent / 100.0
    public static double refundAmount(Order order, RefundPolicy policy, RefundContext ctx) {
        return order.amount * policy.refundPercent(order, ctx) / 100.0;
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        WorkflowAutomationSolution svc = new WorkflowAutomationSolution();
        User u = svc.createUser("Achu");
        Restaurant r = svc.createRestaurant("Testaurant");

        Order o1 = svc.createOrder(u.id(), r.id(), 200.0);
        check("new order is OPEN", o1.status, OrderStatus.OPEN);
        check("deliver OPEN -> true", svc.updateStatus(o1.id, OrderStatus.DELIVERED), true);
        check("o1 now DELIVERED", o1.status, OrderStatus.DELIVERED);
        check("update DELIVERED again -> false (closed)", svc.updateStatus(o1.id, OrderStatus.CANCELLED), false);
        check("o1 still DELIVERED", o1.status, OrderStatus.DELIVERED);

        Order o2 = svc.createOrder(u.id(), r.id(), 100.0);
        check("cancel OPEN -> true", svc.updateStatus(o2.id, OrderStatus.CANCELLED), true);
        check("o2 now CANCELLED", o2.status, OrderStatus.CANCELLED);
        check("update CANCELLED again -> false (closed)", svc.updateStatus(o2.id, OrderStatus.DELIVERED), false);

        check("unknown order id -> false", svc.updateStatus("nope", OrderStatus.DELIVERED), false);

        RefundPolicy late = new LateDeliveryRefund();
        check("late 5 min -> 0%", late.refundPercent(o1, new RefundContext(5, true)), 0);
        check("late 20 min -> 50%", late.refundPercent(o1, new RefundContext(20, true)), 50);
        check("late 45 min -> 100%", late.refundPercent(o1, new RefundContext(45, true)), 100);
        check("refund $200 @ 50% -> 100.0", refundAmount(o1, late, new RefundContext(20, true)), 100.0);

        RefundPolicy cancel = new CancelBeforePrepRefund();
        check("cancel before prep -> 95%", cancel.refundPercent(o2, new RefundContext(0, false)), 95);
        check("cancel after prep -> 0% (stated fallback)", cancel.refundPercent(o2, new RefundContext(0, true)), 0);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop.");
    }
}
