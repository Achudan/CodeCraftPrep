package doordash.q271_best_reviews;

import java.util.*;

// Q271 — Best Reviews and Monthly Rewards System (EXERCISE — fill the TODOs).
//
// Spec (from the DoorDash 2026 problem list):
//   "Design a review system where viewers can write reviews, rate reviews
//    written by other viewers, and reward the best reviews for each calendar
//    month."
// Source: https://codezym.com/question/271-best-reviews-and-monthly-rewards
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Documented readings (the spec leaves these open; stated here so the tests
// below are unambiguous):
//   - Each review belongs to exactly one calendar month, supplied by the
//     caller as an opaque label (e.g. "2026-10") at creation time.
//   - addReview returns a unique review id (String).
//   - rateReview records one integer rating from a rater for a review.
//   - "Best" = highest AVERAGE rating among reviews of that month.
//     Reviews with no ratings average 0.0. Ties: return every tied review,
//     in the order their reviews were added.
public class BestReviewsRewards {

    // Write a review for a calendar month. Returns the new review's id.
    // TODO
    public String addReview(String reviewerId, String content, String month) {
        // TODO
        return null;
    }

    // Rate a review written by another viewer.
    // TODO
    public void rateReview(String raterId, String reviewId, int rating) {
        // TODO
    }

    // Ids of the best review(s) for a calendar month (see header for the
    // definition of "best"). Unknown month -> empty list.
    // TODO
    public List<String> bestReviews(String month) {
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
        BestReviewsRewards sys = new BestReviewsRewards();
        String r1 = sys.addReview("u1", "Great!", "2026-10");
        String r2 = sys.addReview("u2", "Meh.", "2026-10");
        String r3 = sys.addReview("u3", "Old news", "2026-09");

        sys.rateReview("u9", r1, 5);
        sys.rateReview("u8", r1, 3); // avg 4.0
        sys.rateReview("u9", r2, 5); // avg 5.0 -> best of October
        sys.rateReview("u9", r3, 5); // September only

        check("best of 2026-10 -> [r2]", sys.bestReviews("2026-10"), Arrays.asList(r2));
        check("best of 2026-09 -> [r3]", sys.bestReviews("2026-09"), Arrays.asList(r3));
        check("unknown month -> []", sys.bestReviews("2026-01"), List.of());

        // Tie: r4 also averages 5.0 in October -> both, in insertion order.
        String r4 = sys.addReview("u4", "Loved it", "2026-10");
        sys.rateReview("u7", r4, 5);
        check("tie in 2026-10 -> [r2, r4]", sys.bestReviews("2026-10"), Arrays.asList(r2, r4));

        System.out.println(anyFail ? "\nSome tests failing — keep going." : "\nAll tests pass.");
    }
}
