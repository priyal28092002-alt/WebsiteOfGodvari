package com.example.JavaTricksShowcase;

/**
 * Bitwise operators and common bit manipulation tricks.
 */
public class BitwiseOperatorsDemo {
    public static void main(String[] args) {
        example1_basicBitwise();
        example2_shiftOperators();
        example3_signedVsUnsignedShift();
        example4_bitTricks();
        challenge();
    }

    static void example1_basicBitwise() {
        System.out.println("\n[BitwiseOperatorsDemo - Example 1] Basic bitwise operations");
        int a = 5;  // 0101
        int b = 3;  // 0011
        System.out.println("a & b = " + (a & b)); // 0001 = 1
        System.out.println("a | b = " + (a | b)); // 0111 = 7
        System.out.println("a ^ b = " + (a ^ b)); // 0110 = 6
        System.out.println("~a = " + (~a));       // inverts all bits = -6
        System.out.println("Prediction: 1, 7, 6, -6 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: & (AND), | (OR), ^ (XOR), ~ (NOT) operate on bits.");
    }

    static void example2_shiftOperators() {
        System.out.println("\n[BitwiseOperatorsDemo - Example 2] Left and right shift");
        int x = 4; // 0100
        System.out.println("x << 1 = " + (x << 1)); // 1000 = 8 (multiply by 2)
        System.out.println("x >> 1 = " + (x >> 1)); // 0010 = 2 (divide by 2)
        System.out.println("Prediction: 8, 2 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: << multiplies by 2^n, >> divides by 2^n (signed).");
    }

    static void example3_signedVsUnsignedShift() {
        System.out.println("\n[BitwiseOperatorsDemo - Example 3] Signed vs unsigned right shift");
        int neg = -8; // negative number
        System.out.println("neg >> 1 = " + (neg >> 1));   // sign-extended: -4
        System.out.println("neg >>> 1 = " + (neg >>> 1)); // zero-filled: large positive
        System.out.println("Prediction: -4, large positive ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: >> preserves sign bit, >>> fills with zeros.");
    }

    static void example4_bitTricks() {
        System.out.println("\n[BitwiseOperatorsDemo - Example 4] Common bit tricks");
        int n = 7;
        System.out.println("Is " + n + " even? " + ((n & 1) == 0));
        System.out.println("Is " + n + " power of 2? " + ((n & (n-1)) == 0 && n > 0));
        int swap1 = 5, swap2 = 10;
        swap1 ^= swap2; swap2 ^= swap1; swap1 ^= swap2;
        System.out.println("After XOR swap: swap1=" + swap1 + ", swap2=" + swap2);
        System.out.println("Prediction: false, false, swap1=10, swap2=5 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Bit tricks for even/odd, power of 2, XOR swap.");
    }

    static void challenge() {
        System.out.println("\n[BitwiseOperatorsDemo - Challenge] What's the output?");
        System.out.println("Hidden");
        // System.out.println(5 & 6); System.out.println(5 | 6); System.out.println(5 ^ 6);
        System.out.println("Interview Hint: Bit manipulation is common in coding interviews.");
    }
}
