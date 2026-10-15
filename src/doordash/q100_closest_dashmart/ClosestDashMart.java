package doordash.q100_closest_dashmart;

import java.util.*;

// A city grid has open roads ('.'), blocked roads ('X'), DashMarts ('D') and
// optionally customers ('C'). Each city row looks like "X|.|.|D" and each
// location like "1,4" (also accepts "[1, 4]"). Movement is 4-directional.
// Part 1: distance from each location to its closest DashMart (-1 if outside
// the city, blocked, or unreachable). Follow-up: which DashMart serves the
// most customers (nearest wins; ties go to the smaller (row, col)).
//
// Source: https://codezym.com/question/100-find-closest-dashmart-distance
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/1-100/100-find-closest-dashmart-distance/q-100-java-tutorial.md
// (The tutorial names this class DashMartPlanner; the API below is identical.)
public class ClosestDashMart {

    // The four moves we are allowed to make: up, down, left, right
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    char[][] grid;   // the city as a 2-D grid of cells
    int rows, cols;

    // All DashMarts, found row by row and left to right.
    // So a smaller index always means a smaller (row, col) location.
    List<int[]> dashMarts;

    // distance[r][c] = steps from cell (r, c) to its closest DashMart, -1 if not reachable
    int[][] distance;

    // owner[r][c] = index in dashMarts of the DashMart that serves cell (r, c), -1 if none
    int[][] owner;

    public ClosestDashMart() {
    }

    public List<Integer> getClosestDashMartDistances(List<String> city, List<String> locations) {
        // TODO: parse the city, run one multi-source BFS from all DashMarts,
        // then answer each location with a table lookup (-1 when outside the city).
        return new ArrayList<>();
    }

    public List<Integer> findDashMartServingMaximumCustomers(List<String> city) {
        // TODO: parse the city, run the same BFS (it also records each cell's
        // owner), count the 'C' customers per DashMart, and return the winner's
        // [row, col]. Ties keep the smaller location; no DashMarts -> [-1, -1].
        return new ArrayList<>();
    }

    /** One BFS that starts from all DashMarts at the same time. Fills distance[][] and owner[][]. */
    void runBfsFromAllDashMarts() {
        // TODO: enqueue every DashMart at distance 0 (row-major order, so ties
        // go to the smaller location), then expand level by level.
    }

    /** Converts rows like "X|.|D" into a 2-D grid of cells. */
    void parseCity(List<String> city) {
        rows = city.size();
        cols = (rows == 0) ? 0 : city.get(0).split("\\|").length;
        grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            // "|" is a special character in regex, so it must be escaped
            String[] cells = city.get(r).split("\\|");
            for (int c = 0; c < cols; c++) {
                grid[r][c] = cells[c].trim().charAt(0);
            }
        }
    }

    boolean isInsideCity(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    /** Reads a location like "1,4" or "[1, 4]" and returns {row, col}. */
    int[] parseLocation(String location) {
        String[] parts = location.replaceAll("[^0-9-]+", " ").trim().split(" ");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        ClosestDashMart svc = new ClosestDashMart();

        List<String> city1 = List.of(
                "D|.|.",
                ".|X|.",
                ".|.|D");
        // (0,2) and (2,0) are 2 steps from both marts; ties go to (0,0).
        check("distances [0,2],[1,1],[2,0],[9,9],[0,0] -> [2,-1,2,-1,0]",
                svc.getClosestDashMartDistances(city1, List.of("0,2", "1,1", "2,0", "9,9", "0,0")),
                List.of(2, -1, 2, -1, 0));
        check("bracket location format [0, 2] -> 2",
                svc.getClosestDashMartDistances(city1, List.of("[0, 2]")),
                List.of(2));

        List<String> city2 = List.of(
                "D|.|.|C",
                ".|X|.|C",
                "C|.|.|D");
        // (0,3) and (1,3) are served by (2,3); (2,0) by (0,0). Winner: (2,3).
        check("max customers -> [2, 3]",
                svc.findDashMartServingMaximumCustomers(city2),
                List.of(2, 3));

        List<String> noMarts = List.of("X|X", ".|.");
        check("no marts -> distances all -1",
                svc.getClosestDashMartDistances(noMarts, List.of("0,0", "0,1", "1,0", "1,1")),
                List.of(-1, -1, -1, -1));
        check("no marts -> [-1, -1]",
                svc.findDashMartServingMaximumCustomers(noMarts),
                List.of(-1, -1));

        List<String> boxed = List.of("D|X|C");
        check("boxed-in customer -> unreachable -1",
                svc.getClosestDashMartDistances(boxed, List.of("0,2")),
                List.of(-1));
        // Nobody is served: every count is 0, so the smallest location (0,0) wins.
        check("nobody served -> [0, 0]",
                svc.findDashMartServingMaximumCustomers(boxed),
                List.of(0, 0));

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
