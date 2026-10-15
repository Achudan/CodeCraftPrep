package doordash.q275_dasher_coverage;

import java.util.*;

// Q275 — Dasher Coverage of Connected User Areas (EXERCISE — fill the TODO).
//
// Spec (from the DoorDash 2026 problem list):
//   "You are given two binary grids representing Dasher availability and
//    user locations. Count how many user clusters are fully covered by
//    available Dashers."
// Source: https://codezym.com/question/275-dasher-coverage-connected-user-area
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented readings (the spec leaves "fully covered" open; stated here so
// the tests below are unambiguous):
//   - Both grids are non-null, rectangular, and have identical dimensions.
//   - A "user cluster" is a 4-connected component of 1-cells in the user grid.
//   - A user cell (r, c) is covered iff dasherGrid[r][c] == 1 (a dasher is
//     available on that exact cell).
//   - A cluster is "fully covered" iff EVERY user cell in the cluster is
//     covered. Partially covered clusters do not count.
public class DasherCoverage {

    // Count user clusters (4-connected components of 1s in userGrid) in which
    // every user cell has dasherGrid[r][c] == 1.
    // TODO: find components (BFS/DFS), check coverage per component, count.
    public static int countFullyCoveredClusters(int[][] dasherGrid, int[][] userGrid) {
        // TODO
        return 0;
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Two user clusters: {(0,0),(0,1)} and {(2,1)}.
        int[][] users = {
            {1, 1, 0},
            {0, 0, 0},
            {0, 1, 0},
        };
        // Dashers cover the first cluster fully, but not (2,1).
        int[][] dashers = {
            {1, 1, 0},
            {0, 0, 0},
            {0, 0, 0},
        };
        check("one of two clusters fully covered -> 1",
                countFullyCoveredClusters(dashers, users), 1);

        int[][] dashers2 = {
            {1, 1, 0},
            {0, 0, 0},
            {0, 1, 0},
        };
        check("both clusters fully covered -> 2",
                countFullyCoveredClusters(dashers2, users), 2);

        // Only (0,0) has a dasher: first cluster is only partially covered.
        int[][] dashers3 = {
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
        };
        check("partial coverage counts nothing -> 0",
                countFullyCoveredClusters(dashers3, users), 0);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
