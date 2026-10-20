package com.example.JavaTricksShowcase;

/**
 * Shows numeric promotions, widening and narrowing casts.
 */
public class CastingConfusion {
    public static void main(String[] args) {
        example1_bytePromotion();
        example2_narrowingOverflow();
        example3_charArithmetic();
        example4_floatDoubleDivide();
        challenge();
    }

    static void example1_bytePromotion() {
        System.out.println("\n[CastingConfusion - Example 1] byte + byte → int");
        byte b1 = 40, b2 = 50;
        // byte b3 = b1 + b2; // compile error
        byte b3 = (byte) (b1 + b2); // requires cast because result is int
        System.out.println("b3 = " + b3 + " (expect 90)");
        System.out.println("Prediction: Need cast, value 90 ✅  Actual: b3=90 ✅");
        System.out.println("Key Takeaway: Arithmetic promotes to int for byte/short/char.");
    }

    static void example2_narrowingOverflow() {
        System.out.println("\n[CastingConfusion - Example 2] Narrowing causes wrapping");
        int big = 130;
        byte small = (byte) big; // 130 → -126 due to overflow in 8 bits
        System.out.println("small = " + small + " (from 130)");
        System.out.println("Prediction: 130? ❌  Actual: -126 ✅");
        System.out.println("Key Takeaway: Narrowing keeps low-order bits; watch for overflow.");
    }

    static void example3_charArithmetic() {
        System.out.println("\n[CastingConfusion - Example 3] char math");
        char c = 'A'; // 65
        int code = c + 2; // promotes to int
        char d = (char) (c + 3);
        System.out.println("code = " + code + ", d = " + d);
        System.out.println("Prediction: code=67, d=D ✅  Actual: code=" + code + ", d=" + d + " ✅");
        System.out.println("Key Takeaway: char participates in arithmetic via int promotion.");
    }

    static void example4_floatDoubleDivide() {
        System.out.println("\n[CastingConfusion - Example 4] int division vs double");
        int a = 5, b = 2;
        double d1 = a / b; // integer division first → 2, then widened to 2.0
        double d2 = a / (double) b; // 2.5
        System.out.println("d1 = " + d1 + ", d2 = " + d2);
        System.out.println("Prediction: d1=2.0, d2=2.5 ✅  Actual: d1=" + d1 + ", d2=" + d2 + " ✅");
        System.out.println("Key Takeaway: Cast before division to avoid integer truncation.");
    }

    static void challenge() {
        System.out.println("\n[CastingConfusion - Challenge] Predict:");
        short s = 1;
        System.out.println("Hidden");
        // s = s + 1; // compile error (int)
        // s += 1;    // OK — implicit cast in compound assignment
        // System.out.println("Ans → s=" + s);
        System.out.println("Interview Hint: Compound assignments perform implicit casts.");
    }
}