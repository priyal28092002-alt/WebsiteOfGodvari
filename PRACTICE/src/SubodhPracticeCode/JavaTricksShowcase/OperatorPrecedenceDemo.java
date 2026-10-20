package com.example.JavaTricksShowcase;

/**
 * Demonstrates how operators bind and associate.
 */
public class OperatorPrecedenceDemo {
    public static void main(String[] args) {
        example1_mathFirst();
        example2_ternaryVsPlus();
        example3_assignmentChaining();
        challenge();
    }

    static void example1_mathFirst() {
        System.out.println("\n[OperatorPrecedenceDemo - Example 1] *,/ before +,-");
        int r = 2 + 3 * 4; // 2 + (3*4) = 14
        System.out.println("Result = " + r);
        System.out.println("Prediction: 20? ❌  Actual: 14 ✅");
        System.out.println("Key Takeaway: Multiplication/division bind tighter than addition/subtraction.");
    }

    static void example2_ternaryVsPlus() {
        System.out.println("\n[OperatorPrecedenceDemo - Example 2] + with Strings vs ternary");
        int n = 5;
        String s = "Value:" + (n > 3 ? n : 0); // parentheses make intent clear
        System.out.println(s);
        System.out.println("Prediction: Value:5 ✅  Actual: " + s + " ✅");
        System.out.println("Key Takeaway: '+' with String can coerce early; use parentheses with ternary.");
    }

    static void example3_assignmentChaining() {
        System.out.println("\n[OperatorPrecedenceDemo - Example 3] Chained assignment associates right-to-left");
        int a, b, c;
        a = b = c = 1 + 2 * 3; // evaluate RHS first → 7, then assign right-to-left
        System.out.println("a=" + a + ", b=" + b + ", c=" + c);
        System.out.println("Prediction: a=7, b=7, c=7 ✅  Actual: a=" + a + ", b=" + b + ", c=" + c + " ✅");
        System.out.println("Key Takeaway: Assignment is right-associative in Java.");
    }

    static void challenge() {
        System.out.println("\n[OperatorPrecedenceDemo - Challenge] What prints?");
        int x = 1;
        System.out.println("Hidden");
        // System.out.println("Ans → " + (x + 2 < 5 ? x++ : ++x) + ", x=" + x);
        System.out.println("Interview Hint: Seen in campus drives for operator precedence.");
    }
}