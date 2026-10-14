import java.util.*;

// Longest Sequence of Pickup Jobs — REFERENCE SOLUTION (all tests pass).
// Run: javac PickupJobsSolution.java && java PickupJobsSolution
// The exercise version is PickupJobs.java (same tests, TODOs for you).
public class PickupJobsSolution {

    // LCS length of the two ordered merchant lists. Say the recurrence BEFORE
    // coding: dp[i][j] = dp[i-1][j-1]+1 on match, else max(dp[i-1][j], dp[i][j-1]).
    public static int lcsLength(List<String> a, List<String> b) {
        return lcsTable(a, b)[a.size()][b.size()];
    }

    private static int[][] lcsTable(List<String> a, List<String> b) {
        int m = a.size(), n = b.size();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.get(i - 1).equals(b.get(j - 1))) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp;
    }

    // The actual longest common sequence. Tie-break (state BEFORE coding):
    // when skipping from A vs skipping from B tie on length, skip B's job
    // first — the returned sequence prefers merchant A's jobs. That's what
    // makes test 1 deterministic.
    public static List<String> longestPickupSequence(List<String> a, List<String> b) {
        int[][] dp = lcsTable(a, b);
        List<String> seq = new ArrayList<>();
        int i = a.size(), j = b.size();
        while (i > 0 && j > 0) {
            if (a.get(i - 1).equals(b.get(j - 1))) {
                seq.add(a.get(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--; // skipping A's job is strictly better
            } else {
                j--; // tie (or B better): skip B's job first
            }
        }
        Collections.reverse(seq);
        return seq;
    }

    // ---- Test harness ----

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        List<String> a = List.of("p1", "p2", "p3", "p4");
        List<String> b = List.of("p2", "p4", "p3");
        check("length -> 2", lcsLength(a, b), 2);
        // Candidates are [p2, p3] and [p2, p4]; the tie-break keeps A's p4.
        check("sequence -> [p2, p4]", longestPickupSequence(a, b), List.of("p2", "p4"));

        List<String> same = List.of("a", "b", "c");
        check("identical -> 3", lcsLength(same, same), 3);
        check("identical seq -> full list", longestPickupSequence(same, same), same);

        check("no overlap -> 0", lcsLength(List.of("x", "y"), List.of("p", "q")), 0);
        check("no overlap seq -> []", longestPickupSequence(List.of("x", "y"), List.of("p", "q")), List.of());

        check("empty A -> 0", lcsLength(List.of(), List.of("p")), 0);
        check("empty A seq -> []", longestPickupSequence(List.of(), List.of("p")), List.of());

        // Reversed lists: LCS length 1. Ties skip from B's end first,
        // so reconstruction lands on rb's FIRST job that appears in A: d.
        List<String> ra = List.of("a", "b", "c", "d");
        List<String> rb = List.of("d", "c", "b", "a");
        check("reversed -> 1", lcsLength(ra, rb), 1);
        check("reversed seq -> [d]", longestPickupSequence(ra, rb), List.of("d"));

        check("order preserved -> [b, d]",
              longestPickupSequence(List.of("a", "b", "c", "d", "e"), List.of("b", "d")),
              List.of("b", "d"));

        System.out.println(anyFail ? "\nSome tests FAILED — keep going." : "\nAll tests PASS.");
        if (anyFail) System.exit(1);
    }
}
