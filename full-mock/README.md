# Full Cold Mock (45 min)

A blank-canvas mock of the Workflow Automation Engine. Do this **cold**: close
every other folder, start a 45:00 timer, narrate out loud the whole time.

## The prompt (reading this is allowed)

> Design a Workflow Automation Engine for DoorDash's self-help menu. Keep
> users, restaurants, and orders in memory.
>
> **Part 1 — Order lifecycle:** an order is OPEN when created. It can move
> OPEN → DELIVERED or OPEN → CANCELLED. Once DELIVERED or CANCELLED it is
> closed: further status updates that are not refunds are ignored. Updating an
> unknown order id must fail cleanly (no crash, no exception to the caller).
>
> **Part 2 — Refund rules:**
> - Late delivery: ≤ 10 min → 0%; 11–30 min → 50%; > 30 min → 100%
> - Cancellation: before prep started → 95%; after prep started → 0%
>
> New refund rules should be addable without touching existing rules.

## Protocol

1. State requirements + refund thresholds **before** coding.
2. Write your own tests first. Watch them fail.
3. Implement the guard, then the refund policies. Run tests as you go.
4. Debrief after the timer: time sinks, your +15-min plan, one line for Friday.

## Files

| File | What it is |
|---|---|
| `MockSession.java` | The prompt + protocol. The implementation is yours to write. |

There is deliberately no solution here — compare against
`../workflow-automation/WorkflowAutomationSolution.java` *after* the timer,
never during.

## Run

```powershell
cd full-mock
javac MockSession.java && java MockSession
```
