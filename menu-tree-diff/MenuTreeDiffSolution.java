import java.util.*;

// Changed Nodes in Menu Tree — REFERENCE SOLUTION (all tests pass).
// Run: javac MenuTreeDiffSolution.java && java MenuTreeDiffSolution
// The exercise version is MenuTreeDiff.java (same tests, TODOs for you).
public class MenuTreeDiffSolution {

    public static class Node {
        public final String key;
        public String value;
        public final List<Node> children = new ArrayList<>();

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class Diff {
        public final List<String> added = new ArrayList<>();   // keys only in the new tree
        public final List<String> deleted = new ArrayList<>(); // keys only in the old tree
        public final List<String> changed = new ArrayList<>(); // keys in both, different value
    }

    // ---- The drill ----

    // Parse lines of the form "key,value,childKey1;childKey2". The children
    // segment is optional and may be empty. Lines may arrive in ANY order —
    // build all nodes first, wire children in a second pass (say that out
    // loud before coding). The root is the key never named as anyone's child.
    // Duplicate keys: last line wins. Values contain no commas.
    public static Node parse(List<String> lines) {
        // Pass 1: build every node first (lines may arrive in ANY order).
        // Duplicate keys: last line wins for both value and children.
        Map<String, Node> nodes = new LinkedHashMap<>();
        Map<String, List<String>> childKeys = new HashMap<>();
        for (String line : lines) {
            String[] parts = line.split(",", -1);
            String key = parts[0];
            String value = parts.length > 1 ? parts[1] : "";
            Node n = nodes.computeIfAbsent(key, k -> new Node(k, ""));
            n.value = value;
            List<String> kids = new ArrayList<>();
            if (parts.length > 2 && !parts[2].isEmpty()) {
                for (String k : parts[2].split(";")) {
                    if (!k.isEmpty()) kids.add(k);
                }
            }
            childKeys.put(key, kids);
        }
        // Pass 2: wire children. The root is the key never named as a child.
        Set<String> isChild = new HashSet<>();
        for (Map.Entry<String, List<String>> e : childKeys.entrySet()) {
            Node parent = nodes.get(e.getKey());
            for (String k : e.getValue()) {
                isChild.add(k);
                Node child = nodes.get(k);
                if (parent != null && child != null) parent.children.add(child);
            }
        }
        for (String key : nodes.keySet()) {
            if (!isChild.contains(key)) return nodes.get(key);
        }
        return null;
    }

    // Diff two trees BY KEY.
    //   added   = keys in new but not old
    //   deleted = keys in old but not new
    //   changed = keys in both whose VALUE differs
    // A pure re-parenting or child-list change alone does NOT count as
    // changed (state that rule). All three lists are sorted so the answer
    // is deterministic.
    public static Diff diff(Node oldRoot, Node newRoot) {
        Map<String, String> oldMap = flatten(oldRoot);
        Map<String, String> newMap = flatten(newRoot);
        Diff d = new Diff();
        for (String k : newMap.keySet()) {
            if (!oldMap.containsKey(k)) d.added.add(k);
            else if (!Objects.equals(oldMap.get(k), newMap.get(k))) d.changed.add(k);
        }
        for (String k : oldMap.keySet()) {
            if (!newMap.containsKey(k)) d.deleted.add(k);
        }
        // Sorted so the answer is deterministic.
        Collections.sort(d.added);
        Collections.sort(d.deleted);
        Collections.sort(d.changed);
        return d;
    }

    private static Map<String, String> flatten(Node root) {
        Map<String, String> out = new HashMap<>();
        if (root == null) return out;
        ArrayDeque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            out.put(n.key, n.value);
            for (Node c : n.children) stack.push(c);
        }
        return out;
    }

    // ---- Test harness ----

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    private static List<String> childKeys(Node n) {
        List<String> keys = new ArrayList<>();
        for (Node c : n.children) keys.add(c.key);
        return keys;
    }

    public static void main(String[] args) {
        // Lines deliberately shuffled: root is NOT first.
        Node root = parse(List.of(
            "soda,Soda,",
            "root,Menu,pizza;drinks",
            "drinks,Drinks,soda",
            "pizza,Pizza,"
        ));
        check("root found despite shuffle -> root", root.key, "root");
        check("root value -> Menu", root.value, "Menu");
        check("root children -> [pizza, drinks]", childKeys(root), List.of("pizza", "drinks"));

        Node oldTree = parse(List.of(
            "root,Menu,pizza;drinks",
            "pizza,Pizza,",
            "drinks,Drinks,soda",
            "soda,Soda,"
        ));
        Node newTree = parse(List.of(
            "root,Menu,pizza;dessert",
            "pizza,Pizza Classic,",
            "dessert,Dessert,cake",
            "cake,Cake,"
        ));
        Diff d = diff(oldTree, newTree);
        // Say these out loud BEFORE running: drinks+soda are gone, dessert+cake
        // are new, pizza's value changed. root's child LIST changed but its
        // value didn't — per the stated rule, root is NOT "changed".
        check("added -> [cake, dessert]", d.added, List.of("cake", "dessert"));
        check("deleted -> [drinks, soda]", d.deleted, List.of("drinks", "soda"));
        check("changed -> [pizza]", d.changed, List.of("pizza"));

        // Identical trees: everything empty.
        Diff same = diff(oldTree, parse(List.of(
            "root,Menu,pizza;drinks",
            "pizza,Pizza,",
            "drinks,Drinks,soda",
            "soda,Soda,"
        )));
        check("identical trees -> added empty", same.added, List.of());
        check("identical trees -> deleted empty", same.deleted, List.of());
        check("identical trees -> changed empty", same.changed, List.of());

        // Value-only change, same keys and structure.
        Diff v = diff(parse(List.of("a,1,b", "b,2,")),
                      parse(List.of("a,1,b", "b,3,")));
        check("value change -> changed [b]", v.changed, List.of("b"));
        check("value change -> added empty", v.added, List.of());
        check("value change -> deleted empty", v.deleted, List.of());

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass. Note your time, then stop.");
    }
}
