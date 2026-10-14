# Closest DashMart

Multi-source BFS on a city grid: which DashMart is closest to each customer?

## The problem

Grid cells: `M` = DashMart, `.` = open, `#` = blocked.

- `analyze(grid)` — for every cell, the distance to the nearest mart and which
  mart owns it (multi-source BFS)
- `closestDashMartDist(grid, r, c)` — distance from one cell to its nearest
  mart; `-1` if out of bounds, blocked, or unreachable
- `busiestMartIndex(grid, customers)` — which mart serves the most customers
  (nearest mart wins each customer); ties → smallest index; `-1` if nobody is
  served

**Tie-break (state it before coding):** a cell at equal distance from two marts
belongs to the mart that appears **earliest in row-major order**.

## Key ideas

1. **Multi-source BFS**: enqueue *every* mart at distance 0, then expand
   normally. First source to reach a cell owns it.
2. The tie-break falls out of the BFS for free **if marts are enqueued in
   row-major order** and cells are marked visited on enqueue.
3. `'#'` cells are never entered; `dist`/`owner` stay `-1` there.
4. Parts 2 and 3 just **reuse `analyze()`** — say that out loud; it shows you
   design before you code.

## Files

| File | What it is |
|---|---|
| `DashMart.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `DashMartSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd dashmart
javac DashMart.java && java DashMart
javac DashMartSolution.java && java DashMartSolution
```

## Interview tips

- The #1 mistake is running one BFS per mart (O(m·RC)). Multi-source is one
  BFS, O(RC) — name the complexity.
- Trace the tied cell `(0,2)` on paper before running: it's the moment the
  tie-break rule proves itself.
