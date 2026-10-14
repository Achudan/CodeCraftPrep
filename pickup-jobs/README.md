# Longest Sequence of Pickup Jobs

Two merchants each hand you an ordered list of pickup jobs. Find the longest
sequence of jobs you can do for *both*, in order. (This is LCS wearing a
DoorDash uniform.)

## The problem

- `lcsLength(a, b)` — length of the longest common subsequence
- `longestPickupSequence(a, b)` — the actual sequence, not just the length

**Tie-break (state it before coding):** when skipping A's job vs skipping B's
job tie on length, skip **B's** job first — the result prefers merchant A's
jobs, which makes the answer deterministic.

## Key ideas

1. **The recurrence, out loud first**: `dp[i][j] = dp[i-1][j-1]+1` on match,
   else `max(dp[i-1][j], dp[i][j-1])`.
2. **Reconstruction walks the table backwards**: match → take it and go
   diagonal; otherwise step toward the larger neighbor; on ties step toward
   `j-1` (skip B's) per the stated rule.
3. Length is easy; interviewers ask for the sequence because backtracking is
   where people fumble. Practice the walk, not just the table.

## Files

| File | What it is |
|---|---|
| `PickupJobs.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `PickupJobsSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd pickup-jobs
javac PickupJobs.java && java PickupJobs
javac PickupJobsSolution.java && java PickupJobsSolution
```

## Interview tips

- Say the recurrence before touching the keyboard. Then the code is just
  transcription, and transcription is fast.
- If they ask for space optimization, the length needs one row; the sequence
  needs the full table (or Hirschberg's — mention it, don't implement it).
