import java.util.*;

// DoorDash CodeCraft Day 4 (part B) — Changed Nodes in Menu Tree (Java starter)
// Run: javac MenuTreeDiff.java && java MenuTreeDiff
// Implement the TODOs. Tests in main() should all pass when you're done.
// Focus of the drill: (1) parsing "key,value,children" lines in ANY order,
// (2) finding the root without it being marked, (3) a key-based diff with
// added / deleted / changed buckets.
// Prep reconstruction from the locked plan notes (CodeZym 105), not a verbatim
// CodeZym spec — confirm the real method signatures at the link before the interview.
public class MenuTreeDiff {

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
        // TODO
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
        // TODO
        return new Diff();
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
