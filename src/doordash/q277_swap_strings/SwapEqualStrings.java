package doordash.q277_swap_strings;

import java.util.*;

// Q277 — Strings That Have Two Letters Swapped (EXERCISE — fill the TODO).
//
// Spec (from the DoorDash 2026 problem list):
//   "You are given a string name and a list of strings nameList. Return every
//    string from nameList that can be made equal to name by swapping exactly
//    two characters in that string."
// Source: https://codezym.com/question/277-strings-two-letters-swapped
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented reading (the spec's "exactly two" is taken literally):
//   - A candidate must have the SAME length as name.
//   - It must differ from name in EXACTLY two positions i < j, and swapping
//     s[i] with s[j] must make it equal to name.
//   - An identical string needs 0 swaps, so it does NOT qualify (swapping
//     two equal characters does not count as "swapping two characters" here).
// Result order: the order the strings appear in nameList.
public class SwapEqualStrings {

    // Every string in nameList that becomes equal to name after swapping
    // exactly two of its characters (see header for the exact rule).
    // TODO
    public static List<String> swappedEqual(String name, List<String> nameList) {
        // TODO
        return new ArrayList<>();
    }

    private static boolean anyFail = false;

    private static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-46s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        // "conevrse": swap positions 3,4 -> "converse". "eonversc": swap 0,7.
        // "converse" itself: 0 swaps needed -> excluded. The rest fail the rule.
        List<String> list = List.of("converse", "conevrse", "eonversc", "conversf", "convers", "coversen");
        check("exactly-two-swap matches, in list order",
                swappedEqual("converse", list), List.of("conevrse", "eonversc"));

        check("nothing qualifies -> []",
                swappedEqual("converse", List.of("converse", "abc")), List.of());
        check("empty list -> []",
                swappedEqual("converse", List.of()), List.of());

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
