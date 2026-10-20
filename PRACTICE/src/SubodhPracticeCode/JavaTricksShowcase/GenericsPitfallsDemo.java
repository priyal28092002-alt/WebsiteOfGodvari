package com.example.JavaTricksShowcase;

import java.util.ArrayList;
import java.util.List;

/**
 * Type erasure, raw types, and wildcard confusion.
 */
public class GenericsPitfallsDemo {
    public static void main(String[] args) {
        example1_typeErasure();
        example2_rawTypes();
        example3_wildcardExtends();
        example4_wildcardSuper();
        challenge();
    }

    static void example1_typeErasure() {
        System.out.println("\n[GenericsPitfallsDemo - Example 1] Type erasure at runtime");
        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();
        System.out.println("Same class? " + (strings.getClass() == integers.getClass()));
        System.out.println("Prediction: true ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Generics are erased at runtime; both are ArrayList.");
    }

    static void example2_rawTypes() {
        System.out.println("\n[GenericsPitfallsDemo - Example 2] Raw types bypass type safety");
        List raw = new ArrayList();
        raw.add("String");
        raw.add(123); // no compile error with raw type
        System.out.println("raw: " + raw);
        System.out.println("Prediction: [String, 123] ✅  Actual: " + raw + " ✅");
        System.out.println("Key Takeaway: Avoid raw types; they bypass generic type checking.");
    }

    static void printList(List<? extends Number> list) {
        for (Number n : list) System.out.print(n + " ");
        System.out.println();
        // list.add(10); // compile error — can't add to ? extends
    }

    static void example3_wildcardExtends() {
        System.out.println("\n[GenericsPitfallsDemo - Example 3] ? extends (upper bound)");
        List<Integer> ints = List.of(1, 2, 3);
        printList(ints);
        System.out.println("Prediction: 1 2 3 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: ? extends is read-only; can't add elements.");
    }

    static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
        // Integer x = list.get(0); // compile error — returns Object
    }

    static void example4_wildcardSuper() {
        System.out.println("\n[GenericsPitfallsDemo - Example 4] ? super (lower bound)");
        List<Number> numbers = new ArrayList<>();
        addNumbers(numbers);
        System.out.println("numbers: " + numbers);
        System.out.println("Prediction: [10, 20] ✅  Actual: " + numbers + " ✅");
        System.out.println("Key Takeaway: ? super is write-only; can add but read returns Object.");
    }

    static void challenge() {
        System.out.println("\n[GenericsPitfallsDemo - Challenge] Can you create generic array?");
        System.out.println("Hidden");
        // List<String>[] arr = new List<String>[10]; // compile error
        System.out.println("Interview Hint: Cannot create arrays of parameterized types.");
    }
}
