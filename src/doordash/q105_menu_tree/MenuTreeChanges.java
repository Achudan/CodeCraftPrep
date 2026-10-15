package doordash.q105_menu_tree;

import java.util.*;

// Spec (DoorDash 2026 problem list, Q105 — quoted from the list):
// "At DoorDash, menus are updated daily and even hourly to keep them up-to-date. Each
//  menu can be regarded as a tree. When the merchant sends the latest menu, calculate how
//  many nodes have changed, been added, or been deleted."
// Source: https://codezym.com/question/105-count-changed-nodes-doordash-menu-tree
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Readings chosen where the spec is silent (documented here, not from the spec):
// - Nodes are identified by `id`. A node counts as "changed" when it exists in both
//   trees but its `name` differs. A pure structural move (same id, same name, different
//   parent) does not count as changed.
// - A null root is treated as an empty menu.
public class MenuTreeChanges {

    public static class MenuNode {
        public final String id;
        public String name;
        public final List<MenuNode> children = new ArrayList<>();

        public MenuNode(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public MenuNode addChild(MenuNode child) {
            children.add(child);
            return this;
        }
    }

    public record ChangeCounts(int added, int deleted, int changed) {}

    // added   = nodes only in the new menu
    // deleted = nodes only in the old menu
    // changed = nodes in both menus whose name differs
    public static ChangeCounts countChanges(MenuNode oldMenu, MenuNode newMenu) {
        // TODO: flatten both trees to id->name maps and compare.
        return new ChangeCounts(0, 0, 0);
    }

    private static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        MenuNode oldMenu = new MenuNode("root", "Menu");
        oldMenu.addChild(new MenuNode("pizza", "Pizza"));
        MenuNode drinks = new MenuNode("drinks", "Drinks");
        drinks.addChild(new MenuNode("soda", "Soda"));
        oldMenu.addChild(drinks);

        MenuNode newMenu = new MenuNode("root", "Menu");
        newMenu.addChild(new MenuNode("pizza", "Pizza Classic")); // changed name
        newMenu.addChild(new MenuNode("dessert", "Dessert"));     // added (drinks + soda deleted)

        ChangeCounts c = countChanges(oldMenu, newMenu);
        check("added -> 1 (dessert)", c.added(), 1);
        check("deleted -> 2 (drinks, soda)", c.deleted(), 2);
        check("changed -> 1 (pizza)", c.changed(), 1);

        ChangeCounts same = countChanges(oldMenu, oldMenu);
        check("identical menus -> added 0", same.added(), 0);
        check("identical menus -> deleted 0", same.deleted(), 0);
        check("identical menus -> changed 0", same.changed(), 0);

        ChangeCounts bothEmpty = countChanges(null, null);
        check("null, null -> added 0", bothEmpty.added(), 0);
        check("null, null -> deleted 0", bothEmpty.deleted(), 0);
        check("null, null -> changed 0", bothEmpty.changed(), 0);

        // Everything is new when the old menu is empty.
        ChangeCounts fromEmpty = countChanges(null, newMenu);
        check("null old -> added 3", fromEmpty.added(), 3);
        check("null old -> deleted 0", fromEmpty.deleted(), 0);

        System.out.println(anyFail ? "\nSome tests FAILING — implement the TODO."
                                   : "\nAll tests PASS.");
    }
}
