# Aggregate Gift Card Data

**CodeZym:** https://codezym.com/question/102-aggregate-gift-card-data
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

Design a system to manage payments using gift cards.

The system should support adding users with gift cards, making payments using one or more gift cards, and returning aggregated gift card data for a user.

A user can use multiple gift cards in a single payment.

## Notes

The spec leaves the aggregation shape and payment policy open; the exercise documents these readings:

- Aggregated gift card data = total remaining balance + number of cards.
- A payment draws from the user's cards in insertion order and is all-or-nothing: if the cards can't cover the amount, the payment fails and no balances change.
- Unknown user → payment returns `false`, aggregated data is zeros.
