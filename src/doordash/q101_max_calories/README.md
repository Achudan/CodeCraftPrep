# Maximum Calories within Budget

**CodeZym:** https://codezym.com/question/101-maximum-calories-within-budget
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are ordering food from DoorDash and selecting items from a menu. You have a fixed budget, and your goal is to get the maximum total calories that can be bought without going over that budget.

You are given:

- A list of item prices in dollars.
- A list of calorie values for the same items in the same order.
- A budget in dollars.

Each menu item may be purchased more than once. You may choose any combination of items as long as the total price does not exceed the budget.

Return the greatest total calories that can be purchased within the budget. If no item can be purchased, return 0.

## Notes

Unbounded knapsack. The spec is complete as stated; the exercise's tests include the direct example (prices `[2,3]`, calories `[3,4]`, budget `5` → `7`) and the unaffordable → `0` edge case.
