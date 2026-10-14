import java.util.*;

// Longest Increasing Path in a Grid — REFERENCE SOLUTION (all tests pass).
// Run: javac LongestIncreasingPathSolution.java && java LongestIncreasingPathSolution
// The exercise version is LongestIncreasingPath.java (same tests, TODOs for you).
public class LongestIncreasingPathSolution {

    private static final int[][] DIRS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; // up, left, right, down

    // Memoized DFS: longest strictly-increasing path STARTING at (r, c).
    private static int dfs(int[][] g, int[][] memo, int r, int c) {
        if (memo[r][c] != 0) return memo[r][c];
        int best = 1;
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < g.length && nc >= 0 && nc < g[0].length && g[nr][nc] > g[r][c]) {
                best = Math.max(best, 1 + dfs(g, memo, nr, nc));
            }
        }
        memo[r][c] = best;
        return best;
    }

    private static int[][] memoFor(int[][] grid) {
        int[][] memo = new int[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[0].length; c++)
                dfs(grid, memo, r, c);
        return memo;
    }

    // Length of the longest strictly-increasing path (4-directional moves).
    public static int longestPathLength(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int[][] memo = memoFor(grid);
        int best = 0;
        for (int[] row : memo)
            for (int v : row)
                best = Math.max(best, v);
        return best;
    }

    // The actual path as a list of (row, col) int[] cells, start to end.
    // Tie-breaks (state BEFORE coding): (1) if several cells tie for the
    // global max length, start at the topmost, then leftmost; (2) when
    // several next-cells tie, take the first in DIRS order (up, left,
    // right, down).
    public static List<int[]> longestPath(int[][] grid) {
        List<int[]> path = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) return path;
        int R = grid.length, C = grid[0].length;
        int[][] memo = memoFor(grid);
        // Tie-break 1: strict > keeps the topmost, then leftmost max cell.
        int sr = 0, sc = 0, best = 0;
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++)
                if (memo[r][c] > best) { best = memo[r][c]; sr = r; sc = c; }
        // Walk downhill in memo: each step takes a greater-valued neighbor
        // whose memo is exactly one less, first in DIRS order (tie-break 2).
        int r = sr, c = sc;
        while (true) {
            path.add(new int[]{r, c});
            if (memo[r][c] == 1) break;
            boolean moved = false;
            for (int[] d : DIRS) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < R && nc >= 0 && nc < C
                        && grid[nr][nc] > grid[r][c] && memo[nr][nc] == memo[r][c] - 1) {
                    r = nr; c = nc; moved = true; break;
                }
            }
            if (!moved) break; // safety; cannot happen on a correct memo table
        }
        return path;
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
