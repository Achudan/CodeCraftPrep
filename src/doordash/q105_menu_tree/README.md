# Count and Print Changed Nodes in DoorDash Menu Tree

**CodeZym:** https://codezym.com/question/105-count-changed-nodes-doordash-menu-tree
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

At DoorDash, menus are updated daily and even hourly to keep them up-to-date. Each menu can be regarded as a tree. When the merchant sends the latest menu, calculate how many nodes have changed, been added, or been deleted.

## Notes

The spec doesn't define node identity or what counts as "changed"; the exercise documents these readings:

- Nodes are matched by `id`.
- "Changed" = same `id` but different `name`; a node that only moved (re-parented) does not count as changed.
- A `null` root means an empty menu.
- Result is a `ChangeCounts(added, deleted, changed)` record.
