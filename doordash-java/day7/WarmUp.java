import java.util.*;

// DoorDash CodeCraft Day 7 — Taper-day warm-up (Java starter)
// Run: javac WarmUp.java && java WarmUp
// This is Thursday's single easy run: Dasher Payout, easy version, ~30 min.
// It should feel almost automatic by now. If anything feels hard here, that's
// your review target for Block 1 — but you should still be done in 30.
public class WarmUp {

    public record Delivery(int startMin, int endMin) {}

    // Merge overlapping/adjacent deliveries, return total paid minutes.
    public static int totalActiveMinutes(List<Delivery> deliveries) {
        // TODO: sort by start, sweep-merge, sum lengths. You know this one cold.
        return 0;
    }

    // Payout for one dasher at the given hourly rate.
    public static double payout(List<Delivery> deliveries, double ratePerHour) {
        // TODO: use totalActiveMinutes. minutes/60 * rate.
        return 0.0;
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-40s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        check("empty -> 0 min", totalActiveMinutes(List.of()), 0);
        check("[0,60] -> 60 min", totalActiveMinutes(List.of(new Delivery(0, 60))), 60);
        check("overlap [0,60],[30,90] -> 90 min",
                totalActiveMinutes(List.of(new Delivery(0, 60), new Delivery(30, 90))), 90);
        check("adjacent [0,30],[30,60] -> 60 min",
                totalActiveMinutes(List.of(new Delivery(0, 30), new Delivery(30, 60))), 60);
        check("[0,60] @ $30/hr -> $30.00", payout(List.of(new Delivery(0, 60)), 30.0), 30.0);
        check("two merged runs @ $24/hr -> $48.00",
                payout(List.of(new Delivery(0, 60), new Delivery(30, 90), new Delivery(120, 150)), 24.0), 48.0);
        System.out.println(anyFail
                ? "\nSome tests failing — finish the TODOs, then stop for the day."
                : "\nAll tests pass. Note your time. Done for today — early night, no more code.");
    }
}
