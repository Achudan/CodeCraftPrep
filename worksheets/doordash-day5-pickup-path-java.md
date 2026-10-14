# DoorDash CodeCraft — Day 5 (Tue Oct 13): Pickup Jobs + Longest Increasing Path, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · two timed blocks
Questions: CodeZym 99 (Longest Sequence of Pickup Jobs) + CodeZym 106 (Longest Increasing Path in a Grid), per your locked plan (`doordash-codecraft-plan.md`)

Same setup as Days 1–4: no Maven, no IDE needed, tests fail until you fill in the TODOs — that's the drill. Both are faithful prep reconstructions from the plan notes, not verbatim CodeZym copies — confirm the real method signatures at the links when you open the questions.

## Run the starters
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day5
javac PickupJobs.java && java PickupJobs
javac LongestIncreasingPath.java && java LongestIncreasingPath
```
(Note: run from your machine — it has the JDK. Use the same two-file split as Day 4: one method per problem file, tests in `main`.)

## Block A (45 min, timed) — Longest Sequence of Pickup Jobs: LCS
- **0–5 min — Requirements out loud:** Two ordered merchant lists (jobs). Find the longest sequence of pickup jobs both merchants share, in order. Return the SEQUENCE, not just the length. State your tie-break BEFORE coding: when skipping from A vs skipping from B give the same length, skip B's job first (the answer prefers A's jobs) — that's what makes the output deterministic.
- **5–10 min — Sketch:** Classic DP: `dp[i][j]` = LCS length of `A[0..i)`, `B[0..j)`. Match → `dp[i-1][j-1]+1`, else `max(dp[i-1][j], dp[i][j-1])`. Reconstruction walks back from `dp[n][m]`: match → take the job; else move toward the larger cell (ties → skip B first). O(n·m) time and memory.
- **10–35 min — Implement in this order:** `lcsLength` (the table) → `longestPickupSequence` (the walk-back). Run tests after the table, before the reconstruction.
- **35–45 min — Trade-offs out loud:** The walk-back is the part people fumble — narrate it. O(n·m) memory is fine for lists this size; if the lists were huge you'd do rolling-array for the length only (Hirschberg's trick for the sequence). This is the same DP muscle as diffing menu trees by key — you've done that shape before.

## Tests already in the PickupJobs starter (say expected values out loud first)
- `A=[p1,p2,p3,p4]`, `B=[p2,p4,p3]` → length 2, sequence `[p2, p4]` — candidates are `[p2,p3]`/`[p2,p4]`; the tie-break keeps A's `p4`
- Identical lists → the whole list; no overlap → 0 and `[]`; empty A → 0 and `[]`
- Reversed lists `[a,b,c,d]` / `[d,c,b,a]` → length 1, sequence `[d]` (trace WHY — ties skip from B's end first, landing on rb's first job in A)
- `[a,b,c,d,e]` vs `[b,d]` → `[b, d]` (order preserved, no re-sorting)

## Block B (45 min, timed) — Longest Increasing Path: DFS + memoization
- **0–5 min — Requirements out loud:** Grid of integers; move 4-directionally through strictly increasing values. Return the LENGTH and the PATH (list of cells, start to end). State your tie-breaks BEFORE coding: (1) several cells tie for global max → start topmost, then leftmost; (2) several next-cells tie → first in priority order up, left, right, down. Null/empty grid → 0 / empty path (state the edge rule, don't let it surprise you).
- **5–10 min — Sketch:** `memo[r][c]` = longest path starting at (r,c): `1 + max(memo of increasing neighbors)`. Plain DFS re-explores and times out — memoization is the whole point. Reconstruct by walking from the start cell to the neighbor with `memo == memo[cur]-1` (apply tie-break 2 at each step).
- **10–35 min — Implement in this order:** `longestPathLength` (the memoized DFS) → `longestPath` (the memo walk). Run tests after the length, before the path.
- **35–45 min — Trade-offs out loud:** O(rows × cols) time — every cell computed once — and O(rows × cols) memory for the memo. Recursion depth is bounded by path length (fine here; on a 10⁵-cell grid you'd worry about stack). This is Day 4's multi-source BFS cousin: graph search where the "trick" (memo/queue seeding) does the heavy lifting.

## Tests already in the LongestIncreasingPath starter (say expected values out loud first)
- `[[9,9,4],[6,6,8],[2,1,1]]` → length 4, path `[(2,1), (2,0), (1,0), (0,0)]` = 1→2→6→9 (check the memo of (2,1): why is it 4? say it)
- `[[3,4,5],[3,2,6],[2,2,1]]` → length 4, path `[(0,0), (0,1), (0,2), (1,2)]` = 3→4→5→6
- Single cell → 1 / `[(0,0)]`; flat grid → 1, path starts at `(0,0)` (tie-break 1); null and empty grids → 0 / `[]`

## After the blocks (20 min, untimed) — knapsack + trie skim
- **Maximum Calories within Budget (unbounded knapsack, CodeZym 101):** sketch `dp[w] = max value for capacity w` out loud once — reusable items means the loop order is capacity-outer, items-inner. No code; just make the recurrence sayable.
- **Restaurant Search Engine (trie, CodeZym 100-series sibling):** insert + prefix-walk + collect suggestions. Narrate the three methods' shapes — if the interview goes here, you've already said the words.

## Traps to narrate, not fall into
- **Pickup Jobs:** Returning only the length (the question wants the sequence); reconstruction tie-break decided silently by `>` vs `>=` — state it; rebuilding the DP backwards wrong by forgetting the match-check happens at `(i-1, j-1)`
- **Longest Increasing Path:** Plain DFS without memo (mention the timeout risk in the first minute); "increasing" vs "non-decreasing" — `>` is strict, equal values break the chain; recursion without a visited guard is fine BECAUSE strictly-increasing values can't cycle — say that, it's a good interview line

## Tomorrow (Wed Oct 14) — Day 6 preview
Full mock: one Tier 1 CodeCraft question, cold, 45 min, out loud (Java + IDE). Then a redo of whichever Tier 1 felt weakest across Days 1–5. Nothing to build tonight — it'll be ready the same way.
