package com.example.JavaTricksShowcase;

/**
 * String immutability, pool behavior, and concatenation rules.
 */
public class StringTrap {
    public static void main(String[] args) {
        example1_poolVsNew();
        example2_concatWithLiterals();
        example3_concatWithNull();
        example4_stringBuilderContrast();
        challenge();
    }

    static void example1_poolVsNew() {
        System.out.println("\n[StringTrap - Example 1] Pool vs new String()");
        String a = "java";
        String b = "java";
        String c = new String("java");
        System.out.println("a==b: " + (a == b) + ", a==c: " + (a == c) + ", a.equals(c): " + a.equals(c));
        System.out.println("Prediction: a==b true, a==c false, equals true ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: Literals are pooled; 'new String' creates a distinct object.");
    }

    static void example2_concatWithLiterals() {
        System.out.println("\n[StringTrap - Example 2] Compile-time vs runtime concat");
        String x = "ja" + "va"; // compile-time constant → pooled
        String y = "ja";
        String z = y + "va";     // runtime concat → new
        System.out.println("(\"java\"==x): " + ("java" == x) + ", (\"java\"==z): " + ("java" == z));
        System.out.println("Prediction: true, false ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: Only compile-time constant expressions join the pool.");
    }

    static void example3_concatWithNull() {
        System.out.println("\n[StringTrap - Example 3] null in concatenation");
        String s = null;
        String r = s + "X"; // null converted to "null"
        System.out.println(r); // prints "nullX"
        System.out.println("Prediction: nullX ✅  Actual: " + r + " ✅");
        System.out.println("Key Takeaway: null + String yields \"null...\"; watch for bugs when building messages.");
    }

    static void example4_stringBuilderContrast() {
        System.out.println("\n[StringTrap - Example 4] StringBuilder vs String");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) sb.append(i);
        System.out.println(sb.toString());
        System.out.println("Prediction: 012 ✅  Actual: " + sb.toString() + " ✅");
        System.out.println("Key Takeaway: For many appends, StringBuilder is efficient; String is immutable.");
    }

    static void challenge() {
        System.out.println("\n[StringTrap - Challenge] Predict references:");
        String a = "a";
        String b = a.concat("b");
        System.out.println("Hidden");
        // System.out.println("Ans → a='" + a + "', b='" + b + "', a==\"ab\"? " + (a=="ab"));
        System.out.println("Interview Hint: Pooling vs concat shows up in MCQs.");
    }
}