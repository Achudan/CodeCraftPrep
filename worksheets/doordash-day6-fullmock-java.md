# DoorDash CodeCraft — Day 6 (Wed Oct 14): Full Mock + Weakest-Link Redo, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · two timed blocks
Questions: Tier 1 CodeCraft bank — Workflow Automation Engine cold mock (CodeZym 98) + your weakest Tier 1 redo, per your locked plan (`doordash-codecraft-plan.md`)

Different setup from Days 1–5, on purpose: no TODOs, no pre-built tests. A mock is scored on what YOU produce cold — including your own tests. The only starter is a blank canvas with the interviewer prompt in its comments.

## Run the starter
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day6
javac MockSession.java && java MockSession
```
(Note: it compiles to a protocol reminder, nothing more — the file is yours to fill. Run from your machine — it has the JDK.)

## Block A (45 min, timed, COLD) — Full mock: Workflow Automation Engine
Why this question: it's the meatiest Tier 1 (state machine + rule engine), the closest to the "support request automation" style task in the new AI-assisted format, and Dasher Payout already got its redo on Day 3. Cold = the real test is retention + narration under time.

**Cold protocol (non-negotiable):**
- Close every Day 1–5 file. The problem statement lives in `MockSession.java` comments — reading THAT is allowed; re-opening Day 2 is not.
- Start a 45:00 timer. Narrate out loud the entire time — as if the interviewer is in the room. Alone? Talk to the wall or record a voice memo; silence is the thing being trained out.
- Write your own tests BEFORE the implementation. In the real round nobody hands you a test file — proving it works is part of the score.

**0–5 min — Requirements out loud:** In-memory users/restaurants/orders. Lifecycle OPEN → DELIVERED or OPEN → CANCELLED; closed orders ignore non-refund status updates; unknown order id fails clean (no crash). State the refund thresholds BEFORE coding: late ≤10 min → 0%, 11–30 → 50%, >30 → 100%; cancel before prep → 95%, after prep → 0%.

**5–10 min — Sketch:** entities + one `updateStatus` guard that owns every status rule ("all status rules live here, nowhere else") + `RefundPolicy` Strategy so new rules don't touch old ones. Say the open/closed line out loud — interviewers listen for it.

**10–35 min — Implement in this order:** your own test list first (aim for 6: happy-path deliver, double-deliver ignored, cancel flow, unknown id, one late tier, one cancel tier) — watch them fail — then the guard, then the refund policies. Run tests after each piece, not batched at the end.

**35–45 min — Edge cases + trade-offs out loud:** a closed order can still be *read* for a refund even though status updates are ignored — say that. With more time: per-restaurant policy overrides; persisting the event log.

**Score yourself (45 pts, be honest):** requirements stated up front 5 · plan before code 5 · implementation correct (guard + Strategy + edges) 15 · own tests written first and green 10 · continuous narration + trade-offs 10.

**Debrief (5 min, after the timer):** (1) what ate the most time, (2) what you'd do with 15 more minutes, (3) one sentence you'll remember on Friday. Write all three down — they're Thursday's skim material.

## Block B (45 min, timed) — Weakest-link redo
**Picker (2 min, no code):** for each Tier 1, say the core rule from memory in 10 seconds —
- Dasher Payout: the merge rule + two overlap cases.
- Workflow Automation: the refund thresholds (you just did these — if they weren't instant, that's your answer).
- Gift Cards: totalBalance / totalSpent / activeCardCount definitions + the FIFO all-or-nothing pay rule.
Whichever you hesitated on = weakest. Tie → the one you'd least want drawn on Friday.

**10 min — Refresh:** re-read ONLY that day's sheet (notes, no typing). Then close it.

**30 min — Redo from scratch:** fresh file in `day6/` (e.g. `RedoPayout.java`), timed, out loud. Rebuild the entities, the logic, and your own tests — cold.

**5 min — Verify:** run that day's original test expectations against your redo (the expected values are in the Day 1/2/3 sheets). Any miss = a flashcard for Thursday.

## After (10 min, untimed) — Friday readiness check
- IDE + AI agent installed, logged in, project builds — the plan's AI-assisted playbook assumes this; Friday 9:45 AM is the deadline, not the start.
- Screen-share tested once (the format may be on your machine).
- Re-read the 60-min AI-assisted playbook in `doordash-codecraft-plan.md` once — narrate-plan-verify is the whole game.

## Traps to narrate, not fall into
- **Peeking "just for the thresholds"** — the thresholds ARE the test. State them from memory; correcting yourself out loud mid-round beats silent guessing.
- **Skipping own tests to "save time"** — in the real round, tests are how you prove it works. No tests = unproven.
- **Silent coding** — the interviewer scores what they hear. If you went quiet for 5 minutes, that's the debrief's item #1.
- **Gold-plating Part 2 before Part 1 is green** — a working core + clear narration beats a half-finished grand design, every time.

## Tomorrow (Thu Oct 15) — Day 7 preview
Light day, no new material: skim your notes + the three debrief one-liners, one easy timed Dasher Payout run, early night. Friday: setup ready by 9:45, interview 10:00.
