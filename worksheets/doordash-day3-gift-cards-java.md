# DoorDash CodeCraft — Day 3 (Sun Oct 11): Gift Card Aggregation, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · 45-min timed run
Question: CodeZym 102 (per your locked plan, `doordash-codecraft-plan.md`)

Built to mirror Days 1–2: no Maven, no IDE setup, tests fail until you fill in the TODOs — that's the drill. This is a faithful prep reconstruction from the plan notes, not a verbatim CodeZym copy — confirm the real method signatures at the link when you open the question.

## Run the starter
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day3
javac GiftCards.java && java GiftCards
```

## 45-min structure
- **0–5 min — Requirements out loud:** Users hold multiple gift cards. You need per-user aggregates (total remaining, total spent, how many cards still hold money), one system-wide total, and a payment that can span several cards. State your payment rule BEFORE coding: cards are drawn in issued order (FIFO), and payment is all-or-nothing — insufficient total balance means reject and change nothing. Say what an unknown user returns (0 / false, no crash).
- **5–12 min — Sketch:** `User`, `GiftCard{id, userId, initialAmount, balance}`, and a manager holding `Map<userId, List<GiftCard>>` with each list kept in issued order. Say explicitly: "payment order is just list order — no sorting needed because cards are appended on issue." Aggregates are computed from `initialAmount - balance`, never stored as a separate running total that can drift.
- **12–35 min — Implement in this order:** `totalBalance` → `totalSpent` → `activeCardCount` → `totalRemainingAllUsers` (reuse `totalBalance`, don't re-loop raw cards) → `pay`. For `pay`: check the total first, return false untouched if it can't cover the amount, only then sweep cards deducting `min(balance, remaining)`. Run tests after each method, don't batch them to the end.
- **35–45 min — Trade-offs out loud:** Aggregates are O(cards per user) per call — fine in memory; say you'd cache a running total only if reads dwarf writes, and then the payment path is the one place that must maintain it. With more time: card expiry, partial-payment rule if the interviewer wants it instead, per-payment transaction record.

## Tests already in the starter (say expected values out loud first)
- Unknown user → 0.0 balance; user with no cards → 0.0
- Issue $50 + $30 to one user → total 80.0; a second user's $20 stays isolated
- Pay $60 → earliest card drains $50 → 0, next card $30 → $20; spent = 60.0, active cards 2 → 1
- Pay $25 against $20 remaining → false, and every balance is unchanged (this is the test that catches deduct-then-fail)
- Pay $0 → false; pay the exact remaining $20 → true, total 0.0, active cards 0
- System-wide remaining ends at $20 (only the untouched user)

## Traps to narrate, not fall into
- Deducting card-by-card and discovering mid-sweep that funds run out — check the total before touching any card
- Keeping a separate `spent` counter per user that drifts from the card balances — derive it from `initialAmount - balance`
- Sorting cards at payment time — issued order is already the list order
- Throwing on an unknown user instead of returning 0 / false

## Second half of Sunday — AI-assisted mock (60 min, per the plan)
Day 3 is also when the plan slots the AI-assisted format practice, and the setup checklist is already written in `doordash-weekend-kickoff.md` (IDE builds, AI agent logged in, screen-share tested). For the mock itself, redo **Dasher Payout** (Day 1) rather than a new question — a question you already know isolates the skill being scored: driving the AI. Prompt in small steps: (1) entities + API only, (2) the merge function only, (3) tests. Read and RUN the output after each step, narrate your plan and trade-offs out loud the whole time, and note your time at 45 min. Finishing matters less than a tight verify loop.

## Optional untimed add-on (only if energy is left)
Chef Order Preparation List (CodeZym 104) from the plan: repeatedly remove the smallest "eligible" order. Skim the pattern for 15–20 min, don't build a full starter — Day 4 (Mon Oct 12) is DashMart BFS + Menu Tree diff timed, and that's the higher-value fresh start.

## Tomorrow (Mon Oct 12) — Day 4 preview
Closest DashMart (multi-source BFS on a grid with blocked cells) + Changed Nodes in Menu Tree (parse, diff by key), both timed. No prep needed tonight — it'll be ready the same way.
