import java.util.*;

// DoorDash CodeCraft Day 1 — Dasher Payout Service (Java starter)
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
        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop for tonight.");
    }
}
