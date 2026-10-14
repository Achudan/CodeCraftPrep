import java.util.*;

// Dasher Payout Service (exercise — fill the TODOs, make the tests pass)
// Run: javac DasherPayout.java && java DasherPayout
// Implement the two TODOs. Tests in main() should all pass when you're done.
public class DasherPayout {

    public record Interval(int start, int end) {}

    // Total paid minutes after merging overlapping/adjacent intervals.
    public static int totalActiveMinutes(List<Interval> intervals) {
        // TODO: validate, sort by start, sweep-merge, sum lengths.
        return 0;
    }

    // Payout for one dasher at the given hourly rate.
    public static double calculatePayout(List<Interval> intervals, double ratePerHour) {
        // TODO: use totalActiveMinutes.
        return 0.0;
    }

    // Harder version: payout per dasher.
    public static Map<String, Double> payoutByDasher(
            Map<String, List<Interval>> byDasher, double ratePerHour) {
        // TODO (stretch): reuse calculatePayout per dasher.
        return Map.of();
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-42s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        check("empty -> 0 min", totalActiveMinutes(List.of()), 0);
        check("one [0,60] -> 60 min", totalActiveMinutes(List.of(new Interval(0, 60))), 60);
        check("[0,60] @ $30/hr -> $30.00", calculatePayout(List.of(new Interval(0, 60)), 30.0), 30.0);
        check("overlap [0,60],[30,90] -> 90 min",
                totalActiveMinutes(List.of(new Interval(0, 60), new Interval(30, 90))), 90);
        check("adjacent [0,30],[30,60] -> 60 min",
                totalActiveMinutes(List.of(new Interval(0, 30), new Interval(30, 60))), 60);
        check("unsorted [60,90],[0,30] -> 60 min",
                totalActiveMinutes(List.of(new Interval(60, 90), new Interval(0, 30))), 60);
        check("nested [0,120],[30,60] -> 120 min",
                totalActiveMinutes(List.of(new Interval(0, 120), new Interval(30, 60))), 120);

        // Stretch: payout per dasher. d1's intervals merge to 90 min.
        Map<String, List<Interval>> byDasher = new HashMap<>();
        byDasher.put("d1", List.of(new Interval(0, 60), new Interval(30, 90)));
        byDasher.put("d2", List.of(new Interval(0, 30)));
        Map<String, Double> perDasher = payoutByDasher(byDasher, 30.0);
        check("d1: 90 min @ $30/hr -> 45.0", perDasher.get("d1"), 45.0);
        check("d2: 30 min @ $30/hr -> 15.0", perDasher.get("d2"), 15.0);
        check("two dashers in result -> 2", perDasher.size(), 2);
        check("empty map -> empty result", payoutByDasher(Map.of(), 30.0), Map.of());
        check("dasher with no intervals -> 0.0",
                payoutByDasher(Map.of("d3", List.of()), 30.0).get("d3"), 0.0);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop for tonight.");
    }
}
