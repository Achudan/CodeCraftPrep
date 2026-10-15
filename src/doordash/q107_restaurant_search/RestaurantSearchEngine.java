package doordash.q107_restaurant_search;

import java.util.*;

// Restaurant search engine built on a trie (prefix tree). Names only contain
// lowercase letters and spaces. Supports inserting names, exact lookup,
// prefix-exists checks, and returning the top-k names for a prefix in sorted
// order (space sorts before 'a'; a node's own name comes before its children,
// so "pan" comes before "panda").
//
// Source: https://codezym.com/question/107-doordash-restaurant-search-engine
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/101-300/107-doordash-restaurant-search-engine/q-107-java-tutorial.md
public class RestaurantSearchEngine {

    TrieNode root = new TrieNode();

    public RestaurantSearchEngine(List<String> restaurantNames) {
        // TODO: insert every name into the trie.
    }

    /** Walks down the trie, creates missing nodes, and saves the name at the last node. */
    public void insertRestaurant(String restaurantName) {
        // TODO
    }

    public boolean containsRestaurant(String restaurantName) {
        // TODO: true only if the path exists AND a name ends exactly at the last node.
        return false;
    }

    public boolean startsWith(String prefix) {
        // TODO: true if the prefix path exists (names are never removed).
        return false;
    }

    public List<String> getTopKRestaurants(String prefix, int k) {
        // TODO: walk to the node where the prefix ends; DFS from there in
        // sorted order (own name first, then children space..z), stopping at k.
        return new ArrayList<>();
    }

    /** Space goes to slot 0, and 'a' to 'z' go to slots 1 to 26. This matches sorted order. */
    int indexOf(char c) {
        return c == ' ' ? 0 : c - 'a' + 1;
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Tutorial Example 2: "pan" branches to pancake house / panda express / panera bread.
        RestaurantSearchEngine svc =
                new RestaurantSearchEngine(List.of("pancake house", "panda express", "panera bread"));

        check("top 3 for 'pan'",
                svc.getTopKRestaurants("pan", 3),
                List.of("pancake house", "panda express", "panera bread"));
        check("top 2 for 'pan'",
                svc.getTopKRestaurants("pan", 2),
                List.of("pancake house", "panda express"));
        check("contains 'panda express' -> true", svc.containsRestaurant("panda express"), true);
        check("contains 'pan' -> false (no name ends there)", svc.containsRestaurant("pan"), false);
        check("startsWith 'pan' -> true", svc.startsWith("pan"), true);
        check("startsWith 'xyz' -> false", svc.startsWith("xyz"), false);

        svc.insertRestaurant("pan");
        check("after insert, contains 'pan' -> true", svc.containsRestaurant("pan"), true);
        // A node's own name sorts before its children: "pan" comes first.
        check("top 4 for 'pan' after insert",
                svc.getTopKRestaurants("pan", 4),
                List.of("pan", "pancake house", "panda express", "panera bread"));
        check("unknown prefix -> []", svc.getTopKRestaurants("zzz", 2), List.of());

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}

/** One node of the trie. */
class TrieNode {
    // children[0] is for space, children[1] to children[26] are for 'a' to 'z'
    TrieNode[] children = new TrieNode[27];

    // Full restaurant name if a name ends at this node, otherwise null
    String name;
}
