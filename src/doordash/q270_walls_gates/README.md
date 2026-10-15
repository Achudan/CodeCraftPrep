# Walls and Nearest Gate for Empty Rooms

**CodeZym:** https://codezym.com/question/270-walls-nearest-gate-empty-rooms
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are given a rectangular grid containing walls, gates, and empty rooms. Fill every reachable empty room with the minimum number of steps required to reach a gate.

## Notes

The spec gives no cell encoding, so the exercise uses the standard Walls-and-Gates encoding:

- `-1` = wall
- `0` = gate
- `Integer.MAX_VALUE` = empty room

Fill in place; rooms unreachable from any gate keep `INF`. Movement is 4-directional. Tests use the classic 4×4 example.
