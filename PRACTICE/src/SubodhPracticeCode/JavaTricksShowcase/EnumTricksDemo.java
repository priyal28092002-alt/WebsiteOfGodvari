package com.example.JavaTricksShowcase;

/**
 * Enum constructor, abstract methods, and singleton pattern.
 */
public class EnumTricksDemo {
    enum Size {
        SMALL(10), MEDIUM(20), LARGE(30);
        private final int value;
        Size(int value) { this.value = value; }
        int getValue() { return value; }
    }

    enum Operation {
        PLUS { int apply(int a, int b) { return a + b; } },
        MINUS { int apply(int a, int b) { return a - b; } },
        TIMES { int apply(int a, int b) { return a * b; } };
        abstract int apply(int a, int b);
    }

    enum Singleton {
        INSTANCE;
        public void doSomething() { System.out.println("Singleton via enum"); }
    }

    public static void main(String[] args) {
        example1_enumConstructor();
        example2_enumWithAbstractMethod();
        example3_enumSingleton();
        example4_enumComparison();
        challenge();
    }

    static void example1_enumConstructor() {
        System.out.println("\n[EnumTricksDemo - Example 1] Enum with constructor");
        System.out.println("SMALL: " + Size.SMALL.getValue());
        System.out.println("LARGE: " + Size.LARGE.getValue());
        System.out.println("Prediction: 10, 30 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Enum constructors are private; called once per constant.");
    }

    static void example2_enumWithAbstractMethod() {
        System.out.println("\n[EnumTricksDemo - Example 2] Enum with abstract methods");
        System.out.println("5 + 3 = " + Operation.PLUS.apply(5, 3));
        System.out.println("5 * 3 = " + Operation.TIMES.apply(5, 3));
        System.out.println("Prediction: 8, 15 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Each enum constant can override abstract methods.");
    }

    static void example3_enumSingleton() {
        System.out.println("\n[EnumTricksDemo - Example 3] Singleton pattern with enum");
        Singleton s1 = Singleton.INSTANCE;
        Singleton s2 = Singleton.INSTANCE;
        System.out.println("s1 == s2: " + (s1 == s2));
        s1.doSomething();
        System.out.println("Prediction: true, Singleton via enum ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Enum singleton is thread-safe and serialization-safe.");
    }

    static void example4_enumComparison() {
        System.out.println("\n[EnumTricksDemo - Example 4] Enum comparison");
        Size s1 = Size.SMALL;
        Size s2 = Size.SMALL;
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("s1.equals(s2): " + s1.equals(s2));
        System.out.println("Prediction: true, true ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Use == for enum comparison; enums are singletons.");
    }

    static void challenge() {
        System.out.println("\n[EnumTricksDemo - Challenge] Can you create enum instance with new?");
        System.out.println("Hidden");
        // Size s = new Size(40); // compile error
        System.out.println("Interview Hint: Enum constructors are always private.");
    }
}
