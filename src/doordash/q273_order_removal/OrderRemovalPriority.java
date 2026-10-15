package doordash.q273_order_removal;

import java.util.*;

// You get a list of unique order ids. Think of each id as the height of a
// hill. An order is eligible when it is a peak: bigger than every neighbor it
// currently has (first/last orders have one neighbor; a lone order is always
// eligible). At each step remove the SMALLEST eligible order, until the list
// is empty. Return the ids in removal order.
//
// Source: https://codezym.com/question/273-orderid-removal-priority-neighbors
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/101-300/273-orderid-removal-priority-neighbors/q-273-java-tutorial.md
public class OrderRemovalPriority {

    public OrderRemovalPriority() {
    }

    public List<Integer> prioritizeOrders(List<Integer> orderIds) {
        // TODO: repeatedly remove the smallest eligible order.
        // Hint from the tutorial: removing one order only changes the
        // neighbors of two orders (its left and right). Recheck just those
        // two, and keep eligible orders in a min-heap: O(n log n) total.
        // Work on a copy; do not modify the caller's list.
        return new ArrayList<>();
    }

    // An order is eligible if it is bigger than every neighbor it currently has.
    boolean isEligible(List<Integer> orders, int i) {
        boolean biggerThanLeft = (i == 0) || orders.get(i) > orders.get(i - 1);
        boolean biggerThanRight = (i == orders.size() - 1) || orders.get(i) > orders.get(i + 1);
        return biggerThanLeft && biggerThanRight;
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        OrderRemovalPriority svc = new OrderRemovalPriority();

        // Tutorial example: [8,3,6,1,5] -> [5,6,8,3,1].
        check("[8,3,6,1,5] -> [5,6,8,3,1]",
                svc.prioritizeOrders(List.of(8, 3, 6, 1, 5)),
                List.of(5, 6, 8, 3, 1));
        // Tutorial dry run: [2,7,4,9,3,6] -> [6,7,9,4,3,2].
        check("[2,7,4,9,3,6] -> [6,7,9,4,3,2]",
                svc.prioritizeOrders(List.of(2, 7, 4, 9, 3, 6)),
                List.of(6, 7, 9, 4, 3, 2));
        check("ascending [1,2,3] -> [3,2,1]",
                svc.prioritizeOrders(List.of(1, 2, 3)),
                List.of(3, 2, 1));
        check("descending [3,2,1] -> [3,2,1]",
                svc.prioritizeOrders(List.of(3, 2, 1)),
                List.of(3, 2, 1));
        check("single [42] -> [42]",
                svc.prioritizeOrders(List.of(42)),
                List.of(42));
        check("empty -> []",
                svc.prioritizeOrders(List.of()),
                List.of());

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
