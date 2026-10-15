package doordash.q276_lru_cache;

import java.util.*;

// Q276 — Modified LRU Cache for Dasher Delivery Assignments
// (EXERCISE — fill the TODOs).
//
// Spec (from the DoorDash 2026 problem list):
//   "Implement a fixed-capacity cache that stores the most recent delivery
//    assigned to each dasher. The cache keeps dashers based only on how
//    recently their entries were added or updated."
// Source: https://codezym.com/question/276-modified-lru-cache-dasher-delivery
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented readings:
//   - Classic LRU semantics: get(dasherId) returns the stored delivery id, or
//     null when the dasher is absent; get refreshes recency.
//   - put(dasherId, deliveryId) adds a new entry or updates the existing one;
//     both adding and updating refresh recency.
//   - When the cache is full, put evicts the least recently used dasher.
public class DasherLRUCache {

    // TODO: pick your structure (e.g. HashMap + doubly-linked list, or a
    // LinkedHashMap in access order) and store the capacity.

    public DasherLRUCache(int capacity) {
        // TODO
    }

    // Most recent delivery for the dasher, or null if absent. Refreshes recency.
    // TODO
    public String get(String dasherId) {
        // TODO
        return null;
    }

    // Add or update the dasher's delivery; evicts the LRU dasher when full.
    // TODO
    public void put(String dasherId, String deliveryId) {
        // TODO
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        DasherLRUCache c = new DasherLRUCache(2);
        c.put("d1", "orderA");
        c.put("d2", "orderB");
        check("get d1 -> orderA", c.get("d1"), "orderA"); // d1 now most recent
        c.put("d3", "orderC");                           // evicts d2
        check("d2 evicted -> null", c.get("d2"), null);
        check("d1 still cached -> orderA", c.get("d1"), "orderA");
        check("d3 cached -> orderC", c.get("d3"), "orderC");

        c.put("d1", "orderA2"); // update refreshes d1's recency
        c.put("d4", "orderD");  // evicts d3, the LRU now
        check("d3 evicted after d1 update -> null", c.get("d3"), null);
        check("d1 holds updated delivery -> orderA2", c.get("d1"), "orderA2");
        check("d4 cached -> orderD", c.get("d4"), "orderD");

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
