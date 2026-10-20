package com.example.JavaTricksShowcase;

/**
 * Shows pitfalls comparing objects with '==' instead of equals.
 */
public class EqualityPitfallsDemo {
    public static void main(String[] args) {
        example1_stringEquality();
        example2_wrapperEquality();
        example3_customEquals();
        challenge();
    }

    static void example1_stringEquality() {
        System.out.println("\n[EqualityPitfallsDemo - Example 1] String == vs equals");
        String a = "hi";
        String b = new String("hi");
        System.out.println(a == b);        // false
        System.out.println(a.equals(b));   // true
        System.out.println("Prediction: false, true ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Use equals for content equality.");
    }

    static void example2_wrapperEquality() {
        System.out.println("\n[EqualityPitfallsDemo - Example 2] Wrapper == vs equals");
        Integer x = 1000, y = 1000;
        System.out.println(x == y);       // false
        System.out.println(x.equals(y));  // true
        System.out.println("Prediction: false, true ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Wrapper '==' compares references; equals compares numeric value.");
    }

    static class Point {
        final int x, y;
        Point(int x, int y){ this.x=x; this.y=y; }
        @Override public boolean equals(Object o){
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o; return x==p.x && y==p.y;
        }
        @Override public int hashCode(){ return 31*x + y; }
    }

    static void example3_customEquals() {
        System.out.println("\n[EqualityPitfallsDemo - Example 3] equals/hashCode contract");
        Point p1 = new Point(1,2), p2 = new Point(1,2);
        System.out.println(p1.equals(p2) + ", hash equal? " + (p1.hashCode()==p2.hashCode()));
        System.out.println("Prediction: true, true ✅  Actual: as printed ✅");
        System.out.println("Key Takeaway: Equal objects should have equal hashCodes.");
    }

    static void challenge() {
        System.out.println("\n[EqualityPitfallsDemo - Challenge] Predict:");
        String s1 = "a" + "b";
        String s2 = "ab";
        System.out.println("Hidden");
        // System.out.println(s1==s2);
        System.out.println("Interview Hint: Compile-time constants fold into the pool.");
    }
}