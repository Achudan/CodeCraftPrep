import java.util.*;

// Taper-day warm-up — REFERENCE SOLUTION (all tests pass).
// Run: javac WarmUpSolution.java && java WarmUpSolution
// The exercise version is WarmUp.java (same tests, TODOs for you).
public class WarmUpSolution {

    public record Delivery(int startMin, int endMin) {}

    // Merge overlapping/adjacent deliveries, return total paid minutes.
    public static int totalActiveMinutes(List<Delivery> deliveries) {
        if (deliveries.isEmpty()) return 0;
        List<Delivery> sorted = new ArrayList<>(deliveries);
        sorted.sort(Comparator.comparingInt(Delivery::startMin));
        int total = 0;
        int curStart = sorted.get(0).startMin(), curEnd = sorted.get(0).endMin();
        for (int i = 1; i < sorted.size(); i++) {
            Delivery d = sorted.get(i);
            if (d.startMin() <= curEnd) curEnd = Math.max(curEnd, d.endMin());
            else {
                total += curEnd - curStart;
                curStart = d.startMin();
                curEnd = d.endMin();
            }
        }
        return total + (curEnd - curStart);
    }

    // Payout for one dasher at the given hourly rate.
    public static double payout(List<Delivery> deliveries, double ratePerHour) {
        return totalActiveMinutes(deliveries) / 60.0 * ratePerHour;
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
