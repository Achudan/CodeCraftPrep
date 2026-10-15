package doordash.q097_dasher_payout;

import java.util.*;

// Design Dasher Payout Service (CodeZym Q97) — EXERCISE.
// A dasher earns basePayRate cents for every minute of every order; overlapping
// orders each pay for their own minutes (end - start + 1, inclusive). Deliveries
// with both START and END (arriving in any order, even in different calls) also
// earn group bonuses: (completedDeliveries / deliveryCountsToGetBonus) * bonusPay.
// Source: https://codezym.com/question/97-design-dasher-payout-service
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/1-100/97-design-dasher-payout-service/q-97-java-tutorial.md
//
// Implement the three TODOs below. Tests in main() should all pass when done.
public class DasherPayoutService {
    Map<String, Dasher> dashers = new HashMap<>(); // dasherId -> dasher

    public DasherPayoutService() {
    }

    public void addOrUpdatePayoutMetadata(String dasherId, int basePayRate, int bonusPay, int deliveryCountsToGetBonus) {
        Dasher dasher = getOrCreateDasher(dasherId);
        dasher.basePayRate = basePayRate;
        dasher.bonusPay = bonusPay;
        dasher.deliveryCountsToGetBonus = deliveryCountsToGetBonus;
    }

    public void addDeliveryActivity(String dasherId, List<String> deliveryActivities) {
        Dasher dasher = getOrCreateDasher(dasherId);
        for (String activity : deliveryActivities) {
            // "orderId=O1,action=START,time=09:00" -> ["orderId=O1", "action=START", "time=09:00"]
            String[] parts = activity.split(",");
            String orderId = readValue(parts[0]);
            boolean isStart = readValue(parts[1]).equals("START");
            int time = toMinutes(readValue(parts[2]));
            dasher.addRecord(orderId, isStart, time);
        }
    }

    public int payout(String dasherId) {
        Dasher dasher = dashers.get(dasherId);
        if (dasher == null) return 0;
        return dasher.calculatePayout();
    }

    Dasher getOrCreateDasher(String dasherId) {
        Dasher dasher = dashers.get(dasherId);
        if (dasher == null) {
            dasher = new Dasher();
            dashers.put(dasherId, dasher);
        }
        return dasher;
    }

    // "time=09:00" -> "09:00"
    String readValue(String keyValue) {
        return keyValue.substring(keyValue.indexOf('=') + 1).trim();
    }

    // "09:00" -> 540 (minutes since midnight)
    int toMinutes(String hhmm) {
        String[] hourAndMinute = hhmm.split(":");
        return Integer.parseInt(hourAndMinute[0]) * 60 + Integer.parseInt(hourAndMinute[1]);
    }

    // ---- Test harness (worked examples from the tutorial) ----

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-58s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Tutorial Example 3: END arrives before START, split across two calls.
        // O20: 10:00-10:05 = 6 min; O21: 10:08-10:10 = 3 min.
        // 9 * 25 + (2 / 2) * 150 = 225 + 150 = 375 cents.
        DasherPayoutService svc = new DasherPayoutService();
        svc.addOrUpdatePayoutMetadata("D1", 25, 150, 2);
        svc.addDeliveryActivity("D1", List.of(
                "orderId=O20,action=END,time=10:05",
                "orderId=O21,action=START,time=10:08"));
        svc.addDeliveryActivity("D1", List.of(
                "orderId=O20,action=START,time=10:00",
                "orderId=O21,action=END,time=10:10"));
        check("example 3: out-of-order records -> 375", svc.payout("D1"), 375);

