# DoorDash CodeCraft — Day 1 (Fri Oct 9, tonight): Dasher Payout, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java · 45-min timed run

Built from your locked plan (`doordash-codecraft-plan.md`): easy version untimed first if you want, then one clean 45-min timed run. Don't do two full runs tonight — one timed run + a cold 10-min merge redo on Sunday beats grinding.

## Run the starter (no Maven, no IDE setup)
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day1
javac DasherPayout.java && java DasherPayout
```
Tests will fail until you implement the TODOs — that's the drill. Say each expected value out loud before you run.

## 45-min structure
- **0–5 min — Requirements out loud:** Overlapping deliveries must not double-count paid time. Payout = total merged active minutes × rate. State assumptions: minutes as ints, touching intervals [0,30]+[30,60] DO merge (pick this unless CodeZym says otherwise), empty input = 0, invalid interval (end <= start) = reject/skip — say which.
- **5–12 min — API sketch:** `record Interval(int start, int end)`, `totalActiveMinutes(List<Interval>)`, `calculatePayout(List<Interval>, double ratePerHour)`. One pure merge function you can test alone.
- **12–35 min — Implement:** validate → sort by start (tie: earlier end first) → one sweep merging while `next.start <= current.end` → sum lengths → payout = minutes / 60.0 × rate. Harder version: group intervals by dasherId in a `Map<String, List<Interval>>`, reuse the same merge per dasher.
- **35–45 min — Trade-offs out loud:** O(n log n) sort, O(n) sweep. With more time: timestamp/timezone parsing, per-delivery minimums, rounding rules.

## Tests already in the starter (assert these)
- `[]` → 0 min
- `[0,60] @ $30/hr` → $30.00
- `[0,60],[30,90]` → 90 min, not 120
- `[0,30],[30,60]` → 60 min (adjacent-merge rule)
- `[60,90],[0,30]` unsorted → still correct
- `[0,120],[30,60]` nested → 120 min

## AI-assisted variant (use on Sunday's mock, not tonight)
Give your AI agent the spec in small prompts: (1) entities + API only, (2) merge function only, (3) tests. Read and RUN its output after each step — never accept code you haven't run. That's exactly what the new 60-min format scores.

## Tomorrow (Sat Oct 10) — Day 2 preview
Workflow Automation Engine (CodeZym 98), 60 min: order state machine OPEN → DELIVERED/CANCELLED, closed orders ignore non-refund updates, then Part 2 tiered refunds (late → 0/50/100%, cancel-before-prep → 95%) as a `RefundPolicy` Strategy interface. No prep needed tonight.
