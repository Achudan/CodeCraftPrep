package doordash.q104_chef_orders;

import java.util.*;

// Spec (DoorDash 2026 problem list, Q104 — quoted from the list):
// "A chef receives all his orders for the day as a list of unique order ids. He creates a
//  new list by repeatedly removing the smallest eligible order from the current list and
//  appending it to the new list. Return the order in which the chef creates the new list."
// Source: https://codezym.com/question/104-chef-order-preparation-list
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Reading: the spec does not define what makes an order "eligible". The most literal
// reading — repeatedly remove the minimum of the current list — is implemented here,
// which is equivalent to returning the ids in ascending order.
public class ChefOrderPreparation {

    // Return the ids in the order the chef appends them to the new list.
    public static List<Integer> preparationOrder(List<Integer> orderIds) {
        // TODO: repeatedly remove the smallest remaining order id.
        return new ArrayList<>();
    }

    private static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        check("[5,3,8,1] -> [1,3,5,8]",
                preparationOrder(List.of(5, 3, 8, 1)), List.of(1, 3, 5, 8));
        check("already sorted [1,2,3] -> [1,2,3]",
                preparationOrder(List.of(1, 2, 3)), List.of(1, 2, 3));
        check("reverse sorted [9,7,5] -> [5,7,9]",
                preparationOrder(List.of(9, 7, 5)), List.of(5, 7, 9));
        check("single [7] -> [7]",
                preparationOrder(List.of(7)), List.of(7));
        check("empty -> []",
                preparationOrder(List.of()), List.of());
        // Input list must not be mutated by the implementation.
        List<Integer> input = new ArrayList<>(List.of(4, 2, 6));
        preparationOrder(input);
        check("input not mutated", input, List.of(4, 2, 6));

        System.out.println(anyFail ? "\nSome tests FAILING — implement the TODO."
                                   : "\nAll tests PASS.");
    }
}
