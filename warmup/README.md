# Warm-up (taper day)

The Dasher Payout merge, stripped to its essentials. This is Thursday's single
easy run — it should feel almost automatic.

## The problem

- `totalActiveMinutes(deliveries)` — merge overlapping/adjacent delivery
  intervals, return total paid minutes
- `payout(deliveries, ratePerHour)` — `minutes / 60 * rate`

## Key ideas

Same as `dasher-payout`: sort by start, sweep-merge with
`next.start <= curEnd`, sum the lengths. If anything here feels hard, that's
your review target — but you should still finish in ~30 minutes.

## Files

| File | What it is |
|---|---|
| `WarmUp.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `WarmUpAttempt.java` | **Your attempt.** Clean slate — implement here, then copy your methods into the exercise file to run the tests. |
| `WarmUpSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd warmup
javac WarmUp.java && java WarmUp
javac WarmUpSolution.java && java WarmUpSolution
```

## Interview tips

- Taper day is about confidence, not new material. One clean run, then stop.
  Early night, no more code after this.
