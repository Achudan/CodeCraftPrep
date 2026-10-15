package doordash.q098_workflow_automation;

import java.util.*;

// Design Workflow Automation Engine (CodeZym Q98) — EXERCISE.
// Every order is a small state machine: OPEN -> COMPLETED or CANCELLED.
// updateOrderStatus() writes "ORDER:<id>", "BY:<by>", "STATUS:<code>", then the
// current state decides the ACTION/REFUND log lines. Refund percentages are
// business rules kept behind a Strategy (RefundPolicy).
//
// NOTE: the tutorial names the entry class WorkflowAutomator; it is renamed to
// WorkflowAutomationEngine here to match this exercise's file layout. All other
// class/method names, signatures and semantics are exactly as in the tutorial.
// Late delivery refund: < 10 min late -> 0%, 10-20 min -> 50%, > 20 min -> 100%.
// Source: https://codezym.com/question/98-design-workflow-automation-engine
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/1-100/98-design-workflow-automation-engine/q-98-java-tutorial.md
//
// Implement the TODOs below (StandardRefundPolicy + the State classes).
// Tests in main() should all pass when done.
public class WorkflowAutomationEngine {
    Set<String> users = new HashSet<>();
    Set<String> restaurants = new HashSet<>();
    Map<String, Order> orders = new HashMap<>(); // orderId -> Order
    RefundPolicy refundPolicy = new StandardRefundPolicy();

    public WorkflowAutomationEngine(List<String> existingUsers, List<String> existingRestaurants) {
        users.addAll(existingUsers);
        restaurants.addAll(existingRestaurants);
    }

    public void createOrder(String orderId, String restaurantId, String userId, int deliveryETA, int currentTime) {
        // every new order starts OPEN
        orders.put(orderId, new Order(orderId, restaurantId, userId, deliveryETA, new OpenState(refundPolicy)));
    }

    public List<String> updateOrderStatus(String orderId, String by, int currentTime, String statusCode) {
        Order order = orders.get(orderId);
        List<String> logs = new ArrayList<>();
        logs.add("ORDER:" + orderId);
        logs.add("BY:" + by); // 'by' only affects logs
        logs.add("STATUS:" + statusCode);

        // hand the update to the current state, the state decides what happens
        if (statusCode.equals("ORDER_DELIVERED")) {
            order.state.onDelivered(order, currentTime, logs);
        } else if (statusCode.equals("DELIVERY_CANCELLED")) {
            order.state.onDeliveryCancelled(order, logs);
        } else if (statusCode.equals("WANT_REFUND")) {
            order.state.onRefundRequest(order, logs);
        } else {
            // ORDER_GETTING_PREPARED, OUT_FOR_DELIVERY, RAIN_DELAY and other informational updates
            order.state.onInfoUpdate(order, statusCode, logs);
        }
        return logs;
    }

    // ---- Test harness (rules table + Example 1 walkthrough from the tutorial) ----

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-58s %s%n  got  %s%n  want %s%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        WorkflowAutomationEngine eng = new WorkflowAutomationEngine(List.of("U1"), List.of("R1"));

        // Tutorial Example 1: order created with deliveryETA = 100.
        eng.createOrder("O1", "R1", "U1", 100, 0);
        check("info update stays OPEN",
                eng.updateOrderStatus("O1", "D1", 85, "OUT_FOR_DELIVERY"),
                List.of("ORDER:O1", "BY:D1", "STATUS:OUT_FOR_DELIVERY", "ACTION:CONTINUE_ORDER"));
        // 15 minutes late -> LATE:true, COMPLETE_ORDER, 50% refund issued.
        check("delivered 15 min late -> 50% refund",
                eng.updateOrderStatus("O1", "D1", 115, "ORDER_DELIVERED"),
                List.of("ORDER:O1", "BY:D1", "STATUS:ORDER_DELIVERED", "LATE:true",
                        "ACTION:COMPLETE_ORDER", "ACTION:ISSUE_REFUND", "REFUND_PERCENT:50"));
        // Refund already issued -> REFUND_ALREADY_ISSUED.
        check("repeat WANT_REFUND on COMPLETED",
                eng.updateOrderStatus("O1", "D1", 120, "WANT_REFUND"),
                List.of("ORDER:O1", "BY:D1", "STATUS:WANT_REFUND", "ACTION:REFUND_ALREADY_ISSUED"));
        // Closed order ignores informational updates.
        check("info update on COMPLETED -> ignored",
                eng.updateOrderStatus("O1", "D1", 125, "RAIN_DELAY"),
                List.of("ORDER:O1", "BY:D1", "STATUS:RAIN_DELAY", "ACTION:IGNORED_ORDER_ALREADY_CLOSED"));

