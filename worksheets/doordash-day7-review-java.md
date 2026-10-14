# DoorDash CodeCraft — Day 7 (Thu Oct 15): Taper Day, Java
Interview: Fri Oct 16, 2026, 10:00–11:00 AM PDT · Language: Java
Questions: none new — this is the taper day from your locked plan (`doordash-codecraft-plan.md`)

Same rule as race week in training: no new material today. The work is done; today is about keeping the blade sharp and resting the arm. Cap the whole day at ~90 minutes, then close the laptop.

## Run the starter
```bash
cd ~/workspace/your_files/interview-prep/doordash-java/day7
javac WarmUp.java && java WarmUp
```
(Note: run from your machine — it has the JDK. Same single-file, tests-in-`main` setup as all week.)

## Block 1 (35 min, untimed) — Skim, out loud
Read these once, saying the bolded line out loud as you go. If any line hesitates, that's your 5-minute review target — but no full re-dos today.

1. **Your Day 6 debrief answers.** The three sentences you wrote yesterday (what ate the most time · what you'd do with 15 more minutes · the one sentence you'll remember on Friday). Friday's sentence is your warm-up mantra tomorrow — say it twice.
2. **Tier 1 one-liners from memory** (no code, just say them):
   - Dasher Payout: **sort by start, sweep-merge, sum lengths — adjacent counts as merged.**
   - Workflow Automation: **one `updateStatus` guard owns every status rule; refund thresholds 0/50/100 for late ≤10 / 11–30 / >30 min, cancel-before-prep → 95%; closed orders ignore non-refund updates.**
   - Gift Cards: **totalBalance / totalSpent / activeCardCount definitions + FIFO all-or-nothing pay.**
3. **Patterns cheat sheet** (`doordash-codecraft-plan.md`, bottom section): overlaps → sort+merge · status rules → state machine + Strategy · closest facility → multi-source BFS · ordered common sequence → LCS DP · max value reusable items → unbounded knapsack · tree diff → parse to map, diff by key · prefix search → trie. One pass, out loud.
4. **The AI-assisted playbook** (`doordash-codecraft-plan.md`): 0–5 read + state plan, 5–10 define "done", 10–45 implement + verify after each step, 45–55 edge cases, narrate throughout. Read it once.

## Block 2 (30 min, timed) — The warm-up
One easy run: `WarmUp.java` (Dasher Payout easy, 6 tests). Start a 30:00 timer. Narrate out loud like yesterday's mock — quiet narration is the habit you're protecting. When all tests pass: note your time, and **stop**. Do not chase a faster time, do not open another file.

## Block 3 (15 min, untimed) — Environment check
This is the thing that bites on interview day, so verify it Thursday night, not Friday morning:
- IDE opens, project builds, JDK runs (`java -version` agrees with you)
- Your AI agent is logged in (the one you'd actually drive in the session)
- Screen-share test done (whatever link/format Friday uses — join it once)
- Water + snacks + a quiet room plan, the way the playbook says

If anything above is broken, fix it tonight. Friday morning is not debugging time.

## Rules for today
- **No new material.** No knapsack deep-dives, no trie implementations, no "one more question." The plan earned this taper.
- **Early night.** Seriously. Sleep is the last performance lever and it's the one you control.
- If a test fails in Block 2, fix it calmly — it doesn't count as "hard," it counts as the one thing you needed to see today.

## Tomorrow (Fri Oct 16) — Interview morning
- 9:30 AM: setup reminder fires (already scheduled). When it lands: IDE open, agent logged in, screen-share ready.
- 9:45 AM: at the machine, per your plan. Water nearby.
- 10:00 AM: read the task, state your mental model and plan out loud — then do the thing you've done six times this week.

Good luck. You did the work; tomorrow just say it out loud.
