# Workflow Automation Engine

DoorDash's self-help menu backend: an in-memory order lifecycle plus a refund
policy engine.

## The problem

**Part 1 — guarded transitions.** An order is `OPEN` when created. It can move
`OPEN → DELIVERED` or `OPEN → CANCELLED`. Once `DELIVERED` or `CANCELLED`, the
order is closed: further status updates are ignored. Unknown order id → `false`,
no crash.

**Part 2 — refund policies as Strategies.**

- Late delivery: ≤ 10 min late → 0%, 11–30 min → 50%, > 30 min → 100%
- Cancellation: before prep started → 95%, after prep started → 0%

New refund rules must be addable **without touching existing rules**.

## Key ideas (say these out loud before coding)

1. **One guard in one place**: `updateStatus` checks `status == OPEN` exactly
   once. Don't scatter status checks through the codebase.
2. **Strategy, not if/else**: each refund rule is a class implementing
   `RefundPolicy`. The order code never knows the thresholds.
3. State your thresholds before coding them — interviewers grade the narration.

## Files

| File | What it is |
|---|---|
| `WorkflowAutomation.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `WorkflowAutomationAttempt.java` | **Your attempt.** Clean slate — implement here, then copy your methods into the exercise file to run the tests. |
| `WorkflowAutomationSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd workflow-automation
javac WorkflowAutomation.java && java WorkflowAutomation
javac WorkflowAutomationSolution.java && java WorkflowAutomationSolution
```

## Interview tips

- This is the LLD-heavy problem: the design (guard + Strategy) matters more
  than the code volume. Name the patterns as you use them.
- "Unknown id → false, no crash" is the edge case people forget. Handle it first.
