package com.example.JavaTricksShowcase;

/**
 * Demonstrates binary floating-point surprises and comparison strategies.
 */
public class FloatingPointGotchasDemo {
    public static void main(String[] args) {
        example1_decimalSum();
        example2_compareWithEpsilon();
        example3_intDivisionTrap();
        challenge();
    }

    static void example1_decimalSum() {
        System.out.println("\n[FloatingPointGotchasDemo - Example 1] 0.1 + 0.2");
        double x = 0.1 + 0.2;
        System.out.println(x); // 0.30000000000000004
        System.out.println("Prediction: 0.3? ❌  Actual: " + x + " ✅");
        System.out.println("Key Takeaway: Many decimals cannot be represented exactly in binary floating-point.");
    }

    static void example2_compareWithEpsilon() {
        System.out.println("\n[FloatingPointGotchasDemo - Example 2] Compare with tolerance");
        double a = 0.1 + 0.2;
        double b = 0.3;
        boolean equal = Math.abs(a - b) < 1e-9;
        System.out.println("equal? " + equal);
        System.out.println("Prediction: true ✅  Actual: " + equal + " ✅");
        System.out.println("Key Takeaway: Use an epsilon when comparing doubles in general numeric code.");
    }

    static void example3_intDivisionTrap() {
        System.out.println("\n[FloatingPointGotchasDemo - Example 3] int division then cast");
        int i = 5, j = 2;
        double r1 = i / j;        // 2.0
        double r2 = i / (double) j; // 2.5
        System.out.println("r1=" + r1 + ", r2=" + r2);
        System.out.println("Prediction: r1=2.0, r2=2.5 ✅  Actual: r1=" + r1 + ", r2=" + r2 + " ✅");
        System.out.println("Key Takeaway: Cast before division to avoid integer truncation.");
    }

    static void challenge() {
        System.out.println("\n[FloatingPointGotchasDemo - Challenge] Predict:");
        double d = 1.0 - 0.9 - 0.1;
        System.out.println("Hidden");
        // System.out.println(d);
        System.out.println("Interview Hint: Floating precision checked in coding rounds.");
    }
}