        // Tutorial rule: WANT_REFUND on OPEN = customer cancel. Preparation not
        // started -> 95% refund first, then CANCEL_ORDER.
        eng.createOrder("O2", "R1", "U1", 200, 0);
        check("customer cancel before prep -> 95%",
                eng.updateOrderStatus("O2", "U1", 50, "WANT_REFUND"),
                List.of("ORDER:O2", "BY:U1", "STATUS:WANT_REFUND",
                        "ACTION:ISSUE_REFUND", "REFUND_PERCENT:95", "ACTION:CANCEL_ORDER"));
        // A CANCELLED order ignores everything, even WANT_REFUND.
        check("WANT_REFUND on CANCELLED -> ignored",
                eng.updateOrderStatus("O2", "U1", 55, "WANT_REFUND"),
                List.of("ORDER:O2", "BY:U1", "STATUS:WANT_REFUND", "ACTION:IGNORED_ORDER_ALREADY_CLOSED"));

        // Tutorial rule: DELIVERY_CANCELLED -> CANCEL_ORDER first, then 100% refund.
        eng.createOrder("O3", "R1", "U1", 200, 0);
        check("delivery cancelled -> 100%",
                eng.updateOrderStatus("O3", "D1", 60, "DELIVERY_CANCELLED"),
                List.of("ORDER:O3", "BY:D1", "STATUS:DELIVERY_CANCELLED",
                        "ACTION:CANCEL_ORDER", "ACTION:ISSUE_REFUND", "REFUND_PERCENT:100"));

        // Tutorial rule: ORDER_GETTING_PREPARED marks preparation started, so a
        // later customer cancel refunds 100% instead of 95%.
        eng.createOrder("O4", "R1", "U1", 200, 0);
        check("prep started -> CONTINUE_ORDER",
                eng.updateOrderStatus("O4", "D1", 10, "ORDER_GETTING_PREPARED"),
                List.of("ORDER:O4", "BY:D1", "STATUS:ORDER_GETTING_PREPARED", "ACTION:CONTINUE_ORDER"));
        check("customer cancel after prep -> 100%",
                eng.updateOrderStatus("O4", "U1", 20, "WANT_REFUND"),
                List.of("ORDER:O4", "BY:U1", "STATUS:WANT_REFUND",
                        "ACTION:ISSUE_REFUND", "REFUND_PERCENT:100", "ACTION:CANCEL_ORDER"));

        // Tutorial rule: less than 10 minutes late -> LATE:true but NO refund lines.
        eng.createOrder("O5", "R1", "U1", 100, 0);
        check("delivered 5 min late -> no refund lines",
                eng.updateOrderStatus("O5", "D1", 105, "ORDER_DELIVERED"),
                List.of("ORDER:O5", "BY:D1", "STATUS:ORDER_DELIVERED", "LATE:true", "ACTION:COMPLETE_ORDER"));
        // ...and a later WANT_REFUND finds no matching refund rule.
        check("WANT_REFUND with 5 min lateness -> NO_REFUND_RULE_MATCH",
                eng.updateOrderStatus("O5", "U1", 110, "WANT_REFUND"),
                List.of("ORDER:O5", "BY:U1", "STATUS:WANT_REFUND", "ACTION:NO_REFUND_RULE_MATCH"));

        // Tutorial rule: more than 20 minutes late -> 100% refund on delivery.
        eng.createOrder("O6", "R1", "U1", 100, 0);
        check("delivered 25 min late -> 100%",
                eng.updateOrderStatus("O6", "D1", 125, "ORDER_DELIVERED"),
                List.of("ORDER:O6", "BY:D1", "STATUS:ORDER_DELIVERED", "LATE:true",
                        "ACTION:COMPLETE_ORDER", "ACTION:ISSUE_REFUND", "REFUND_PERCENT:100"));

        System.out.println(anyFail ? "\nSome tests FAILED — keep going." : "\nAll tests PASS.");
        if (anyFail) System.exit(1);
    }
}

/**
 * Everything we need to remember about one order.
 * The 'state' object decides how the order reacts to the next status update.
 */
class Order {
    String orderId;
    String restaurantId;
    String userId;
    int deliveryETA;
    OrderState state;

