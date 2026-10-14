# DoorDash CodeCraft — Day 4 (Mon Oct 12): DashMart BFS + Menu Tree Diff, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · two timed blocks
Questions: CodeZym 100 (Closest DashMart) + CodeZym 105 (Changed Nodes in Menu Tree), per your locked plan (`doordash-codecraft-plan.md`)

Same setup as Days 1–3: no Maven, no IDE needed, tests fail until you fill in the TODOs — that's the drill. Both are faithful prep reconstructions from the plan notes, not verbatim CodeZym copies — confirm the real method signatures at the links when you open the questions.

## Run the starters
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day4
javac DashMart.java && java DashMart
javac MenuTreeDiff.java && java MenuTreeDiff
```

## Block A (45 min, timed) — Closest DashMart: multi-source BFS
- **0–5 min — Requirements out loud:** Grid cells are `M` (DashMart), `.` (open), `#` (blocked). For any customer cell: steps to the nearest mart through open cells, or -1 if blocked/unreachable. Then: which mart serves the most customers (each customer goes to its nearest mart). State your tie-breaks BEFORE coding: (1) a cell equidistant to two marts belongs to the earliest mart in row-major order; (2) a customer-count tie goes to the smallest mart index.
- **5–10 min — Sketch:** One BFS seeded with ALL marts at distance 0 — that's the whole trick, no per-mart loops. Two int grids: `dist` (init -1) and `owner` (init -1). Queue holds (r, c, martIndex); the first source to claim a cell owns it, which is exactly tie-break (1) because marts were enqueued row-major. `#` cells are never enqueued.
- **10–35 min — Implement in this order:** `findMarts` is given → `analyze` (the BFS) → `closestDashMartDist` (bounds check, then read `dist`) → `busiestMartIndex` (count `owner` per customer, skip -1s, argmax with tie → smallest index). Run tests after each method.
- **35–45 min — Trade-offs out loud:** BFS is O(rows × cols) time and memory; the `owner` grid is the "serving" answer for free afterward. If customers arrive online, you'd keep the BFS result and answer each in O(1). With more time: weighted cells (traffic) → Dijkstra instead of BFS; nearest-k marts instead of one.

## Tests already in the DashMart starter (say expected values out loud first)
- `(2,0)` → 2 steps from mart A via `(1,0)`; `(1,3)` → 2 steps from mart B via `(2,3)` — check both directions of the wall
- `(0,2)` is 2 steps from BOTH marts → tie-break gives it to mart A (index 0); `owner[0][2] == 0` is the test that catches a missing tie-break
- Blocked `(1,1)`, `(0,3)` and out-of-bounds → -1
- Busiest with customers `(2,0),(0,2),(1,3)` → mart 0 (2 vs 1); the 1–1 tie `[(2,0),(1,3)]` → 0 (smallest index wins)
- A blocked customer `(1,1)` serves nobody — mixed with `(1,3)` the answer is mart 1; empty customer list → -1
- Grid with no marts at all → everything -1

## Block B (40 min, timed) — Changed Nodes in Menu Tree
- **0–5 min — Requirements out loud:** Lines look like `key,value,childKey1;childKey2` (children segment optional). Lines can come in ANY order. Diff two trees by key: added (new only), deleted (old only), changed (same key, different value). State the rules BEFORE coding: root = the key never named as anyone's child; a pure re-parenting or child-list change alone does NOT count as "changed"; duplicate keys → last line wins; values contain no commas.
- **5–10 min — Sketch:** Two-pass parse — pass 1 builds every `Node` and remembers each key's child-key list; pass 2 wires children (this is the "any order" answer). Flatten each tree to `key → value` with a DFS, then compare maps. Sort all three output lists for a deterministic answer.
- **10–30 min — Implement in this order:** `parse` (two-pass) → a `flatten` helper → `diff` (added/deleted/changed + sort). Run tests after parse, before diff.
- **30–40 min — Trade-offs out loud:** O(n) parse + O(n) diff, O(n) memory. Key insight to narrate: diffing by key means structure is irrelevant except for finding the root — that's why child-list changes don't flag. With more time: report moved subtrees as their own bucket; stream the diff for trees too big for memory.

## Tests already in the MenuTreeDiff starter (say expected values out loud first)
- Shuffled lines (root NOT first) still parse: root key `root`, children `[pizza, drinks]` in line order
- Old vs new: added `[cake, dessert]`, deleted `[drinks, soda]`, changed `[pizza]` — and note root is NOT changed (its child list changed, its value didn't)
- Identical trees → all three lists empty
- Value-only change (`b`: `2` → `3`, same keys/structure) → changed `[b]`, added/deleted empty

## Traps to narrate, not fall into
- **DashMart:** Running one BFS per mart instead of one multi-source BFS (that's the pattern being tested — say "multi-source" in the first minute); forgetting the tie-break rule and letting HashMap/FIFO order decide it silently; counting blocked customers in the busiest tally
- **MenuTree:** Wiring children in a single pass and breaking on shuffled line order; diffing by tree position instead of by key; flagging the root as "changed" when only its child list changed

## Tomorrow (Tue Oct 13) — Day 5 preview
Longest Sequence of Pickup Jobs (LCS over two ordered merchant lists) + Longest Increasing Path in a Grid (DFS + memoization, return the path too), both timed. No prep needed tonight — it'll be ready the same way.
