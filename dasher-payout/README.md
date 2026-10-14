# Dasher Payout Service

Compute a dasher's payout from their active delivery intervals at an hourly rate.

## The problem

Given a list of `[start, end)` active-minute intervals (they can overlap, arrive
unsorted, or touch each other), merge them and compute:

- `totalActiveMinutes` — total paid minutes after merging
- `calculatePayout` — `minutes / 60 * ratePerHour`
- `payoutByDasher` (stretch) — same thing, per dasher

## Key ideas (say these out loud before coding)

1. **Sort by start**, then sweep: keep a running `[curStart, curEnd]`.
2. **Merge when `next.start <= curEnd`** — that one condition covers both
   overlapping *and* adjacent intervals (`[0,30],[30,60]` → 60, not two runs).
3. Empty list → 0. Don't overthink validation; state your assumption.

## Files

| File | What it is |
|---|---|
| `DasherPayout.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `DasherPayoutSolution.java` | **Reference.** Complete, all tests pass. Run it first to see the shape of the answer, then close it and do the exercise. |

## Run

```powershell
cd dasher-payout
javac DasherPayout.java && java DasherPayout              # exercise (tests fail until TODOs done)
javac DasherPayoutSolution.java && java DasherPayoutSolution  # reference (all pass)
```

## Interview tips

- This is the classic CodeCraft warm-up: sort + merge + aggregate. If it takes
  more than ~20 minutes, that's your signal to drill it again.
- The stretch (`payoutByDasher`) is just "reuse the single-dasher method per
  key" — interviewers love hearing you say that instead of rewriting logic.
