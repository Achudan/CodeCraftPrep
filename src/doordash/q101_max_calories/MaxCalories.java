package doordash.q101_max_calories;

import java.util.*;

// Spec (DoorDash 2026 problem list, Q101 — quoted from the list):
// "You are ordering food from DoorDash and selecting items from a menu. You have a fixed
//  budget, and your goal is to get the maximum total calories that can be bought without
//  going over that budget. You are given: a list of item prices in dollars, a list of
//  calorie values for the same items in the same order, a budget in dollars. Each menu
//  item may be purchased more than once. You may choose any combination of items as long
//  as the total price does not exceed the budget. Return the greatest total calories that
//  can be purchased within the budget. If no item can be purchased, return 0."
// Source: https://codezym.com/question/101-maximum-calories-within-budget
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
public class MaxCalories {

    // Unbounded knapsack: greatest total calories with total price <= budget.
    public static int maxCalories(int[] prices, int[] calories, int budget) {
        // TODO: unbounded knapsack DP over the budget.
        return 0;
    }

    private static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // One $2 item (3 cal) + one $3 item (4 cal) = $5 -> 7 cal. Best combo.
        check("prices [2,3] cal [3,4] budget 5 -> 7",
                maxCalories(new int[]{2, 3}, new int[]{3, 4}, 5), 7);
        // One $1 item bought 5 times: 5 * 10 = 50 cal.
        check("prices [1] cal [10] budget 5 -> 50",
                maxCalories(new int[]{1}, new int[]{10}, 5), 50);
        // Nothing affordable -> 0 (stated in the spec).
        check("prices [10] cal [100] budget 5 -> 0",
                maxCalories(new int[]{10}, new int[]{100}, 5), 0);
        // Zero budget -> 0.
        check("budget 0 -> 0",
                maxCalories(new int[]{2, 3}, new int[]{3, 4}, 0), 0);
        // Empty menu -> 0.
        check("empty menu -> 0",
                maxCalories(new int[]{}, new int[]{}, 5), 0);
        // Leftover budget that fits nothing: $4 buys two $2 items -> 6 cal.
        check("prices [2,3] cal [3,4] budget 4 -> 6",
                maxCalories(new int[]{2, 3}, new int[]{3, 4}, 4), 6);

        System.out.println(anyFail ? "\nSome tests FAILING — implement the TODO."
                                   : "\nAll tests PASS.");
    }
}
