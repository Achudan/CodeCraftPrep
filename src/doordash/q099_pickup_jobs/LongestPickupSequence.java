package doordash.q099_pickup_jobs;

import java.util.*;

// Two dashers want to dash together in one car. Each has an ordered list of
// merchant pickup names. Find the longest sequence of pickups both dashers can
// complete together, preserving left-to-right order in both lists (skips allowed).
// Ties are broken by dictionary order: the lexicographically smallest longest
// plan wins (compare with String.compareTo).
//
// Source: https://codezym.com/question/99-find-longest-sequence-pickup-jobs-dashers
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/1-100/99-find-longest-sequence-pickup-jobs-dashers/q-99-java-tutorial.md
public class LongestPickupSequence {

    public LongestPickupSequence() {
    }

    /**
     * Returns the longest common pickup sequence of the two dasher lists.
     * "Best" means longest first; among equally long plans, the
     * lexicographically smallest one.
     */
    public List<String> longestPickupSequence(List<String> dasher1Pickups, List<String> dasher2Pickups) {
        // TODO: dynamic programming. Hint from the tutorial: fill a table of
        // lengths for every suffix pair (i, j), then build the answer greedily
        // from the front, always taking the smallest merchant that keeps the
        // maximum length reachable.
        return new ArrayList<>();
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        LongestPickupSequence svc = new LongestPickupSequence();

        // Tutorial Example 4: ["a","b"] and ["a","c"] both have length 2; ["a","b"] is smaller.
        check("example4 -> [a, b]",
                svc.longestPickupSequence(List.of("a", "b", "c"), List.of("a", "c", "b")),
                List.of("a", "b"));
        check("identical lists -> full list",
                svc.longestPickupSequence(List.of("panda", "panera"), List.of("panda", "panera")),
                List.of("panda", "panera"));
        check("no common pickup -> []",
                svc.longestPickupSequence(List.of("a", "b"), List.of("c", "d")),
                List.of());
        check("empty list -> []",
                svc.longestPickupSequence(List.of(), List.of("a")),
                List.of());
        check("single common -> [y]",
                svc.longestPickupSequence(List.of("x", "y", "z"), List.of("q", "y", "w")),
                List.of("y"));
        check("shared prefix only -> [pan]",
                svc.longestPickupSequence(List.of("pan", "panda"), List.of("pan", "panera")),
                List.of("pan"));

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
