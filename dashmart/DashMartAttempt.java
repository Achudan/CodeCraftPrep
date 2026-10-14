import java.util.*;

// Closest DashMart — YOUR attempt, clean slate.
// Implement the methods below, then copy them into DashMart.java
// (replacing the TODOs) to run the built-in tests.
public class DashMartAttempt {

    public record Cell(int r, int c) {}

    public record Analysis(int[][] dist, int[][] owner, List<Cell> marts) {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static List<Cell> findMarts(char[][] grid) {
        List<Cell> marts = new ArrayList<>();
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[r].length; c++)
                if (grid[r][c] == 'M') marts.add(new Cell(r, c));
        return marts;
    }

    public static Analysis analyze(char[][] grid) {
        List<Cell> marts = findMarts(grid);
        int R = grid.length, C = grid[0].length;
        int[][] dist = new int[R][C];
        int[][] owner = new int[R][C];
        for (int r = 0; r < R; r++) {
            Arrays.fill(dist[r], -1);
            Arrays.fill(owner[r], -1);
        }
        return new Analysis(dist, owner, marts);
    }

    public static int closestDashMartDist(char[][] grid, int r, int c) {
        return -1;
    }

    public static int busiestMartIndex(char[][] grid, List<Cell> customers) {
        return -1;
    }
}
