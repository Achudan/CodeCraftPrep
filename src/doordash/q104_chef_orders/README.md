# Chef Order Preparation List

**CodeZym:** https://codezym.com/question/104-chef-order-preparation-list
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

A chef receives all his orders for the day as a list of unique order ids. He creates a new list by repeatedly removing the smallest eligible order from the current list and appending it to the new list. Return the order in which the chef creates the new list.

## Notes

The spec doesn't define "eligible" beyond the list itself, so the exercise takes the most literal reading: repeatedly remove the minimum of the current list (equivalent to ascending sort). The input list must not be mutated.
