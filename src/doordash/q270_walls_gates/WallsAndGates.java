package doordash.q270_walls_gates;

import java.util.*;

// Q270 — Walls and Nearest Gate for Empty Rooms (EXERCISE — fill the TODO).
//
// Spec (from the DoorDash 2026 problem list):
//   "You are given a rectangular grid containing walls, gates, and empty
//    rooms. Fill every reachable empty room with the minimum number of steps
//    required to reach a gate."
// Source: https://codezym.com/question/270-walls-nearest-gate-empty-rooms
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented reading of the encoding (standard Walls and Gates / LeetCode 286):
//   -1  = wall (never entered, never overwritten)
//    0  = gate (distance 0, a BFS source)
//   INF = empty room, to be filled with the minimum steps to the nearest gate.
//         Use Integer.MAX_VALUE as INF. Rooms unreachable from any gate keep INF.
// The grid is modified in place. Movement is 4-directional.
public class WallsAndGates {

    // Fill every reachable empty room (INF) with its minimum steps to a gate.
    // TODO: multi-source BFS — enqueue all gates at distance 0, then expand.
    public static void wallsAndGates(int[][] rooms) {
        // TODO
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        int INF = Integer.MAX_VALUE;
        int[][] rooms = {
            {INF, -1,   0, INF},
            {INF, INF, INF, -1},
            {INF, -1,  INF, -1},
            {0,   -1,  INF, INF},
        };
        wallsAndGates(rooms);

        check("room (0,0) -> 3 steps", rooms[0][0], 3);
        check("room (0,3) -> 1 step", rooms[0][3], 1);
        check("room (3,3) -> 4 steps", rooms[3][3], 4);
        check("wall (0,1) stays -1", rooms[0][1], -1);
        check("gate (0,2) stays 0", rooms[0][2], 0);
        check("gate (3,0) stays 0", rooms[3][0], 0);

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
