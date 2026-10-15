# CodeCraftPrep

DoorDash **CodeCraft** interview prep in Java — interview **Friday, Oct 16, 2026,
10:00–11:00 AM PDT**.

Every problem lives in its own folder with three things:

- `README.md` — the problem, the key ideas to say out loud, and how to run it
- `<Problem>.java` — **exercise**: TODOs for you; the built-in tests fail until
  you fill them in
- `<Problem>Solution.java` — **reference**: complete and verified; every test
  passes. Run it first to understand the shape, then close it and do the
  exercise yourself.

## Problems (crash-plan order)

| # | Folder | Problem | Night |
|---|---|---|---|
| 1 | `dasher-payout/` | Sort + merge intervals → payout | Tue |
| 2 | `gift-cards/` | Aggregates + FIFO all-or-nothing pay | Tue |
| 3 | `workflow-automation/` | Guarded state machine + refund Strategy | Wed |
| 4 | `dashmart/` | Multi-source BFS, nearest-mart ownership | Thu |
| 5 | `menu-tree-diff/` | Parse-any-order + key-based diff | Thu |
| 6 | `pickup-jobs/` | LCS length + sequence reconstruction | stretch |
| 7 | `longest-increasing-path/` | DFS + memo, return the path | stretch |
| 8 | `warmup/` | Taper-day easy run (~30 min) | Thu eve |
| 9 | `full-mock/` | 45-min cold mock, no peeking | Wed/Thu |

`worksheets/` holds the longer drill sheets if you want the full 45-minute
timed format for any problem.

## Run any problem (Windows PowerShell)

```powershell
cd dasher-payout
javac DasherPayoutSolution.java && java DasherPayoutSolution  # reference: all tests pass
javac DasherPayout.java && java DasherPayout                  # exercise: tests fail until TODOs done
```

Requires a JDK (17+). Check with `javac -version`.

## How to use this

1. Read the problem's `README.md`.
2. Run the `*Solution` once — see green, understand the approach.
3. Close the solution. Open the exercise, start a timer, narrate out loud.
4. All tests green → note your time, move on.

These are prep reconstructions from the interview plan, not verbatim DoorDash
specs — confirm exact method signatures against the real prompt on the day,
and adapt.
