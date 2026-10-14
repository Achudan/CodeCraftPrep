# Longest Increasing Path in a Grid

The classic LeetCode 329, framed as dasher routing: the longest strictly
increasing path you can walk with 4-directional moves.

## The problem

- `longestPathLength(grid)` — length of the longest strictly-increasing path
- `longestPath(grid)` — the actual path as `(row, col)` cells, start to end

**Tie-breaks (state both before coding):**

1. Several cells tie for the global max length → start at the **topmost, then
   leftmost**.
2. Several next-cells tie → take the first in DIRS order
   (up, left, right, down).

## Key ideas

1. **DFS + memoization.** Naive DFS re-explores exponentially and times out;
   memo makes it O(RC). Say that sentence — it's the expected "why" answer.
2. `memo[r][c] = 1 + max(memo of greater neighbors)`, computed once.
3. Path reconstruction walks **downhill in memo**: from the start cell, each
   step takes a greater-valued neighbor whose memo is exactly one less, first
   in DIRS order.

## Files

| File | What it is |
|---|---|
| `LongestIncreasingPath.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `LongestIncreasingPathAttempt.java` | **Your attempt.** Clean slate — implement here, then copy your methods into the exercise file to run the tests. |
| `LongestIncreasingPathSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd longest-increasing-path
javac LongestIncreasingPath.java && java LongestIncreasingPath
javac LongestIncreasingPathSolution.java && java LongestIncreasingPathSolution
```

## Interview tips

- "Why memo?" is the whole question. The path reconstruction is the bonus
  that separates a pass from a strong hire.
- Null / empty grid → 0 and `[]`. Handle it before the loops, not inside them.
