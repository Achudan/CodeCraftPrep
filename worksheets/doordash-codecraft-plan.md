# DoorDash CodeCraft — Prep Plan (interview next week)

**Interview: Friday, October 16, 2026, 10:00-11:00 AM PDT** (from your calendar). Language: **Java**. Prepared 2026-10-09. You started generic CodeZym LLD prep; this plan re-aims it at DoorDash's actual question bank from your screenshots.

## What you're walking into

DoorDash's question bank is small and repeats a lot, so focused prep beats broad prep. Two formats to be ready for:

1. **Classic CodeCraft (LLD-style implementation)** — API-style, real-world coding: design + implement a small service in ~45-60 min.
2. **New AI-assisted working session (rolling out)** — 60 min on your own machine/IDE with starter code; AI tools (Cursor, Claude Code, Codex) explicitly allowed. Scored on how fast you orient in unfamiliar code, how you plan, how you verify AI output (tests, repro, logs), debugging, scope control, and narrating trade-offs. Finishing everything matters less than a tight loop and clear communication.

Prep for the classic format AND practice driving an AI agent on one timed run, so either format is comfortable.

## Priority questions (from your screenshots, ranked)

### Tier 1 — CodeCraft implementation (highest ROI)
1. **Design Dasher Payout Service** — payout API from time spent on deliveries; merge overlapping delivery intervals. Easy version first.
   - https://codezym.com/question/103 (easy) → https://codezym.com/question/97
   - Core skill: interval merging + clean API design.
2. **Design Workflow Automation Engine (Self Help Menu)** — in-memory users/restaurants/orders; order status state machine (OPEN → DELIVERED/CANCELLED, closed orders ignore non-refund updates); Part 2 adds tiered refund rules (late → 0/50/100%, cancel before prep → 95%).
   - https://codezym.com/question/98
   - Core skill: state machine + rule engine. Very close to the "support request automation" style task in the new format.
3. **Aggregate Gift Card Data** — users with multiple gift cards, payments across cards, aggregated totals.
   - https://codezym.com/question/102
   - Core skill: clean entity/manager design, aggregates.

### Tier 2 — Coding round regulars (drill to automatic)
4. **Closest DashMart + DashMart serving max customers** — multi-source BFS on a grid with blocked cells. https://codezym.com/question/100
5. **Changed Nodes in Menu Tree** — parse "key,value,children" nodes, diff two trees (changed/added/deleted). https://codezym.com/question/105
6. **Longest Increasing Path in a Grid** — DFS + memoization; also return the path(s). https://codezym.com/question/106
7. **Longest Sequence of Pickup Jobs** — LCS over two ordered merchant lists. https://codezym.com/question/99
8. **Maximum Calories within Budget** — unbounded knapsack DP. https://codezym.com/question/101
9. **Chef Order Preparation List** — repeatedly remove smallest "eligible" (peak) order. https://codezym.com/question/104
10. **Restaurant Search Engine** — trie with prefix suggestions.

Your existing generic plan still helps: Food Ordering (Observer) transfers directly to DoorDash order systems; Parking Lot/Chess are good pattern warm-ups but lower priority than the list above.

## 7-day schedule (Fri 10/9 → Thu 10/15, interview Fri 10/16)

- **Day 1 (Fri 10/9, tonight):** Dasher Payout — easy version untimed, then timed 45 min. Write your own tests for overlaps.
- **Day 2 (Sat 10/10):** Workflow Automation Engine — Part 1 timed, then add Part 2 refund rules.
- **Day 3 (Sun 10/11):** Gift Cards timed + Chef Order untimed. Set up your AI-assisted environment (IDE + agent) and do one 60-min AI-assisted mock on a Tier 1 question — narrate out loud the whole time.
- **Day 4 (Mon 10/12):** DashMart BFS + Menu Tree diff, timed.
- **Day 5 (Tue 10/13):** Pickup Jobs (LCS) + Longest Increasing Path; skim knapsack + trie patterns.
- **Day 6 (Wed 10/14):** Full mock: one Tier 1 CodeCraft, cold, 45 min, out loud (Java + IDE). Redo whichever question felt weakest.
- **Day 7 (Thu 10/15):** Light day — skim your notes, one easy timed run (Dasher Payout easy), early night. No new material.
- **Fri 10/16 10:00 AM:** Interview. IDE + AI agent open and ready by 9:45.

## AI-assisted session playbook (60 min)

- **Before:** IDE + AI agent installed and logged in, project builds, screen-share tested, water/snacks. Arrive with a working setup — they explicitly flag this.
- **0-5 min:** Read the task + starter code. Say your mental model and plan out loud before touching code.
- **5-10 min:** Break the task into small steps; tell the interviewer (and the AI) what "done" looks like for step 1.
- **10-45 min:** Implement in small increments. Prompt the AI with precise specs, then READ and verify its output — run code/tests after each step. Narrate trade-offs as you go.
- **45-55 min:** Edge cases, quick tests, fix what breaks. Call out what you'd do with more time.
- **Throughout:** Scope control beats cleverness. A working core + clear narration > a half-finished grand design.

## Patterns cheat sheet
- Overlaps/payout → sort + merge intervals
- Status-driven rules → state machine + Strategy for refund policies
- Closest facility on grid → multi-source BFS
- Longest common ordered sequence → DP (LCS)
- Max value under budget, reusable items → unbounded knapsack
- Tree updates → parse to map, diff by key
- Prefix search → trie

---
Still unknown: which format your loop uses (classic CodeCraft vs new AI-assisted session). The 60-minute length matches the new format — prep covers both, and your IDE + AI setup should be ready either way.
