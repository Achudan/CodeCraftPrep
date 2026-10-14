import java.util.*;

// Closest DashMart (exercise — fill the TODOs, make the tests pass)
// Run: javac DashMart.java && java DashMart
// Implement the TODOs. Tests in main() should all pass when you're done.
// Focus of the drill: (1) multi-source BFS on a grid with blocked cells,
// (2) nearest-mart ownership with a stated tie-break, (3) a "serves the most
// customers" aggregation on top of the BFS result.
// Prep reconstruction from the locked plan notes (CodeZym 100), not a verbatim
// CodeZym spec — confirm the real method signatures at the link before the interview.
public class DashMart {

    public record Cell(int r, int c) {}

    // dist[r][c]  = steps to the nearest DashMart (-1 if blocked or unreachable).
    // owner[r][c] = index into marts() of that nearest mart (-1 if none).
    public record Analysis(int[][] dist, int[][] owner, List<Cell> marts) {}

    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    // ---- Scaffolding (done; this is not the drill) ----

    // Mart cells in row-major order. 'M' = DashMart, '.' = open, '#' = blocked.
    public static List<Cell> findMarts(char[][] grid) {
        List<Cell> marts = new ArrayList<>();
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[r].length; c++)
                if (grid[r][c] == 'M') marts.add(new Cell(r, c));
        return marts;
    }

    // ---- The drill ----

    // Part 1: multi-source BFS. Enqueue every mart at distance 0, then expand.
    // The FIRST source to reach a cell owns it — say the tie-break out loud:
    // equal-distance cells go to the earliest mart in row-major order.
    // '#' cells are never entered; dist/owner stay -1 there.
    public static Analysis analyze(char[][] grid) {
        List<Cell> marts = findMarts(grid);
        int R = grid.length, C = grid[0].length;
        int[][] dist = new int[R][C];
        int[][] owner = new int[R][C];
        for (int r = 0; r < R; r++) {
            Arrays.fill(dist[r], -1);
            Arrays.fill(owner[r], -1);
        }
        // TODO: multi-source BFS over dist/owner.
        return new Analysis(dist, owner, marts);
    }

    // Part 2: distance from one customer cell to its nearest mart.
    // -1 when the cell is out of bounds, blocked, or no mart can reach it.
    public static int closestDashMartDist(char[][] grid, int r, int c) {
        // TODO: reuse analyze()
        return -1;
    }

    // Part 3: which mart serves the most customers (nearest mart wins each
    // customer). Returns the mart's index in findMarts order; ties go to the
    // SMALLEST index (say it). Customers on blocked/unreachable cells serve
    // nobody. -1 when no customer is served.
    public static int busiestMartIndex(char[][] grid, List<Cell> customers) {
        // TODO: reuse analyze(), count owners, argmax with tie-break
        return -1;
    }

    // ---- Test harness ----

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    private static char[][] gridOf(String... rows) {
        char[][] g = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) g[i] = rows[i].toCharArray();
        return g;
    }

    public static void main(String[] args) {
        // Marts at (0,0) and (2,2); '#' cells are walls.
        char[][] grid = gridOf(
            "M..#",
            ".#..",
            "..M."
        );

        check("mart count -> 2", findMarts(grid).size(), 2);

        // Say these out loud BEFORE running: (2,0) is 2 steps from mart A
        // via (1,0); (1,3) is 2 steps from mart B via (2,3); (0,2) is 2 steps
        // from BOTH marts, so the row-major tie-break gives it to mart A.
        check("closest (2,0) -> 2", closestDashMartDist(grid, 2, 0), 2);
        check("closest (1,3) -> 2", closestDashMartDist(grid, 1, 3), 2);
        check("closest (0,2) tie -> 2", closestDashMartDist(grid, 0, 2), 2);
        check("on a mart (0,0) -> 0", closestDashMartDist(grid, 0, 0), 0);
        check("blocked (1,1) -> -1", closestDashMartDist(grid, 1, 1), -1);
        check("blocked (0,3) -> -1", closestDashMartDist(grid, 0, 3), -1);
        check("out of bounds -> -1", closestDashMartDist(grid, 9, 9), -1);

        Analysis a = analyze(grid);
        check("owner of tied (0,2) -> mart 0", a.owner()[0][2], 0);
        check("owner of (2,3) -> mart 1", a.owner()[2][3], 1);
        check("dist of (0,2) -> 2", a.dist()[0][2], 2);

        // (2,0) and (0,2) nearest mart A; (1,3) nearest mart B -> mart A wins.
        check("busiest [(2,0),(0,2),(1,3)] -> 0",
                busiestMartIndex(grid, List.of(new Cell(2, 0), new Cell(0, 2), new Cell(1, 3))), 0);
        // 1-1 tie -> smallest index.
        check("busiest tie [(2,0),(1,3)] -> 0",
                busiestMartIndex(grid, List.of(new Cell(2, 0), new Cell(1, 3))), 0);
        // (1,1) is blocked and serves nobody; (1,3) -> mart B.
        check("busiest with blocked customer -> 1",
                busiestMartIndex(grid, List.of(new Cell(1, 1), new Cell(1, 3))), 1);
        check("busiest with no customers -> -1", busiestMartIndex(grid, List.of()), -1);

        // A grid with no marts at all: everything is -1.
        char[][] noMarts = gridOf("..", "..");
        check("no marts -> closest -1", closestDashMartDist(noMarts, 0, 0), -1);
        check("no marts -> busiest -1", busiestMartIndex(noMarts, List.of(new Cell(0, 0))), -1);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop.");
    }
}
