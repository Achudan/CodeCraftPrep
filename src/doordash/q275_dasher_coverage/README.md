# Dasher Coverage of Connected User Areas

**CodeZym:** https://codezym.com/question/275-dasher-coverage-connected-user-area
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are given two binary grids representing Dasher availability and user locations. Count how many user clusters are fully covered by available Dashers.

## Notes

"Fully covered" is undefined in the spec; the exercise documents this reading:

- Both grids have the same dimensions.
- A user cluster = a 4-connected component of `1`s in the user grid.
- A user cell is covered iff the dasher grid has a `1` at that exact cell.
- A cluster counts iff **every** cell in it is covered; partially covered clusters count as 0.
