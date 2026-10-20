package com.example.JavaTricksShowcase;

/**
 * Static methods are hidden, not overridden; resolved at compile-time.
 */
public class StaticBindingDemo {
    static class Parent {
        static void show() { System.out.println("Parent static"); }
        void display() { System.out.println("Parent instance"); }
    }
    static class Child extends Parent {
        static void show() { System.out.println("Child static"); }
        @Override void display() { System.out.println("Child instance"); }
    }

    public static void main(String[] args) {
        example1_staticHiding();
        example2_instanceOverride();
        example3_staticViaReference();
        example4_staticVariableHiding();
        challenge();
    }

    static void example1_staticHiding() {
        System.out.println("\n[StaticBindingDemo - Example 1] Static method hiding");
        Parent p = new Child();
        p.show(); // calls Parent.show() — compile-time binding
        System.out.println("Prediction: Parent static ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Static methods are hidden, not overridden; resolved by declared type.");
    }

    static void example2_instanceOverride() {
        System.out.println("\n[StaticBindingDemo - Example 2] Instance method override");
        Parent p = new Child();
        p.display(); // calls Child.display() — runtime binding
        System.out.println("Prediction: Child instance ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Instance methods use dynamic dispatch based on actual object.");
    }

    static void example3_staticViaReference() {
        System.out.println("\n[StaticBindingDemo - Example 3] Calling static via reference");
        Child c = null;
        c.show(); // no NPE! resolved at compile-time to Child.show()
        System.out.println("Prediction: NPE? ❌  Actual: Child static ✅");
        System.out.println("Key Takeaway: Static methods don't need an instance; call via class name.");
    }

    static class Base { static int x = 10; }
    static class Derived extends Base { static int x = 20; }

    static void example4_staticVariableHiding() {
        System.out.println("\n[StaticBindingDemo - Example 4] Static variable hiding");
        Base b = new Derived();
        System.out.println("b.x = " + b.x); // 10, not 20
        System.out.println("Prediction: b.x=10 ✅  Actual: b.x=" + b.x + " ✅");
        System.out.println("Key Takeaway: Static variables are also hidden, not overridden.");
    }

    static void challenge() {
        System.out.println("\n[StaticBindingDemo - Challenge] Predict:");
        System.out.println("Hidden");
        // Parent p = new Child(); Child.show(); p.show();
        System.out.println("Interview Hint: Static binding is a classic OOP trap question.");
    }
}
