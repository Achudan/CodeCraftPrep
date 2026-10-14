import java.util.*;

// DoorDash CodeCraft Day 5 (part A) — Longest Sequence of Pickup Jobs (Java starter)
// Run: javac PickupJobs.java && java PickupJobs
// Implement the TODOs. Tests in main() should all pass when you're done.
// Focus of the drill: (1) a clean DP table, (2) reconstructing the actual
// SEQUENCE from the table, not just the length, (3) a stated tie-break so
// the answer is deterministic.
// Prep reconstruction from the locked plan notes (CodeZym 99), not a verbatim
// CodeZym spec — confirm the real method signatures at the link before the interview.
public class PickupJobs {

    // LCS length of the two ordered merchant lists. Say the recurrence BEFORE
    // coding: dp[i][j] = dp[i-1][j-1]+1 on match, else max(dp[i-1][j], dp[i][j-1]).
    public static int lcsLength(List<String> a, List<String> b) {
        // TODO
        return 0;
    }

    // The actual longest common sequence. Tie-break (state BEFORE coding):
    // when skipping from A vs skipping from B tie on length, skip B's job
    // first — the returned sequence prefers merchant A's jobs. That's what
    // makes test 1 deterministic.
    public static List<String> longestPickupSequence(List<String> a, List<String> b) {
        // TODO
        return new ArrayList<>();
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
