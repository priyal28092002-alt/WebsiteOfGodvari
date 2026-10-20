package com.example.JavaTricksShowcase;

/**
 * Shows class loading order, static/instance initializers, and final fields.
 */
public class InitializationOrderDemo {
    static class Parent {
        static { System.out.println("Parent: static block"); }
        { System.out.println("Parent: instance block"); }
        Parent() { System.out.println("Parent: constructor"); }
    }
    static class Child extends Parent {
        static { System.out.println("Child: static block"); }
        { System.out.println("Child: instance block"); }
        final int x;
        Child() { this.x = 42; System.out.println("Child: constructor, x=" + x); }
    }

    // Helper to demonstrate explicit static/instance initialization order
    static class OrderProbe {
        private static int mark(String label, int v) { System.out.println("OrderProbe: static init " + label); return v; }
        private static int imark(String label, int v) { System.out.println("OrderProbe: instance init " + label); return v; }
        static int S1 = mark("S1", 1);
        static { System.out.println("OrderProbe: static block A"); }
        static int S2 = mark("S2", 2);
        int I1 = imark("I1", 10);
        { System.out.println("OrderProbe: instance block A"); }
        int I2 = imark("I2", 20);
        OrderProbe() { System.out.println("OrderProbe: constructor"); }
    }

    public static void main(String[] args) {
        System.out.println("\n[InitializationOrderDemo] Creating first Child()");
        new Child();
        System.out.println("\n[InitializationOrderDemo] Creating second Child()");
        new Child();
        System.out.println("Prediction: Static blocks once, instance+ctor each time ✅  Actual: See order above ✅");
        System.out.println("Key Takeaway: Static blocks run once at class load; instance blocks before constructors.");

        example1_staticInitOrder();
        example2_instanceInitOrder();

        challenge();
    }

    static void example1_staticInitOrder() {
        System.out.println("\n[InitializationOrderDemo - Example 1] Static fields and blocks order");
        // First active use of OrderProbe triggers static initialization only once
        int v = OrderProbe.S1; // triggers: S1 init → static block A → S2 init (as per declaration order)
        System.out.println("Accessed S1, value=" + v);
        System.out.println("Prediction: S1 init, static block A, S2 init (in this order) ✅  Actual: see prints ✅");
        System.out.println("Key Takeaway: Class initialization follows declaration order for static fields/blocks.");
    }

    static void example2_instanceInitOrder() {
        System.out.println("\n[InitializationOrderDemo - Example 2] Instance fields and blocks order");
        // Creating an instance triggers: instance field initializers → instance blocks → constructor
        new OrderProbe();
        System.out.println("Prediction: instance init I1, instance block A, instance init I2, then constructor ✅  Actual: see prints ✅");
        System.out.println("Key Takeaway: For each construction: instance fields/blocks run before the constructor.");
    }

    static void challenge() {
        System.out.println("\n[InitializationOrderDemo - Challenge] Predict output order (Hidden)");
        // Hint: Parent static → Child static → Parent instance → Parent ctor → Child instance → Child ctor
        System.out.println("Interview Hint: Often asked to check understanding of order.");
    }
}