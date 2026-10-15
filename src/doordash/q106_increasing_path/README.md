# Longest Path and Increasing Paths in a Grid

**CodeZym:** https://codezym.com/question/106-longest-increasing-path-grid
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are given a row x column integer grid. Find the length of the longest strictly increasing path in the grid.

From any cell, you may move only to its immediate left, right, up, or down neighbor. Diagonal movement is not allowed, and you cannot go outside the grid.

In addition to the maximum length, also return one longest strictly increasing path, and return all strictly increasing paths.

## Notes

"All strictly increasing paths" is read literally: every path of length ≥ 1 whose values are strictly increasing — single cells count. (On a 2×2 grid of `1 2 / 3 4`, that's exactly 10 paths, which the exercise's test enumerates.) The classic `{{9,9,4},{6,6,8},{2,1,1}}` grid → length 4.
