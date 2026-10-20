package com.example.JavaTricksShowcase;

/**
 * Chained increments and mixed operators with short-circuit effects.
 */
public class MixedEvaluationDemo {
    public static void main(String[] args) {
        example1_chain();
        example2_mixBooleanArithmetic();
        example3_shortCircuitSideEffects();
        challenge();
    }

    static void example1_chain() {
        System.out.println("\n[MixedEvaluationDemo - Example 1] Chained increments");
        int i = 1;
        int r = i++ + ++i + i; // 1 + 3 + 3 = 7
        System.out.println("i=" + i + ", r=" + r);
        System.out.println("Prediction: i=3, r=7 ✅  Actual: i=" + i + ", r=" + r + " ✅");
        System.out.println("Key Takeaway: Evaluate left-to-right; track post vs pre carefully.");
    }

    static void example2_mixBooleanArithmetic() {
        System.out.println("\n[MixedEvaluationDemo - Example 2] Booleans and arithmetic");
        int x = 0;
        boolean ok = (x++ == 0) | (++x == 2); // '|' (non-short-circuit) evaluates both
        System.out.println("x=" + x + ", ok=" + ok);
        System.out.println("Prediction: x=2, ok=true ✅  Actual: x=" + x + ", ok=" + ok + " ✅");
        System.out.println("Key Takeaway: '|' and '&' (single) always evaluate both sides.");
    }

    static void example3_shortCircuitSideEffects() {
        System.out.println("\n[MixedEvaluationDemo - Example 3] Short-circuit saving from NPE");
        String s = null;
        if (s != null && s.length() > 0) {
            System.out.println("Non-empty");
        } else {
            System.out.println("Empty or null");
        }
        System.out.println("Prediction: 'Empty or null' ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Use && to guard later dereferences.");
    }

    static void challenge() {
        System.out.println("\n[MixedEvaluationDemo - Challenge] Predict:");
        int a = 1;
        System.out.println("Hidden");
        // System.out.println(a++ + a + ++a);
        System.out.println("Interview Hint: Track each increment precisely.");
    }
}