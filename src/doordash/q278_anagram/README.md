# Names Requiring Minimum Changes to Become an Anagram

**CodeZym:** https://codezym.com/question/278-names-minimum-changes-anagram
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

You are given a string name and a list of strings nameList. Return every string from nameList that requires the minimum number of character changes to become an anagram of name.

## Notes

"Character changes" = single-character replacements. The exercise documents these readings:

- For equal-length strings, `changes = Σ |count_s(c) − count_name(c)| / 2` over all characters.
- Different-length strings can never become anagrams by replacements alone, so they are excluded from candidacy.
- Returns all candidates tied at the minimum change count, in `nameList` order.
