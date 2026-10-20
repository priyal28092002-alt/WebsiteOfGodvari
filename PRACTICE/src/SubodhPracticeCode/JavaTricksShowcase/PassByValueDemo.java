package com.example.JavaTricksShowcase;

/**
 * Demonstrates that Java passes copies of references by value.
 */
public class PassByValueDemo {
    static class Box { int v; Box(int v){this.v=v;} }

    public static void main(String[] args) {
        example1_mutateField();
        example2_reassignReference();
        example3_wrappersAreImmutable();
        challenge();
    }

    static void increment(Box b) { b.v++; }

    static void replace(Box b) { b = new Box(999); } // reassigns local copy only

    static void example1_mutateField() {
        System.out.println("\n[PassByValueDemo - Example 1] Mutating object via reference");
        Box b = new Box(1);
        increment(b);
        System.out.println("b.v = " + b.v);
        System.out.println("Prediction: b.v=2 ✅  Actual: b.v=" + b.v + " ✅");
        System.out.println("Key Takeaway: Mutating through the reference affects the same object.");
    }

    static void example2_reassignReference() {
        System.out.println("\n[PassByValueDemo - Example 2] Reassigning parameter doesn't escape");
        Box b = new Box(1);
        replace(b);
        System.out.println("b.v = " + b.v);
        System.out.println("Prediction: b.v=1 ✅  Actual: b.v=" + b.v + " ✅");
        System.out.println("Key Takeaway: The reference itself is passed by value; reassigning param has no effect.");
    }

    static void example3_wrappersAreImmutable() {
        System.out.println("\n[PassByValueDemo - Example 3] Integer wrapper 'modification'");
        Integer n = 10;
        addOne(n);
        System.out.println("n = " + n);
        System.out.println("Prediction: n=10 ✅  Actual: n=" + n + " ✅");
        System.out.println("Key Takeaway: Wrappers are immutable; operations produce new objects.");
    }

    static void addOne(Integer x) { x = x + 1; }

    static void challenge() {
        System.out.println("\n[PassByValueDemo - Challenge] What prints?");
        Box b = new Box(5);
        mutateAndReassign(b);
        System.out.println("Hidden");
        // System.out.println("Ans → b.v=" + b.v);
        System.out.println("Interview Hint: Classic pass-by-value trick.");
    }

    static void mutateAndReassign(Box b) { b.v++; b = new Box(0); }
}