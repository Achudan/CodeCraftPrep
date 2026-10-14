import java.util.*;

// Changed Nodes in Menu Tree — YOUR attempt, clean slate.
// Implement the methods below, then copy them into MenuTreeDiff.java
// (replacing the TODOs) to run the built-in tests.
public class MenuTreeDiffAttempt {

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
        public final List<String> added = new ArrayList<>();
        public final List<String> deleted = new ArrayList<>();
        public final List<String> changed = new ArrayList<>();
    }

    public static Node parse(List<String> lines) {
        return null;
    }

    public static Diff diff(Node oldRoot, Node newRoot) {
        return new Diff();
    }
}
