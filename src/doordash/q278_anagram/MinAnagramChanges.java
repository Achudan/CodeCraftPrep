package doordash.q278_anagram;

import java.util.*;

// Q278 — Names Requiring Minimum Changes to Become an Anagram
// (EXERCISE — fill the TODO).
//
// Spec (from the DoorDash 2026 problem list):
//   "You are given a string name and a list of strings nameList. Return every
//    string from nameList that requires the minimum number of character
//    changes to become an anagram of name."
// Source: https://codezym.com/question/278-names-minimum-changes-anagram
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented reading of "number of character changes" (the spec leaves the
// counting open; stated here so the tests below are unambiguous):
//   - One "change" = replacing a single character with another character.
//   - For two EQUAL-LENGTH strings, changes(s) =
//         (sum over every character c of |count_s(c) - count_name(c)|) / 2.
//     Each replacement fixes one surplus character and one deficit character.
//   - A string whose length differs from name's can never become an anagram
//     of name by character changes alone, so it is excluded from candidacy.
// Result order: the order the strings appear in nameList.
public class MinAnagramChanges {

    // Every string in nameList whose change-count (see header) is minimal
    // among the length-matching candidates. No length-matching candidates
    // -> empty list.
    // TODO
    public static List<String> minAnagramChanges(String name, List<String> nameList) {
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
        // "silent" and "enlist": 0 changes (already anagrams) -> the minimum.
        // "listex": 1 change. "abcxyz": 3 changes. "listens": wrong length.
        check("minimum-change names, in list order",
                minAnagramChanges("listen",
                        List.of("silent", "enlist", "listex", "abcxyz", "listens")),
                List.of("silent", "enlist"));

        // Minimum above zero: "abd" needs 1 change, "xyz" needs 3, "ab" excluded.
        check("minimum is 1, not 0",
                minAnagramChanges("abc", List.of("abd", "xyz", "ab")),
                List.of("abd"));

        check("no length-matching candidates -> []",
                minAnagramChanges("abc", List.of("ab", "abcd")), List.of());

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
