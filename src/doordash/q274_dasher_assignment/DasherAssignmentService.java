package doordash.q274_dasher_assignment;

import java.util.*;

// DEBUGGING DRILL: the code below is close to correct but contains planted
// logic bugs. Find and fix them — the tests fail until the behavior is right.
// Do not change the tests; change the implementation.
//
// Dasher Assignment Service: a pool of available dashers kept in 16 buckets.
// Bucket of a dasher: (dasherId * 31 + 17) % 16. pickDasher takes the first
// dasher of the first non-empty bucket (lowest bucket wins; FIFO inside a
// bucket) and records the order->dasher assignment. orderDelivered frees the
// dasher back to the END of its bucket. An order that already has a dasher
// gets -1; with nobody available, pickDasher returns -1.
//
// Suspect areas (from the tutorial's bug table): integer overflow in the
// bucket formula, whether every pickDasher scan restarts at bucket 0, where
// a freed dasher is re-inserted, and the missing duplicate-order guard.
//
// Source: https://codezym.com/question/274-dasher-assignment-service-order-delivery
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/101-300/274-dasher-assignment-service-order-delivery/q-274-java-tutorial.md
public class DasherAssignmentService {

    // 16 buckets of available dashers, each bucket keeps arrival (FIFO) order
    List<List<Integer>> buckets = new ArrayList<>();

    // Active assignments kept side by side:
    // activeOrders.get(i) is being delivered by activeDashers.get(i)
    List<Integer> activeOrders = new ArrayList<>();
    List<Integer> activeDashers = new ArrayList<>();

    int nextBucket = 0;

    public DasherAssignmentService() {
        for (int i = 0; i < 16; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    int bucketIndex(int dasherId) {
        return (dasherId * 31 + 17) % 16;
    }

    public void addDasher(int dasherId) {
        List<Integer> bucket = buckets.get(bucketIndex(dasherId));
        if (bucket.contains(dasherId)) return;          // already available
        if (activeDashers.contains(dasherId)) return;   // busy with an active order
        bucket.add(dasherId);                           // append to the end
    }

    public int pickDasher(int orderId) {
        for (int s = 0; s < buckets.size(); s++) {
            int b = (nextBucket + s) % buckets.size();
            List<Integer> bucket = buckets.get(b);
            if (!bucket.isEmpty()) {
                int dasherId = bucket.remove(0);        // first in, first out
                nextBucket = (b + 1) % buckets.size();
                activeOrders.add(orderId);
                activeDashers.add(dasherId);
                return dasherId;
            }
        }
        return -1;                                      // no dasher is available
    }

    public void orderDelivered(int orderId) {
        int i = activeOrders.indexOf(orderId);
        if (i == -1) return;                            // order is not active
        activeOrders.remove(i);                         // remove by index
        int dasherId = activeDashers.remove(i);
        buckets.get(bucketIndex(dasherId)).add(0, dasherId);
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Tutorial Example 1: bucket(12)=5 -> [12]; bucket(16)=bucket(32)=1 -> [16, 32].
        DasherAssignmentService ex1 = new DasherAssignmentService();
        ex1.addDasher(12);
        ex1.addDasher(16);
        ex1.addDasher(32);
        check("pickDasher(101) -> 16", ex1.pickDasher(101), 16);
        check("pickDasher(102) -> 32", ex1.pickDasher(102), 32);
        check("pickDasher(103) -> 12", ex1.pickDasher(103), 12);
        check("pickDasher(104) -> -1 (nobody left)", ex1.pickDasher(104), -1);
        ex1.orderDelivered(101);
        check("after orderDelivered(101), pickDasher(104) -> 16", ex1.pickDasher(104), 16);

        // An order that already has a dasher must not get a second one.
        DasherAssignmentService dup = new DasherAssignmentService();
        dup.addDasher(16);
        dup.addDasher(32);
        check("pickDasher(7) -> 16", dup.pickDasher(7), 16);
        check("pickDasher(7) again -> -1", dup.pickDasher(7), -1);

        // Every pickDasher scan must restart at bucket 0 (dasher 1 -> bucket 0).
        DasherAssignmentService rs = new DasherAssignmentService();
        rs.addDasher(1);
        rs.addDasher(16);
        check("pickDasher(1) -> 1", rs.pickDasher(1), 1);
        rs.orderDelivered(1);
        check("pickDasher(2) -> 1 (bucket 0 again)", rs.pickDasher(2), 1);

        // A freed dasher goes to the END of its bucket (FIFO), not the front.
        DasherAssignmentService fifo = new DasherAssignmentService();
        fifo.addDasher(16);
        fifo.addDasher(32);
        check("pickDasher(1) -> 16", fifo.pickDasher(1), 16);
        fifo.orderDelivered(1);
        check("pickDasher(2) -> 32 (16 went to the back)", fifo.pickDasher(2), 32);

        // Large dasherId must not break the bucket formula (dasherId * 31 overflows int).
        boolean largeOk;
        try {
            DasherAssignmentService big = new DasherAssignmentService();
            big.addDasher(100_000_000); // correct bucket: 1
            largeOk = (big.pickDasher(3) == 100_000_000);
        } catch (Exception e) {
            largeOk = false;
        }
        check("dasherId 100000000 -> picked correctly", largeOk, true);

        // Delivering an unknown order is a no-op, not a crash.
        DasherAssignmentService noop = new DasherAssignmentService();
        noop.addDasher(16);
        noop.orderDelivered(999);
        check("unknown orderDelivered -> still picks 16", noop.pickDasher(5), 16);

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
