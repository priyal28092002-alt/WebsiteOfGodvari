package com.example.JavaTricksShowcase;

/**
 * Overloading is compile-time; overriding is runtime dispatch.
 */
public class OverloadOverrideDemo {
    static class Animal { String who() { return "Animal"; } }
    static class Dog extends Animal { @Override String who() { return "Dog"; } }
    static class Cat extends Animal { @Override String who() { return "Cat"; } }

    // Overloads
    static void shout(Animal a) { System.out.println("shout(Animal): " + a.who()); }
    static void shout(Dog d)    { System.out.println("shout(Dog): " + d.who()); }

    public static void main(String[] args) {
        example1_overloadResolution();
        example2_runtimeOverride();
        example3_autoboxingVarargsAmbiguity();
        challenge();
    }

    static void example1_overloadResolution() {
        System.out.println("\n[OverloadOverrideDemo - Example 1] Overload chosen at compile-time");
        Animal a = new Dog();
        shout(a); // variable type is Animal → calls shout(Animal)
        System.out.println("Prediction: shout(Animal) ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Overload picked using declared type, not actual object.");
    }

    static void example2_runtimeOverride() {
        System.out.println("\n[OverloadOverrideDemo - Example 2] Override chosen at runtime");
        Animal a = new Dog();
        System.out.println(a.who()); // Dog
        a = new Cat();
        System.out.println(a.who()); // Cat
        System.out.println("Prediction: Dog then Cat ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Overriding uses dynamic dispatch based on actual object.");
    }

    static void call(int x) { System.out.println("call(int)"); }
    static void call(Integer x) { System.out.println("call(Integer)"); }
    static void call(int... x) { System.out.println("call(int...)"); }

    static void example3_autoboxingVarargsAmbiguity() {
        System.out.println("\n[OverloadOverrideDemo - Example 3] Boxing vs varargs");
        call(5);          // exact int
        call(Integer.valueOf(5)); // Integer
        call();           // varargs empty
        System.out.println("Prediction: call(int), call(Integer), call(int...) ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Overload preference: exact > boxing > varargs.");
    }

    static void challenge() {
        System.out.println("\n[OverloadOverrideDemo - Challenge] Which overload?");
        System.out.println("Hidden");
        // call((short)5); // which one? short → int (widening) beats boxing
        System.out.println("Interview Hint: Overload ranking questions are common.");
    }
}