    boolean preparationStarted = false; // decides 95% vs 100% on WANT_REFUND
    boolean refundIssued = false;       // at most one refund per order
    int minutesLate = 0;                // saved on delivery, reused by a later WANT_REFUND

    Order(String orderId, String restaurantId, String userId, int deliveryETA, OrderState state) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.deliveryETA = deliveryETA;
        this.state = state;
    }

    /** The only place a refund is issued, so the flag and both log lines always stay together. */
    void issueRefund(int percent, List<String> logs) {
        refundIssued = true;
        logs.add("ACTION:ISSUE_REFUND");
        logs.add("REFUND_PERCENT:" + percent);
    }
}

// ===== Strategy: refund rules =====

/** All compensation numbers live behind this interface. */
interface RefundPolicy {
    int lateDeliveryPercent(int minutesLate);
    int cancelledDeliveryPercent();
    int customerCancelPercent(boolean preparationStarted);
}

/** The refund rules from the problem statement. */
class StandardRefundPolicy implements RefundPolicy {
    // TODO: less than 10 minutes late -> 0; 10 to 20 minutes -> 50; more than 20 -> 100.
    public int lateDeliveryPercent(int minutesLate) {
        return 0;
    }

    // TODO: a cancelled delivery always refunds 100%.
    public int cancelledDeliveryPercent() {
        return 0;
    }

    // TODO: customer cancel -> 100% if preparation started, else 95%.
    public int customerCancelPercent(boolean preparationStarted) {
        return 0;
    }
}

// ===== State: order lifecycle =====

/** Every state answers the same 4 kinds of updates, each in its own way. */
interface OrderState {
    void onInfoUpdate(Order order, String statusCode, List<String> logs);
    void onDelivered(Order order, int currentTime, List<String> logs);
    void onDeliveryCancelled(Order order, List<String> logs);
    void onRefundRequest(Order order, List<String> logs);
}

/** OPEN: the only state where an order can still move forward. */
class OpenState implements OrderState {
    RefundPolicy refundPolicy;

    OpenState(RefundPolicy refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    public void onInfoUpdate(Order order, String statusCode, List<String> logs) {
        // TODO: ORDER_GETTING_PREPARED marks preparationStarted = true.
        // Every informational update logs ACTION:CONTINUE_ORDER; the order stays OPEN.
    }

    public void onDelivered(Order order, int currentTime, List<String> logs) {
        // TODO: store minutesLate = currentTime - deliveryETA; log LATE:<minutesLate > 0>
        // right after STATUS; switch to CompletedState; log ACTION:COMPLETE_ORDER;
        // issue a refund via the policy when the percent is > 0.
    }

    public void onDeliveryCancelled(Order order, List<String> logs) {
        // TODO: switch to CancelledState; log ACTION:CANCEL_ORDER first, then
        // issue the policy's cancelled-delivery refund.
    }

    public void onRefundRequest(Order order, List<String> logs) {
        // TODO: customer cancel: issue the policy's customer-cancel refund FIRST
        // (95 vs 100 depends on preparationStarted), then switch to
        // CancelledState and log ACTION:CANCEL_ORDER.
    }
}

/** Shared by COMPLETED and CANCELLED: every non-refund update is ignored. */
abstract class ClosedState implements OrderState {
    public void onInfoUpdate(Order order, String statusCode, List<String> logs) {
        logs.add("ACTION:IGNORED_ORDER_ALREADY_CLOSED");
    }

    public void onDelivered(Order order, int currentTime, List<String> logs) {
        logs.add("ACTION:IGNORED_ORDER_ALREADY_CLOSED");
    }

    public void onDeliveryCancelled(Order order, List<String> logs) {
        logs.add("ACTION:IGNORED_ORDER_ALREADY_CLOSED");
    }
}

/** COMPLETED: a refund request is checked against the stored lateness. */
class CompletedState extends ClosedState {
    RefundPolicy refundPolicy;

    CompletedState(RefundPolicy refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    public void onRefundRequest(Order order, List<String> logs) {
        // TODO: if a refund was already issued -> ACTION:REFUND_ALREADY_ISSUED.
        // Otherwise decide from the stored minutesLate via the policy: issue the
        // refund when percent > 0, else log ACTION:NO_REFUND_RULE_MATCH.
    }
}

/** CANCELLED: final. It ignores every update, even WANT_REFUND. */
class CancelledState extends ClosedState {
    public void onRefundRequest(Order order, List<String> logs) {
        // TODO: the refund decided at cancellation time is final ->
        // log ACTION:IGNORED_ORDER_ALREADY_CLOSED.
    }
}
