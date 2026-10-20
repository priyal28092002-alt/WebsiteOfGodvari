package com.example.JavaTricksShowcase;

/**
 * Shows pre/post increment and decrement in expressions.
 */
public class IncrementDemo {
    public static void main(String[] args) {
        example1_basicChain();
        example2_selfAssignment();
        example3_arrayIndexing();
        example4_booleanShortCircuit();
        challenge();
    }

    static void example1_basicChain() {
        System.out.println("\n[IncrementDemo - Example 1] Basic pre/post mix");
        int x = 5;
        int y = x++ + ++x; // x++ uses 5 (then x=6); ++x makes x=7, uses 7
        System.out.println("x = " + x + ", y = " + y);
        // Prediction: Some think y=11 and x=6
        System.out.println("Prediction: x=6, y=11? ❌");
        System.out.println("Actual: x=7, y=" + y + " ✅");
        System.out.println("Key Takeaway: Post uses old value; pre uses incremented value.");
    }

    static void example2_selfAssignment() {
        System.out.println("\n[IncrementDemo - Example 2] Self assignment with post-increment");
        int a = 10;
        a = a++; // a++ returns old 10, then a becomes 11, but assignment writes old 10 back
        System.out.println("a = " + a);
        System.out.println("Prediction: 11? ❌  Actual: 10 ✅");
        System.out.println("Key Takeaway: a=a++ throws away the incremented value; prefer ++a or separate line.");
    }

    static void example3_arrayIndexing() {
        System.out.println("\n[IncrementDemo - Example 3] Using index++ in arrays");
        int[] arr = { 10, 20, 30 };
        int i = 0;
        int sum = arr[i++] + arr[i++]; // uses arr[0]=10 then i=1; uses arr[1]=20 then i=2
        System.out.println("i = " + i + ", sum = " + sum);
        System.out.println("Prediction: i=1? ❌  Actual: i=2, sum=30 ✅");
        System.out.println("Key Takeaway: Side-effects occur after the element is used for post-increment.");
    }

    static void example4_booleanShortCircuit() {
        System.out.println("\n[IncrementDemo - Example 4] Short-circuit with increments");
        int x = 0;
        boolean b = (x++ > 0) && (++x > 0); // first is false (0>0), second is NOT evaluated
        System.out.println("x = " + x + ", b = " + b);
        System.out.println("Prediction: x=2? ❌  Actual: x=1, b=false ✅");
        System.out.println("Key Takeaway: Right side of && is skipped if left is false; increments may not run.");
    }

    static void challenge() {
        System.out.println("\n[IncrementDemo - Challenge] Guess without running:");
        int a = 1;
        int res = a++ + a++ + ++a; // What are a and res?
        System.out.println("a and res? (Hidden)");
        // System.out.println("Answer → a=" + a + ", res=" + res); // <- Reveal by uncommenting
        System.out.println("Interview Hint: Frequently asked in service-company screens.");
    }
}