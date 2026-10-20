package com.example.JavaTricksShowcase;

/**
 * Subtle null handling and try/finally behaviors.
 */
public class NullAndExceptionsDemo {
    public static void main(String[] args) {
        example1_stringValueOf();
        example2_tryFinallyReturn();
        example3_instanceofWithNull();
        example4_catchOrder();
        challenge();
    }

    static void example1_stringValueOf() {
        System.out.println("\n[NullAndExceptionsDemo - Example 1] String.valueOf(null)");
        String s = null;
        System.out.println(String.valueOf(s)); // prints "null" not NPE
        System.out.println("Prediction: prints 'null' ✅  Actual: 'null' ✅");
        System.out.println("Key Takeaway: valueOf(null) returns \"null\"; s.toString() would NPE.");
    }

    static int returnsFinally() {
        try {
            return 1;
        } finally {
            return 2; // overrides try's return
        }
    }

    static void example2_tryFinallyReturn() {
        System.out.println("\n[NullAndExceptionsDemo - Example 2] finally overrides return");
        System.out.println(returnsFinally());
        System.out.println("Prediction: 2 ✅  Actual: 2 ✅");
        System.out.println("Key Takeaway: A return in finally overrides prior returns — avoid this.");
    }

    static void example3_instanceofWithNull() {
        System.out.println("\n[NullAndExceptionsDemo - Example 3] instanceof and null");
        String s = null;
        System.out.println(s instanceof String); // false
        System.out.println("Prediction: false ✅  Actual: false ✅");
        System.out.println("Key Takeaway: null is not an instance of any type.");
    }

    static void example4_catchOrder() {
        System.out.println("\n[NullAndExceptionsDemo - Example 4] Catch order matters");
        try {
            int x = 1/0;
            System.out.println(x);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException first");
        } catch (RuntimeException e) {
            System.out.println("RuntimeException");
        }
        System.out.println("Prediction: 'ArithmeticException first' ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Catch more specific exceptions before general ones.");
    }

    static void challenge() {
        System.out.println("\n[NullAndExceptionsDemo - Challenge] Predict:");
        Integer n = null;
        System.out.println("Hidden");
        // int x = n; System.out.println(x); // NPE or not?
        System.out.println("Interview Hint: Autounboxing with null explored often.");
    }
}