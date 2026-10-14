import java.util.*;

// DoorDash CodeCraft Day 5 (part B) — Longest Increasing Path in a Grid (Java starter)
// Run: javac LongestIncreasingPath.java && java LongestIncreasingPath
// Implement the TODOs. Tests in main() should all pass when you're done.
// Focus of the drill: (1) DFS + memoization (the naive DFS re-explores and
// times out), (2) RETURNING THE PATH, not just the length, (3) stated
// tie-breaks so the path is deterministic.
// Prep reconstruction from the locked plan notes (CodeZym 106), not a verbatim
// CodeZym spec — confirm the real method signatures at the link before the interview.
public class LongestIncreasingPath {

    private static final int[][] DIRS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; // up, left, right, down

    // Length of the longest strictly-increasing path (4-directional moves).
    public static int longestPathLength(int[][] grid) {
        // TODO
        return 0;
    }

    // The actual path as a list of (row, col) int[] cells, start to end.
    // Tie-breaks (state BEFORE coding): (1) if several cells tie for the
    // global max length, start at the topmost, then leftmost; (2) when
    // several next-cells tie, take the first in DIRS order (up, left,
    // right, down).
    public static List<int[]> longestPath(int[][] grid) {
        // TODO
        return new ArrayList<>();
    }

    // ---- Test harness ----

    private static boolean anyFail = false;

    private static String fmt(List<int[]> path) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < path.size(); i++) {
            int[] c = path.get(i);
            sb.append("(").append(c[0]).append(",").append(c[1]).append(")");
            if (i < path.size() - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        int[][] g1 = {{9, 9, 4}, {6, 6, 8}, {2, 1, 1}};
        check("g1 length -> 4", longestPathLength(g1), 4);
        // 1 -> 2 -> 6 -> 9 : (2,1)->(2,0)->(1,0)->(0,0)
        check("g1 path -> [(2,1), (2,0), (1,0), (0,0)]",
              fmt(longestPath(g1)), "[(2,1), (2,0), (1,0), (0,0)]");

        int[][] g2 = {{3, 4, 5}, {3, 2, 6}, {2, 2, 1}};
        check("g2 length -> 4", longestPathLength(g2), 4);
        // 3 -> 4 -> 5 -> 6 : (0,0)->(0,1)->(0,2)->(1,2)
        check("g2 path -> [(0,0), (0,1), (0,2), (1,2)]",
              fmt(longestPath(g2)), "[(0,0), (0,1), (0,2), (1,2)]");

        check("single cell -> 1", longestPathLength(new int[][]{{7}}), 1);
        check("single cell path -> [(0,0)]",
              fmt(longestPath(new int[][]{{7}})), "[(0,0)]");

        check("flat grid -> 1", longestPathLength(new int[][]{{5, 5}, {5, 5}}), 1);
        // Tie-break (1): topmost-leftmost of the four length-1 cells.
        check("flat grid path -> [(0,0)]",
              fmt(longestPath(new int[][]{{5, 5}, {5, 5}})), "[(0,0)]");

        check("null grid -> 0", longestPathLength(null), 0);
        check("null grid path -> []", fmt(longestPath(null)), "[]");
        check("empty grid -> 0", longestPathLength(new int[0][0]), 0);

        System.out.println(anyFail ? "\nSome tests FAILED — keep going." : "\nAll tests PASS.");
        if (anyFail) System.exit(1);
    }
}
