package com.example.JavaTricksShowcase;

/**
 * Subtle control flow behaviors with if/switch/loops/ternary.
 */
public class ControlFlowTricks {
    public static void main(String[] args) {
        example1_assignmentInIf();
        example2_switchFallthrough();
        example3_innerIncrementLoop();
        example4_nestedTernary();
        challenge();
    }

    static void example1_assignmentInIf() {
        System.out.println("\n[ControlFlowTricks - Example 1] Assignment vs equality in if");
        boolean flag = false;
        if (flag = true) { // assignment! sets flag to true
            System.out.println("Branch taken");
        }
        System.out.println("flag = " + flag);
        System.out.println("Prediction: Branch not taken? ❌  Actual: Branch taken, flag=true ✅");
        System.out.println("Key Takeaway: '==' compares, '=' assigns; watch for accidental assignment.");
    }

    static void example2_switchFallthrough() {
        System.out.println("\n[ControlFlowTricks - Example 2] switch fall-through");
        int day = 6;
        switch (day) {
            case 6:
            case 7:
                System.out.print("Weekend ");
                // no break → fall through
            default:
                System.out.println("— check schedule");
        }
        System.out.println("Prediction: Weekend — check schedule ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: Add 'break' to stop fall-through.");
    }

    static void example3_innerIncrementLoop() {
        System.out.println("\n[ControlFlowTricks - Example 3] Mutating loop counter inside");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            i++; // changes counter additionally
        }
        System.out.println("\nPrediction: 0 1 2? ❌  Actual: 0 2 ✅");
        System.out.println("Key Takeaway: Modifying the loop variable inside the loop changes iteration count.");
    }

    static void example4_nestedTernary() {
        System.out.println("\n[ControlFlowTricks - Example 4] Nested ternary — use parentheses");
        int score = 72;
        String grade = score >= 90 ? "A" : (score >= 75 ? "B" : (score >= 60 ? "C" : "D"));
        System.out.println("grade = " + grade);
        System.out.println("Prediction: grade=B? ❌  Actual: grade=C ✅");
        System.out.println("Key Takeaway: Use parentheses for clarity with nested ternaries.");
    }

    static void challenge() {
        System.out.println("\n[ControlFlowTricks - Challenge] What prints?");
        int x = 0;
        while (x++ < 2) {
            // empty
        }
        System.out.println("Hidden");
        // System.out.println("Ans → x=" + x); // off-by-one?
        System.out.println("Interview Hint: Off-by-one pitfalls commonly tested.");
    }
}