        // Tutorial Example 2: overlapping orders each pay their own minutes.
        // O10: 09:00-09:20 = 21 min; O11: 09:10-09:30 = 21 min. 42 * 30 = 1260.
        DasherPayoutService svc2 = new DasherPayoutService();
        svc2.addOrUpdatePayoutMetadata("D2", 30, 0, 1);
        svc2.addDeliveryActivity("D2", List.of(
                "orderId=O10,action=START,time=09:00",
                "orderId=O10,action=END,time=09:20",
                "orderId=O11,action=START,time=09:10",
                "orderId=O11,action=END,time=09:30"));
        check("example 2: overlap double-pays -> 1260", svc2.payout("D2"), 1260);

        // Tutorial edge case: start > end pays 0 minutes, but the order still
        // has both records, so it counts as a completed delivery for the bonus.
        DasherPayoutService svc3 = new DasherPayoutService();
        svc3.addOrUpdatePayoutMetadata("D3", 10, 100, 1);
        svc3.addDeliveryActivity("D3", List.of(
                "orderId=O1,action=START,time=10:05",
                "orderId=O1,action=END,time=10:00"));
        check("start > end: 0 min, 1 completed delivery -> 100", svc3.payout("D3"), 100);

        // Tutorial edge case: only START received -> order still pending, pays nothing.
        DasherPayoutService svc4 = new DasherPayoutService();
        svc4.addOrUpdatePayoutMetadata("D4", 50, 200, 1);
        svc4.addDeliveryActivity("D4", List.of(
                "orderId=O1,action=START,time=09:00"));
        check("incomplete order -> 0", svc4.payout("D4"), 0);

        // Unknown dasher -> 0.
        check("unknown dasher -> 0", svc.payout("NOBODY"), 0);

        // Tutorial edge case: bonus uses integer division (3 deliveries, groups
        // of 2 -> 1 group). 3 * 10 + (3 / 2) * 50 = 30 + 50 = 80.
        DasherPayoutService svc5 = new DasherPayoutService();
        svc5.addOrUpdatePayoutMetadata("D5", 10, 50, 2);
        svc5.addDeliveryActivity("D5", List.of(
                "orderId=O1,action=START,time=09:00", "orderId=O1,action=END,time=09:00",
                "orderId=O2,action=START,time=09:01", "orderId=O2,action=END,time=09:01",
                "orderId=O3,action=START,time=09:02", "orderId=O3,action=END,time=09:02"));
        check("integer-division bonus: 3 deliveries, groups of 2 -> 80", svc5.payout("D5"), 80);

        System.out.println(anyFail ? "\nSome tests FAILED — keep going." : "\nAll tests PASS.");
        if (anyFail) System.exit(1);
    }
}

/**
 * An order that is still waiting for its START or END record.
 * Times are minutes of the day (09:00 -> 540).
 * -1 means "not received yet", because 0 is a valid time (00:00).
 */
class Order {
    int start = -1;
    int end = -1;

    boolean isComplete() {
        return start != -1 && end != -1;
    }

    // [start, end] includes both minutes. If start > end the order pays nothing.
    int minutes() {
        // TODO: return end - start + 1, or 0 when start > end.
        return 0;
    }
}

/**
 * Pay settings and running totals of one dasher.
 */
class Dasher {
    int basePayRate;              // cents per minute for each ongoing delivery
    int bonusPay;                 // cents for every group of completed deliveries
    int deliveryCountsToGetBonus; // deliveries needed for one bonus

    Map<String, Order> pendingOrders = new HashMap<>(); // orderId -> order still missing START or END
    int totalMinutes = 0;         // minutes of all completed orders
    int completedDeliveries = 0;  // orders that have both START and END

    void addRecord(String orderId, boolean isStart, int time) {
        // TODO: fill in whichever half arrived. When the order is complete,
        // add its minutes to totalMinutes, bump completedDeliveries, and
        // remove it from pendingOrders (its minutes can't change anymore).
    }

    int calculatePayout() {
        // TODO: basePay = totalMinutes * basePayRate; bonus =
        // (completedDeliveries / deliveryCountsToGetBonus) * bonusPay.
        // Guard against deliveryCountsToGetBonus == 0 (metadata never set).
        return 0;
    }
}
