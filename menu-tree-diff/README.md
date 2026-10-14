# Changed Nodes in Menu Tree

Parse a restaurant menu from flat lines, then diff two menu versions by key.

## The problem

**`parse(lines)`** — lines look like `key,value,childKey1;childKey2`. The
children segment is optional and may be empty. Lines arrive in **any order**;
the root is **never marked** — it's the key no other line names as a child.
Duplicate keys: last line wins.

**`diff(oldRoot, newRoot)`** — compare by key:

- `added` — keys only in the new tree
- `deleted` — keys only in the old tree
- `changed` — keys in both whose **value** differs

The stated rule: a pure re-parenting or child-list change alone does **not**
count as `changed`. All three lists are sorted so the answer is deterministic.

## Key ideas

1. **Two passes for parse**: build all nodes first, wire children second.
   (Say that sentence before coding — it's the whole trick.)
2. Root = `allKeys − childKeys`. One set difference, no special marker needed.
3. For diff, **flatten each tree to a `key → value` map** first; the three
   buckets are then just set operations.

## Files

| File | What it is |
|---|---|
| `MenuTreeDiff.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `MenuTreeDiffSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd menu-tree-diff
javac MenuTreeDiff.java && java MenuTreeDiff
javac MenuTreeDiffSolution.java && java MenuTreeDiffSolution
```

## Interview tips

- "Lines in any order" is testing whether you reach for two passes instead of
  hoping the input is nice. Name it explicitly.
- The `changed` rule (value-only) is a classic follow-up probe — state it
  before they ask.
