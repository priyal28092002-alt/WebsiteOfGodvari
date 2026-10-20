package com.example.JavaTricksShowcase;

/**
 * Variable shadowing in nested scopes and inheritance.
 */
public class ShadowingDemo {
    static int x = 10; // class variable

    static class Parent {
        int value = 100;
        void show() { System.out.println("Parent value: " + value); }
    }
    static class Child extends Parent {
        int value = 200; // shadows parent's value
        void show() { System.out.println("Child value: " + value); }
        void showParent() { System.out.println("Parent value via super: " + super.value); }
    }

    public static void main(String[] args) {
        example1_localShadowsField();
        example2_parameterShadowsField();
        example3_inheritanceShadowing();
        example4_blockScopeShadowing();
        challenge();
    }

    static void example1_localShadowsField() {
        System.out.println("\n[ShadowingDemo - Example 1] Local variable shadows field");
        int x = 20; // shadows class variable
        System.out.println("x = " + x + ", ShadowingDemo.x = " + ShadowingDemo.x);
        System.out.println("Prediction: x=20, class x=10 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Local variable hides class/instance variable in same scope.");
    }

    static void example2_parameterShadowsField() {
        System.out.println("\n[ShadowingDemo - Example 2] Parameter shadows field");
        ShadowingDemo demo = new ShadowingDemo();
        demo.showValue(30);
        System.out.println("Prediction: value=30, this.value=50 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Use 'this' to access instance field when shadowed.");
    }
    
    int value = 50;
    void showValue(int value) {
        System.out.println("value = " + value + ", this.value = " + this.value);
    }

    static void example3_inheritanceShadowing() {
        System.out.println("\n[ShadowingDemo - Example 3] Field shadowing in inheritance");
        Child c = new Child();
        Parent p = c;
        System.out.println("c.value = " + c.value + ", p.value = " + p.value);
        System.out.println("Prediction: c.value=200, p.value=100 ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Fields are not polymorphic; accessed by reference type.");
    }

    static void example4_blockScopeShadowing() {
        System.out.println("\n[ShadowingDemo - Example 4] Block scope shadowing");
        int a = 1;
        System.out.println("Outer a = " + a);
        // { int a = 2; } // compile error — can't shadow in nested block
        System.out.println("Prediction: Compile error if uncommented ✅  Actual: Java prevents nested shadowing ✅");
        System.out.println("Key Takeaway: Java doesn't allow variable shadowing in nested blocks (unlike C++).");
    }

    static void challenge() {
        System.out.println("\n[ShadowingDemo - Challenge] What prints?");
        System.out.println("Hidden");
        // Child c = new Child(); c.show(); c.showParent();
        System.out.println("Interview Hint: Field shadowing vs method overriding confusion is common.");
    }
}
