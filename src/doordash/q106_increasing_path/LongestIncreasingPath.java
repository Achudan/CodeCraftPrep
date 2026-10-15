package doordash.q106_increasing_path;

import java.util.*;

// Spec (DoorDash 2026 problem list, Q106 — quoted from the list):
// "You are given a row x column integer grid. Find the length of the longest strictly
//  increasing path in the grid. From any cell, you may move only to its immediate left,
//  right, up, or down neighbor. Diagonal movement is not allowed, and you cannot go
//  outside the grid. In addition to the maximum length, also return one longest strictly
//  increasing path, and return all strictly increasing paths."
// Source: https://codezym.com/question/106-longest-increasing-path-grid
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Readings chosen where the spec is silent (documented here, not from the spec):
// - "All strictly increasing paths" is read literally: every path of length >= 1 whose
//   values are strictly increasing — single cells included.
// - oneLongestPath returns the cells as int[]{row, col}, from start to end.
public class LongestIncreasingPath {

    // Length of the longest strictly increasing path (4-directional, no diagonals).
    public static int longestPathLength(int[][] grid) {
        // TODO
        return 0;
    }

    // One longest strictly increasing path, as a list of {row, col} cells.
    public static List<int[]> oneLongestPath(int[][] grid) {
        // TODO
        return new ArrayList<>();
    }

    // Every strictly increasing path in the grid (each as a list of {row, col}).
    public static List<List<int[]>> allIncreasingPaths(int[][] grid) {
        // TODO
        return new ArrayList<>();
    }

    // ---- Test helpers (not part of the drill) ----

    private static String fmt(List<int[]> path) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append("(").append(path.get(i)[0]).append(",").append(path.get(i)[1]).append(")");
        }
        return sb.append("]").toString();
    }

    private static boolean isValidIncreasingPath(int[][] grid, List<int[]> path) {
        if (path.isEmpty()) return false;
        for (int i = 0; i < path.size(); i++) {
            int r = path.get(i)[0], c = path.get(i)[1];
            if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return false;
            if (i > 0) {
                int pr = path.get(i - 1)[0], pc = path.get(i - 1)[1];
                if (Math.abs(r - pr) + Math.abs(c - pc) != 1) return false; // must be orthogonal neighbors
                if (grid[r][c] <= grid[pr][pc]) return false;              // must be strictly increasing
            }
        }
        return true;
    }

    private static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Classic example: longest path is 1 -> 2 -> 6 -> 9, length 4.
        int[][] g = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        check("classic grid length -> 4", longestPathLength(g), 4);

        List<int[]> one = oneLongestPath(g);
        check("one longest path has 4 cells", one.size(), 4);
        check("one longest path is valid and strictly increasing",
                isValidIncreasingPath(g, one), true);

        check("1x1 grid length -> 1", longestPathLength(new int[][]{{7}}), 1);
        check("1x1 one path -> [(0,0)]", fmt(oneLongestPath(new int[][]{{7}})), "[(0,0)]");

        // All strictly increasing paths on a tiny 2x2 grid (values unique, so
        // value-strings identify paths unambiguously):
        // singles: 1, 2, 3, 4; pairs: 1->2, 1->3, 2->4, 3->4; triples: 1->2->4, 1->3->4.
        int[][] tiny = {{1, 2}, {3, 4}};
        List<String> got = new ArrayList<>();
        for (List<int[]> path : allIncreasingPaths(tiny)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < path.size(); i++) {
                if (i > 0) sb.append("->");
                sb.append(tiny[path.get(i)[0]][path.get(i)[1]]);
            }
            got.add(sb.toString());
        }
        Collections.sort(got);
        check("all paths on 2x2 -> the 10 increasing paths",
                got, List.of("1", "1->2", "1->2->4", "1->3", "1->3->4",
                             "2", "2->4", "3", "3->4", "4"));

        System.out.println(anyFail ? "\nSome tests FAILING — implement the TODOs."
                                   : "\nAll tests PASS.");
    }
}
