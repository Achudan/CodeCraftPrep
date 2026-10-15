package doordash.q102_gift_cards;

import java.util.*;

// Spec (DoorDash 2026 problem list, Q102 — quoted from the list):
// "Design a system to manage payments using gift cards. The system should support adding
//  users with gift cards, making payments using one or more gift cards, and returning
//  aggregated gift card data for a user. A user can use multiple gift cards in a single
//  payment."
// Source: https://codezym.com/question/102-aggregate-gift-card-data
// Spec source: DoorDash 2026 problem list (no full tutorial in repo)
//
// Readings chosen where the spec is silent (documented here, not from the spec):
// - "Aggregated gift card data" = total remaining balance + number of cards.
// - pay() draws from the user's cards in the order they were added, and is
//   all-or-nothing: if the total balance is less than the amount (or the amount
//   is not positive), it returns false and changes nothing.
// - Unknown user: pay() returns false; aggregated data is zeros.
public class GiftCardService {

    public record GiftCard(String cardId, double amount) {}

    public record AggregatedData(double totalBalance, int cardCount) {}

    private final Map<String, List<GiftCard>> cardsByUser = new HashMap<>();

    public void addUser(String userId) {
        // TODO: register the user (no cards yet).
    }

    public void addGiftCard(String userId, String cardId, double amount) {
        // TODO: attach a new gift card to the user, keeping insertion order.
    }

    // Pay `amount` using one or more of the user's gift cards.
    public boolean pay(String userId, double amount) {
        // TODO: all-or-nothing multi-card payment.
        return false;
    }

    // Aggregated gift card data for the user.
    public AggregatedData aggregatedData(String userId) {
        // TODO: total remaining balance + card count.
        return new AggregatedData(0.0, 0);
    }

    private static boolean anyFail = false;

    static void check(String name, Object actual, Object expected) {
        boolean ok = Objects.equals(String.valueOf(actual), String.valueOf(expected));
        System.out.printf("%-52s %s  (got %s, want %s)%n", name, ok ? "PASS" : "FAIL", actual, expected);
        if (!ok) anyFail = true;
    }

    public static void main(String[] args) {
        GiftCardService svc = new GiftCardService();
        svc.addUser("u1");
        svc.addGiftCard("u1", "g1", 50.0);
        svc.addGiftCard("u1", "g2", 30.0);

        check("total balance -> 80.0", svc.aggregatedData("u1").totalBalance(), 80.0);
        check("card count -> 2", svc.aggregatedData("u1").cardCount(), 2);

        // $60 spans both cards (g1 fully, then $10 of g2).
        check("pay 60 -> true", svc.pay("u1", 60.0), true);
        check("balance after pay -> 20.0", svc.aggregatedData("u1").totalBalance(), 20.0);

        // Cannot cover $25 with $20 left: nothing changes.
        check("pay 25 > balance -> false", svc.pay("u1", 25.0), false);
        check("balance unchanged -> 20.0", svc.aggregatedData("u1").totalBalance(), 20.0);

        // Exact remaining balance works.
        check("pay exact 20 -> true", svc.pay("u1", 20.0), true);
        check("balance now -> 0.0", svc.aggregatedData("u1").totalBalance(), 0.0);

        // Unknown user.
        check("unknown user pay -> false", svc.pay("nope", 10.0), false);
        check("unknown user balance -> 0.0", svc.aggregatedData("nope").totalBalance(), 0.0);
        check("unknown user card count -> 0", svc.aggregatedData("nope").cardCount(), 0);

        System.out.println(anyFail ? "\nSome tests FAILING — implement the TODOs."
                                   : "\nAll tests PASS.");
    }
}
