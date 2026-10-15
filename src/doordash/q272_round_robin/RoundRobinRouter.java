package doordash.q272_round_robin;

import java.util.*;

// DEBUGGING DRILL: the code below is close to correct but contains planted
// logic bugs. Find and fix them — the tests fail until the behavior is right.
// Do not change the tests; change the implementation.
//
// Traffic router that distributes incoming requests across backend servers
// using round-robin selection. Backends are kept in the order they were added
// and each request goes to the next AVAILABLE one, wrapping around the list.
// Backends marked "UNAVAILABLE" are skipped. With nothing available (or no
// backends at all), getBackend returns "". The requestId must not affect
// the choice.
//
// Suspect areas (from the tutorial's bug table): how the rotation position is
// stored and advanced between requests, wrap-around of the scan, and which
// backend updateBackendState actually modifies.
//
// Source: https://codezym.com/question/272-debug-round-robin-traffic-router
// Tutorial: https://raw.githubusercontent.com/prsnt558908/CodeZymSolutions/main/101-300/272-debug-round-robin-traffic-router/q-272-java-tutorial.md
// (The tutorial names this class TrafficRouter; the API below is identical.)

/** One backend server. */
class Backend {
    String backendId;
    String hostname;
    int port;
    String state;

    Backend(String backendId, String hostname, int port, String state) {
        this.backendId = backendId;
        this.hostname = hostname;
        this.port = port;
        this.state = state;
    }

    // The only place that checks the state text, so a typo cannot hide elsewhere.
    boolean isAvailable() {
        return "AVAILABLE".equals(state);
    }
}

public class RoundRobinRouter {

    // Backends in the order they were added. This is the round-robin order.
    List<Backend> backends;

    // backendId -> backend, so updateBackendState does not have to search the list.
    Map<String, Backend> backendById;

    // Index where the next search starts.
    int nextIndex;

    public RoundRobinRouter() {
        backends = new ArrayList<>();
        backendById = new HashMap<>();
        nextIndex = 0;
    }

    public void addBackend(String backendId, String hostname, int port, String state) {
        Backend backend = new Backend(backendId, hostname, port, state);
        backends.add(backend); // add at the end, never replace a backend
        backendById.put(backendId, backend);
    }

    public void updateBackendState(String backendId, String state) {
        if (backends.isEmpty()) return;
        backends.get(backends.size() - 1).state = state;
    }

    public String getBackend(String requestId) {
        int n = backends.size();
        nextIndex = 0;

        // Check each backend at most once, starting at nextIndex and wrapping around.
        for (int step = 0; step < n; step++) {
            int index = (nextIndex + step) % n;
            Backend backend = backends.get(index);
            if (backend.isAvailable()) {
                nextIndex = (nextIndex + 1) % n;
                return backend.backendId;
            }
        }

        // No backends, or all of them are unavailable.
        return "";
    }

    static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-44s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // Tutorial Example 2: east (available), central (unavailable), west (available).
        RoundRobinRouter ex2 = new RoundRobinRouter();
        ex2.addBackend("east", "east.host", 80, "AVAILABLE");
        ex2.addBackend("central", "central.host", 80, "UNAVAILABLE");
        ex2.addBackend("west", "west.host", 80, "AVAILABLE");
        check("request-a -> east", ex2.getBackend("request-a"), "east");
        check("request-b -> west (central skipped)", ex2.getBackend("request-b"), "west");
        check("request-c -> east (wrapped)", ex2.getBackend("request-c"), "east");

        // Tutorial Example 3: state changes must hit the right backend.
        RoundRobinRouter ex3 = new RoundRobinRouter();
        ex3.addBackend("primary", "p.host", 80, "AVAILABLE");
        ex3.addBackend("secondary", "s.host", 80, "AVAILABLE");
        check("request-1 -> primary", ex3.getBackend("request-1"), "primary");
        ex3.updateBackendState("secondary", "UNAVAILABLE");
        check("request-2 -> primary (secondary down)", ex3.getBackend("request-2"), "primary");
        ex3.updateBackendState("secondary", "AVAILABLE");
        check("request-3 -> secondary (rotation continued)", ex3.getBackend("request-3"), "secondary");

        // Updating a backend that was NOT added last must still work.
        RoundRobinRouter upd = new RoundRobinRouter();
        upd.addBackend("a", "a.host", 80, "AVAILABLE");
        upd.addBackend("b", "b.host", 80, "AVAILABLE");
        upd.updateBackendState("a", "UNAVAILABLE");
        check("update first backend -> b picked", upd.getBackend("r1"), "b");

        // Nothing to pick.
        RoundRobinRouter none = new RoundRobinRouter();
        none.addBackend("x", "x.host", 80, "UNAVAILABLE");
        check("all unavailable -> \"\"", none.getBackend("r"), "");
        RoundRobinRouter empty = new RoundRobinRouter();
        check("no backends -> \"\"", empty.getBackend("r"), "");

        System.out.println(anyFail ? "SOME TESTS FAILED" : "ALL TESTS PASSED");
    }
}
