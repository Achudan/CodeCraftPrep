package doordash.q103_payout_logic;

import java.time.LocalDate;
import java.util.*;

// A dasher is paid for the time their fulfilled deliveries were in progress.
// Each record looks like "dasherId,deliveryId,timestamp,status" where status is
// ACCEPTED, FULFILLED or CANCELLED. A delivery earns 0.3 dollars per minute from
// its ACCEPTED record until its FULFILLED record; a cancelled delivery never
// earns anything, not even while ongoing. Seconds are ignored and the input
// list may not be in time order.
//
// Source: https://codezym.com/question/103-dasher-payout-logic
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/101-300/103-dasher-payout-logic/q-103-java-tutorial.md
// (The tutorial names this class DasherPayoutService; the API below is identical.)
public class DasherPayoutLogic {

    // dasherId -> total minutes of all fulfilled deliveries of that dasher
    Map<Integer, Long> fulfilledMinutes = new HashMap<>();

    public DasherPayoutLogic(List<String> deliveryActivities) {
        // TODO: precompute each dasher's total fulfilled minutes in the
        // constructor, so getPayout() is a single map lookup. Hint from the
        // tutorial: paying each ongoing fulfilled delivery 0.3/min is the same
        // as paying every fulfilled delivery for its own ACCEPTED->FULFILLED
        // span, so no timeline walk or sorting is needed.
    }

    public double getPayout(int dasherId) {
        // TODO: convert this dasher's fulfilled minutes to dollars.
        // 0.3 dollars/min = 30 cents/min: minutes * 30 / 100.0 (count in
        // whole cents first so the money stays exact).
        return 0.0;
    }

    // "1,10,2023-03-31 18:15:00,ACCEPTED" -> ["1", "10", "2023-03-31 18:15:00", "ACCEPTED"]
    String[] parseActivity(String activity) {
        String[] parts = activity.split(",");
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }

    // "2023-03-31 18:15:42" -> minutes counted from 1970-01-01.
    // Seconds are never read, so they are ignored as the problem asks.
    long toMinutes(String timestamp) {
        String[] dateAndTime = timestamp.split(" ");
        String[] time = dateAndTime[1].split(":");
        long days = LocalDate.parse(dateAndTime[0]).toEpochDay();
        return days * 24 * 60 + Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Tutorial Example 1 (records deliberately shuffled): d1 18:15->18:36
        // (21 min) and d2 18:18->18:45 (27 min) = 48 paid minutes -> 14.4.
        DasherPayoutLogic ex1 = new DasherPayoutLogic(List.of(
                "1,2,2023-03-31 18:45:00,FULFILLED",
                "1,1,2023-03-31 18:15:00,ACCEPTED",
                "1,2,2023-03-31 18:18:00,ACCEPTED",
                "1,1,2023-03-31 18:36:00,FULFILLED"));
        check("example1 -> 14.4", ex1.getPayout(1), 14.4);

        // Tutorial Example 2: delivery 2 is CANCELLED, so it adds nothing.
        // Only d1 18:15->18:36 counts = 21 paid minutes -> 6.3.
        DasherPayoutLogic ex2 = new DasherPayoutLogic(List.of(
                "1,1,2023-03-31 18:15:00,ACCEPTED",
                "1,2,2023-03-31 18:18:00,ACCEPTED",
                "1,1,2023-03-31 18:36:00,FULFILLED",
                "1,2,2023-03-31 18:45:00,CANCELLED"));
        check("example2 (cancelled) -> 6.3", ex2.getPayout(1), 6.3);

        check("unknown dasher -> 0.0", ex1.getPayout(999), 0.0);

        // Seconds are ignored: 10:04:16 to 10:06:12 counts as 2 minutes -> 0.6.
        DasherPayoutLogic secs = new DasherPayoutLogic(List.of(
                "1,5,2023-03-31 10:04:16,ACCEPTED",
                "1,5,2023-03-31 10:06:12,FULFILLED"));
        check("seconds ignored -> 0.6", secs.getPayout(1), 0.6);

        // Two dashers stay isolated: dasher 2 works 09:00->10:00 = 60 min -> 18.0.
        DasherPayoutLogic two = new DasherPayoutLogic(List.of(
                "1,1,2023-03-31 18:15:00,ACCEPTED",
                "1,1,2023-03-31 18:36:00,FULFILLED",
                "2,9,2023-03-31 09:00:00,ACCEPTED",
                "2,9,2023-03-31 10:00:00,FULFILLED"));
        check("dasher 1 -> 6.3", two.getPayout(1), 6.3);
        check("dasher 2 -> 18.0", two.getPayout(2), 18.0);

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
