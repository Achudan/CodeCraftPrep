// DoorDash CodeCraft — Day 6 (Wed Oct 14): COLD full mock canvas.
// Build the whole thing from scratch in this file. No peeking at day2/.
//
// INTERVIEWER PROMPT (reading this is allowed — re-opening Day 2 is not):
//
//   "Design a Workflow Automation Engine for DoorDash's self-help menu.
//    Keep users, restaurants, and orders in memory.
//
//    Part 1 — Order lifecycle: an order is OPEN when created. It can move
//    OPEN -> DELIVERED or OPEN -> CANCELLED. Once an order is DELIVERED or
//    CANCELLED it is closed: further status updates that are not refunds
//    are ignored. Updating an unknown order id must fail cleanly (no crash,
//    no exception to the caller).
//
//    Part 2 — Refund rules: add a refund policy engine.
//      * Late delivery: <= 10 min late -> 0% refund;
//        11-30 min late -> 50% refund; > 30 min late -> 100% refund.
//      * Cancellation: cancelled before prep started -> 95% refund;
//        cancelled after prep started -> 0% refund.
//    New refund rules should be addable without touching existing rules."
//
// COLD PROTOCOL: close every other drill file, start a 45:00 timer, narrate
// out loud the whole time. Write your own tests BEFORE the implementation.

public class MockSession {
    public static void main(String[] args) {
        System.out.println("Day 6 cold mock: Workflow Automation Engine, 45:00, out loud.");
        System.out.println("1. State requirements + refund thresholds BEFORE coding.");
        System.out.println("2. Write your own tests first. Watch them fail.");
        System.out.println("3. Implement the guard, then the refund policies. Run tests as you go.");
        System.out.println("4. Debrief after the timer: time sinks, +15min plan, one line for Friday.");
    }
}
