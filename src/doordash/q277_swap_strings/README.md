# Strings That Have Two Letters Swapped

**CodeZym:** https://codezym.com/question/277-strings-two-letters-swapped
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are given a string name and a list of strings nameList. Return every string from nameList that can be made equal to name by swapping exactly two characters in that string.

## Notes

"Exactly two" is taken literally:

- The candidate must have the same length as `name`, differ in exactly 2 positions, and swapping those two characters must yield `name`.
- Identical strings (0 swaps needed) are **excluded**; swapping two equal characters does not count.
- Results come back in `nameList` order.
