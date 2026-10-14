# DoorDash CodeCraft — Day 2 (Sat Oct 10): Workflow Automation Engine, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · 60-min timed run
Question: CodeZym 98 (per your locked plan, `doordash-codecraft-plan.md`)

Built to mirror Day 1: no Maven, no IDE setup, tests fail until you fill in the TODOs — that's the drill.

## Run the starter
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day2
javac WorkflowAutomation.java && java WorkflowAutomation
```

## 60-min structure
- **0–5 min — Requirements out loud:** In-memory users/restaurants/orders. Order lifecycle OPEN → DELIVERED or OPEN → CANCELLED. Closed orders (DELIVERED/CANCELLED) ignore non-refund status updates. Unknown order id = clean failure, no crash.
- **5–15 min — Sketch:** `User`, `Restaurant`, `Order{id,userId,restaurantId,amount,status}`, `OrderStatus`. One transition method `updateStatus(orderId, newStatus)` that owns the guard — say explicitly: "all status rules live here, nowhere else."
- **15–45 min — Part 1 timed:** Implement `updateStatus`: unknown id → false; non-OPEN → false; OPEN → apply and return true. Run tests as you go, don't batch them to the end.
- **45–60 min — Part 2 refunds (Strategy):** `RefundPolicy.refundPercent(order, ctx)`. State thresholds BEFORE coding: late ≤10 min → 0%, 11–30 → 50%, >30 → 100%; cancel before prep → 95%, after prep → 0% fallback. Then implement `LateDeliveryRefund`, `CancelBeforePrepRefund`, and `refundAmount`. Narrate why Strategy beats one big if/else: new rule = new class, existing rules untouched (open/closed) — that's a line interviewers listen for.

## Tests already in the starter (say expected values out loud first)
- New order is OPEN; deliver OPEN → DELIVERED; update DELIVERED again → ignored, still DELIVERED
- Cancel OPEN → CANCELLED; update CANCELLED again → ignored
- Unknown order id → false, no exception
- Late 5 min → 0%, 20 min → 50%, 45 min → 100%; $200 @ 50% → $100.00
- Cancel before prep → 95%, after prep → 0% (your stated fallback)

## Traps to narrate, not fall into
- Scattering status checks across callers instead of one guard method
- Throwing on unknown id instead of returning a clean false/empty
- Hard-coding refund tiers inside `Order` — keep them in policy classes
- Forgetting a closed order can still be *read* for a refund even though status updates are ignored

## AI-assisted note (save for Sunday's mock)
Same drill, AI driving: prompt (1) entities + store only, (2) `updateStatus` guard only, (3) one refund policy at a time. Read and RUN after each step.

## Tomorrow (Sun Oct 11) — Day 3 preview
Gift Card Aggregation timed (CodeZym 102) + Chef Order untimed, plus the AI-assisted environment setup checklist from `doordash-weekend-kickoff.md` and one 60-min AI-assisted mock, narrating out loud. No prep needed tomorrow morning — it'll be ready the same way.
