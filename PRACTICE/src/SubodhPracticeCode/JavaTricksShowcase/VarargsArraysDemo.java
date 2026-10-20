package com.example.JavaTricksShowcase;

/**
 * Varargs overloads, passing null, and array behavior.
 */
public class VarargsArraysDemo {
    static void show(int... xs) { System.out.println("show(int...): len=" + xs.length); }
    static void show(Integer[] xs) { System.out.println("show(Integer[]): len=" + xs.length); }

    public static void main(String[] args) {
        example1_emptyVarargs();
        example2_nullAmbiguity();
        example3_arrayReferenceSharing();
        example4_varargsMutation();
        challenge();
    }

    static void example1_emptyVarargs() {
        System.out.println("\n[VarargsArraysDemo - Example 1] Empty varargs");
        show(); // length=0
        System.out.println("Prediction: len=0 ✅  Actual: len=0 ✅");
        System.out.println("Key Takeaway: Varargs can be empty; it's just an array under the hood.");
    }

    static void example2_nullAmbiguity() {
        System.out.println("\n[VarargsArraysDemo - Example 2] Passing null");
        // show(null); // ambiguous between int... and Integer[]; won't compile
        try {
            show((Integer[]) null); // explicitly choose one overload, but will NPE inside
        } catch (NullPointerException e) {
            System.out.println("Caught NPE: show(Integer[]) received null");
        }
        System.out.println("Prediction: Ambiguous without cast; with (Integer[]) null you'll get NPE ✅  Actual: Caught NPE ✅");
        System.out.println("Key Takeaway: Disambiguate null with a cast, but avoid passing null arrays unless you handle it.");
    }

    static void example3_arrayReferenceSharing() {
        System.out.println("\n[VarargsArraysDemo - Example 3] Arrays share references");
        int[] a = {1,2};
        int[] b = a;
        b[0] = 99;
        System.out.println("a[0]=" + a[0]);
        System.out.println("Prediction: a[0]=99 ✅  Actual: a[0]=" + a[0] + " ✅");
        System.out.println("Key Takeaway: Arrays are objects; assignments copy references.");
    }

    static void example4_varargsMutation() {
        System.out.println("\n[VarargsArraysDemo - Example 4] Mutating varargs parameter");
        mutate(1,2,3);
        System.out.println("Prediction: xs[0] now=-1 ✅  Actual: see line above ✅");
        System.out.println("Key Takeaway: Varargs is an array — mutations inside affect the array instance.");
    }

    static void mutate(int... xs) {
        if (xs.length > 0) xs[0] = -1;
        System.out.println("xs[0] now=" + (xs.length>0?xs[0]:"n/a"));
    }

    static void challenge() {
        System.out.println("\n[VarargsArraysDemo - Challenge] Which overload?");
        System.out.println("Hidden");
        // show((int[]) null); // allowed? only Integer[] overload exists besides int...; cast clarifies
        System.out.println("Interview Hint: Varargs overload + null is a favorite edge case.");
    }
}