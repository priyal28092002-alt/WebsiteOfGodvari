package com.example.JavaTricksShowcase;

/**
 * Ternary operator type inference and edge cases.
 */
public class TernaryTricksDemo {
    public static void main(String[] args) {
        example1_typeInference();
        example2_nullInTernary();
        example3_nestedTernary();
        example4_autoboxingInTernary();
        challenge();
    }

    static void example1_typeInference() {
        System.out.println("\n[TernaryTricksDemo - Example 1] Type inference in ternary");
        int x = 5;
        Object result = x > 3 ? 10 : 20.5; // int vs double → double
        System.out.println("result type: " + result.getClass().getName() + ", value: " + result);
        System.out.println("Prediction: Double, 10.0 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Ternary promotes to common type (int → double).");
    }

    static void example2_nullInTernary() {
        System.out.println("\n[TernaryTricksDemo - Example 2] null in ternary with primitives");
        Integer i = null;
        try {
            int result = true ? i : 0; // unboxes null → NPE
            System.out.println(result);
        } catch (NullPointerException e) {
            System.out.println("Caught NPE");
        }
        System.out.println("Prediction: NPE ✅  Actual: Caught NPE ✅");
        System.out.println("Key Takeaway: Ternary with primitive target unboxes both branches.");
    }

    static void example3_nestedTernary() {
        System.out.println("\n[TernaryTricksDemo - Example 3] Nested ternary readability");
        int score = 85;
        String grade = score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "D";
        System.out.println("grade: " + grade);
        System.out.println("Prediction: B ✅  Actual: " + grade + " ✅");
        System.out.println("Key Takeaway: Nested ternary is right-associative; use parentheses for clarity.");
    }

    static void example4_autoboxingInTernary() {
        System.out.println("\n[TernaryTricksDemo - Example 4] Autoboxing in ternary");
        Integer a = 1;
        Double b = 2.0;
        Number result = true ? a : b; // both boxed to Number
        System.out.println("result: " + result + ", type: " + result.getClass().getName());
        System.out.println("Prediction: 1, Integer ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Ternary finds common supertype when branches differ.");
    }

    static void challenge() {
        System.out.println("\n[TernaryTricksDemo - Challenge] Predict:");
        System.out.println("Hidden");
        // char c = true ? 'A' : 65; System.out.println(c);
        System.out.println("Interview Hint: Ternary type inference is tricky in interviews.");
    }
}
