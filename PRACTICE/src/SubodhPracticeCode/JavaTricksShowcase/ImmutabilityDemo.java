package com.example.JavaTricksShowcase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creating truly immutable classes and common pitfalls.
 */
public class ImmutabilityDemo {
    // Broken immutable class
    static final class BrokenImmutable {
        private final List<String> items;
        BrokenImmutable(List<String> items) { this.items = items; }
        List<String> getItems() { return items; }
    }

    // Proper immutable class
    static final class ProperImmutable {
        private final List<String> items;
        ProperImmutable(List<String> items) { this.items = new ArrayList<>(items); }
        List<String> getItems() { return Collections.unmodifiableList(items); }
    }

    public static void main(String[] args) {
        example1_brokenImmutability();
        example2_properImmutability();
        example3_finalFieldWithMutable();
        example4_stringImmutability();
        challenge();
    }

    static void example1_brokenImmutability() {
        System.out.println("\n[ImmutabilityDemo - Example 1] Broken immutable class");
        List<String> list = new ArrayList<>();
        list.add("A");
        BrokenImmutable obj = new BrokenImmutable(list);
        list.add("B"); // modifies internal state!
        System.out.println("obj.getItems(): " + obj.getItems());
        System.out.println("Prediction: [A, B] ✅  Actual: " + obj.getItems() + " ✅");
        System.out.println("Key Takeaway: Must defensively copy mutable parameters.");
    }

    static void example2_properImmutability() {
        System.out.println("\n[ImmutabilityDemo - Example 2] Proper immutable class");
        List<String> list = new ArrayList<>();
        list.add("A");
        ProperImmutable obj = new ProperImmutable(list);
        list.add("B");
        System.out.println("obj.getItems(): " + obj.getItems());
        System.out.println("Prediction: [A] ✅  Actual: " + obj.getItems() + " ✅");
        System.out.println("Key Takeaway: Defensive copy in constructor and return unmodifiable view.");
    }

    static class Container {
        private final StringBuilder sb;
        Container(String s) { sb = new StringBuilder(s); }
        StringBuilder getSb() { return sb; }
    }

    static void example3_finalFieldWithMutable() {
        System.out.println("\n[ImmutabilityDemo - Example 3] final doesn't mean immutable");
        Container c = new Container("Hello");
        c.getSb().append(" World");
        System.out.println("c.getSb(): " + c.getSb());
        System.out.println("Prediction: Hello World ✅  Actual: " + c.getSb() + " ✅");
        System.out.println("Key Takeaway: final reference can't be reassigned, but object can be mutated.");
    }

    static void example4_stringImmutability() {
        System.out.println("\n[ImmutabilityDemo - Example 4] String immutability");
        String s1 = "Java";
        String s2 = s1;
        s1 = s1.concat(" 17");
        System.out.println("s1=" + s1 + ", s2=" + s2);
        System.out.println("Prediction: s1=Java 17, s2=Java ✅  Actual: s1=" + s1 + ", s2=" + s2 + " ✅");
        System.out.println("Key Takeaway: String operations create new objects; original unchanged.");
    }

    static void challenge() {
        System.out.println("\n[ImmutabilityDemo - Challenge] Is this class immutable?");
        System.out.println("Hidden");
        // final class X { private final int[] arr; X(int[] a){arr=a;} int[] get(){return arr;} }
        System.out.println("Interview Hint: Arrays are always mutable; need defensive copying.");
    }
}
