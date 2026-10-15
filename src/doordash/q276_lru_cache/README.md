# Modified LRU Cache for Dasher Delivery Assignments

**CodeZym:** https://codezym.com/question/276-modified-lru-cache-dasher-delivery
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

Implement a fixed-capacity cache that stores the most recent delivery assigned to each dasher. The cache keeps dashers based only on how recently their entries were added or updated.

## Notes

Straight classic LRU, keyed by dasher:

- `get(dasherId)` returns the delivery id (or `null` when absent) and refreshes recency.
- `put(dasherId, deliveryId)` adds or updates — both refresh recency — and evicts the least-recently-used dasher when the cache is full.
