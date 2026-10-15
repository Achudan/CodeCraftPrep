# Design Best Reviews and Monthly Rewards System

**CodeZym:** https://codezym.com/question/271-best-reviews-and-monthly-rewards
**Spec source:** DoorDash 2026 problem list — no full tutorial exists for this question in the source repo, so this README carries the problem statement verbatim.

## Problem statement

Design a review system where viewers can write reviews, rate reviews written by other viewers, and reward the best reviews for each calendar month.

## Notes

API-design problem. The exercise uses a minimal surface faithful to the spec:

- `addReview(reviewerId, content, month)` → review id
- `rateReview(raterId, reviewId, rating)`
- `bestReviews(month)` → review ids

Readings: "best" = highest **average** rating (unrated reviews count as 0.0); ties return all tied reviews in insertion order; `month` is an opaque caller-supplied label.
