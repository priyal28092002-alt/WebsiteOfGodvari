package com.example.JavaTricksShowcase;

/**
 * Integer cache, equals vs ==, and NPE on unboxing.
 */
public class BoxingCachingDemo {
    public static void main(String[] args) {
        example1_integerCache();
        example2_equalsVsDoubleEquals();
        example3_nullUnboxing();
        example4_overflowBoxing();
        challenge();
    }

    static void example1_integerCache() {
        System.out.println("\n[BoxingCachingDemo - Example 1] Integer cache [-128,127]");
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println("a==b: " + (a==b) + ", c==d: " + (c==d));
        System.out.println("Prediction: true, false ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: Small integers are cached; bigger ones are distinct objects.");
    }

    static void example2_equalsVsDoubleEquals() {
        System.out.println("\n[BoxingCachingDemo - Example 2] equals vs ==");
        Integer x = 1000, y = 1000;
        System.out.println("x==y: " + (x==y) + ", x.equals(y): " + x.equals(y));
        System.out.println("Prediction: false, true ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: '==' compares references for objects; equals compares values.");
    }

    static void example3_nullUnboxing() {
        System.out.println("\n[BoxingCachingDemo - Example 3] NullPointerException on unboxing");
        Integer z = null;
        try {
            int n = z; // unboxing null → NPE
            System.out.println(n); // won't reach
        } catch (NullPointerException e) {
            System.out.println("Caught NPE while unboxing null");
        }
        System.out.println("Prediction: Caught NPE ✅  Actual: Caught NPE ✅");
        System.out.println("Key Takeaway: Guard against null before unboxing.");
    }

    static void example4_overflowBoxing() {
        System.out.println("\n[BoxingCachingDemo - Example 4] Autoboxing after overflow");
        int a = Integer.MAX_VALUE;
        int b = a + 1; // overflow to negative
        Integer c = b; // boxes the overflowed value
        System.out.println("b=" + b + ", c.equals(b): " + c.equals(b));
        System.out.println("Prediction: b is negative, equals true ✅  Actual: as above ✅");
        System.out.println("Key Takeaway: Overflow happens before boxing; boxed value reflects overflowed int.");
    }

    static void challenge() {
        System.out.println("\n[BoxingCachingDemo - Challenge] Predict:");
        Integer i1 = 127, i2 = 127, i3 = 128, i4 = 128;
        System.out.println("Hidden");
        // System.out.println((i1==i2) + ", " + (i3==i4));
        System.out.println("Interview Hint: Integer cache range is a favorite.");
    }
}