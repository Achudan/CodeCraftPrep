# Aggregate Gift Card Data

A small gift-card service: issue cards, report aggregates per user, and pay for
an order by draining cards in the order they were issued (FIFO).

## The problem

- `totalBalance(userId)` — sum of remaining balances across the user's cards
- `totalSpent(userId)` — sum of `initialAmount - balance` (what's been used)
- `activeCardCount(userId)` — cards with `balance > 0`
- `pay(userId, amount)` — drain the earliest-issued card first, then the next,
  until `amount` is covered
- `totalRemainingAllUsers()` — system-wide remaining balance

The stated rule for `pay` (say it before coding): **all-or-nothing**. If the
user's total balance is less than `amount` (or `amount <= 0`), return `false`
and leave every card untouched.

## Key ideas

1. Keep per-user cards in a `List` in **issued order** — payment order depends
   on insertion order, so don't use a `Set`.
2. In `pay`, **check the total first**, then sweep. Checking first is what makes
   the all-or-nothing guarantee trivially true.
3. Unknown user → `0.0` / `0`, never an exception.

## Files

| File | What it is |
|---|---|
| `GiftCards.java` | **Exercise.** TODOs for you; tests fail until you fill them. |
| `GiftCardsSolution.java` | **Reference.** Complete, all tests pass. |

## Run

```powershell
cd gift-cards
javac GiftCards.java && java GiftCards
javac GiftCardsSolution.java && java GiftCardsSolution
```

## Interview tips

- The drill here is **entity/manager design**, not algorithms: clean `User` /
  `GiftCard` records, one manager class, no logic leaking into the wrong place.
- The FIFO + all-or-nothing `pay` is the part interviewers probe — narrate the
  rule before you type it.
