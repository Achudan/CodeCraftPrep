# DoorDash CodeCraft Prep

Java practice package for the DoorDash **CodeCraft** interview round.
Rebuilt from the real problem set in
[CodeZymSolutions — DoorDash 2026](https://github.com/prsnt558908/CodeZymSolutions/blob/main/0-company-wise-interview-questions/2026/01-doordash-ds-algo-2026.md).

Every problem is a Java exercise class with **TODO stubs and test cases — no solutions**.
Implement the TODOs; the `main` test harness tells you when you pass.

## Problems (20)

| # | Problem | Class | Source |
|---|---------|-------|--------|
| 97 | Design Dasher Payout Service | `doordash.q097_dasher_payout.DasherPayoutService` | full tutorial |
| 98 | Design Workflow Automation Engine for Self Help Menu | `doordash.q098_workflow_automation.WorkflowAutomationEngine` | full tutorial |
| 99 | Find Longest Sequence of Pickup Jobs for Dashers | `doordash.q099_pickup_jobs.LongestPickupSequence` | full tutorial |
| 100 | Find Closest DashMart Distance / DashMart Serving Maximum Customers | `doordash.q100_closest_dashmart.ClosestDashMart` | full tutorial |
| 101 | Maximum Calories within Budget | `doordash.q101_max_calories.MaxCalories` | problem-list spec |
| 102 | Aggregate Gift Card Data | `doordash.q102_gift_cards.GiftCardService` | problem-list spec |
| 103 | Dasher Payout Logic — Simple | `doordash.q103_payout_logic.DasherPayoutLogic` | full tutorial |
| 104 | Chef Order Preparation List | `doordash.q104_chef_orders.ChefOrderPreparation` | problem-list spec |
| 105 | Count and Print Changed Nodes in DoorDash Menu Tree | `doordash.q105_menu_tree.MenuTreeChanges` | problem-list spec |
| 106 | Longest Path and Increasing Paths in a Grid | `doordash.q106_increasing_path.LongestIncreasingPath` | problem-list spec |
| 107 | DoorDash Restaurant Search Engine (trie) | `doordash.q107_restaurant_search.RestaurantSearchEngine` | full tutorial |
| 270 | Walls and Nearest Gate for Empty Rooms | `doordash.q270_walls_gates.WallsAndGates` | problem-list spec |
| 271 | Design Best Reviews and Monthly Rewards System | `doordash.q271_best_reviews.BestReviewsRewards` | problem-list spec |
| 272 | Debugging: Round Robin Traffic Router | `doordash.q272_round_robin.RoundRobinRouter` | full tutorial |
| 273 | Order ID Removal Priority Based on Neighbors | `doordash.q273_order_removal.OrderRemovalPriority` | full tutorial |
| 274 | Debugging: Dasher Assignment Service for Order Delivery | `doordash.q274_dasher_assignment.DasherAssignmentService` | full tutorial |
| 275 | Dasher Coverage of Connected User Areas | `doordash.q275_dasher_coverage.DasherCoverage` | problem-list spec |
| 276 | Modified LRU Cache for Dasher Delivery Assignments | `doordash.q276_lru_cache.DasherLRUCache` | problem-list spec |
| 277 | Strings That Have Two Letters Swapped | `doordash.q277_swap_strings.SwapEqualStrings` | problem-list spec |
| 278 | Names Requiring Minimum Changes to Become an Anagram | `doordash.q278_anagram.MinAnagramChanges` | problem-list spec |

**Source key**
- *full tutorial* — signatures, rules, and test expectations taken from the question's
  Java walkthrough in CodeZymSolutions (`q-<n>-java-tutorial.md`).
- *problem-list spec* — that question has no full write-up in the repo, so the
  exercise follows the problem statement from the DoorDash 2026 list exactly;
  each file's header documents any judgment calls.

Each file links its CodeZym question page in its header comment.

## Build & run (JDK 21)

```bash
cd src
javac -d out $(find . -name "*.java")

# run one exercise's tests, e.g. Q97:
java -cp out doordash.q097_dasher_payout.DasherPayoutService
```

Tests fail until you implement the TODOs — that's the point. The two
*Debugging* problems (272, 274) ship with buggy starter code instead of
TODOs: find and fix the bugs until the harness goes green.
