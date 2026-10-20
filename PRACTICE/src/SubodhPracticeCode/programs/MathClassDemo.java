package com.example.programs;

public class MathClassDemo {
    public static void main(String[] args) {

        System.out.println("=== Mathematical Constants ===");
        System.out.println("PI: " + Math.PI);
        System.out.println("E (Euler's number): " + Math.E);

        System.out.println("\n=== Basic Arithmetic Operations ===");
        System.out.println("abs(-25): " + Math.abs(-25));
        System.out.println("abs(-3.14): " + Math.abs(-3.14));
        System.out.println("max(10, 20): " + Math.max(10, 20));
        System.out.println("min(10, 20): " + Math.min(10, 20));
        System.out.println("negateExact(5): " + Math.negateExact(5));

        System.out.println("\n=== Exact Arithmetic Methods ===");
        System.out.println("addExact(100, 200): " + Math.addExact(100, 200));
        System.out.println("subtractExact(500, 200): " + Math.subtractExact(500, 200));
        System.out.println("multiplyExact(10, 5): " + Math.multiplyExact(10, 5));
        System.out.println("incrementExact(99): " + Math.incrementExact(99));
        System.out.println("decrementExact(100): " + Math.decrementExact(100));

        System.out.println("\n=== Power and Root Functions ===");
        System.out.println("pow(2, 10): " + Math.pow(2, 10));
        System.out.println("sqrt(144): " + Math.sqrt(144));
        System.out.println("cbrt(27): " + Math.cbrt(27));
        System.out.println("hypot(3, 4): " + Math.hypot(3, 4));
        System.out.println("exp(1): " + Math.exp(1));
        System.out.println("expm1(0): " + Math.expm1(0));
        System.out.println("scalb(2, 3): " + Math.scalb(2, 3));

        System.out.println("\n=== Logarithmic Functions ===");
        System.out.println("log(Math.E): " + Math.log(Math.E));
        System.out.println("log10(1000): " + Math.log10(1000));
        System.out.println("log1p(0): " + Math.log1p(0));
        System.out.println("log(100)/log(10): " + (Math.log(100) / Math.log(10)));

        System.out.println("\n=== Rounding Functions ===");
        System.out.println("ceil(4.3): " + Math.ceil(4.3));
        System.out.println("ceil(-4.3): " + Math.ceil(-4.3));
        System.out.println("floor(4.7): " + Math.floor(4.7));
        System.out.println("floor(-4.7): " + Math.floor(-4.7));
        System.out.println("round(4.5): " + Math.round(4.5));
        System.out.println("round(4.4): " + Math.round(4.4));
        System.out.println("round(-4.5): " + Math.round(-4.5));
        System.out.println("rint(4.5): " + Math.rint(4.5));
        System.out.println("rint(5.5): " + Math.rint(5.5));

        System.out.println("\n=== Division and Modulus ===");
        System.out.println("floorDiv(7, 3): " + Math.floorDiv(7, 3));
        System.out.println("floorDiv(-7, 3): " + Math.floorDiv(-7, 3));
        System.out.println("floorMod(7, 3): " + Math.floorMod(7, 3));
        System.out.println("floorMod(-7, 3): " + Math.floorMod(-7, 3));
        System.out.println("IEEEremainder(10, 3): " + Math.IEEEremainder(10, 3));

        System.out.println("\n=== Trigonometric Functions ===");
        double angleRadians = Math.PI / 4;
        System.out.println("sin(PI/4): " + Math.sin(angleRadians));
        System.out.println("cos(PI/4): " + Math.cos(angleRadians));
        System.out.println("tan(PI/4): " + Math.tan(angleRadians));
        System.out.println("sin(90 degrees): " + Math.sin(Math.toRadians(90)));
        System.out.println("cos(0 degrees): " + Math.cos(Math.toRadians(0)));

        System.out.println("\n=== Inverse Trigonometric Functions ===");
        System.out.println("asin(1): " + Math.asin(1));
        System.out.println("acos(0): " + Math.acos(0));
        System.out.println("atan(1): " + Math.atan(1));
        System.out.println("atan2(1, 1): " + Math.atan2(1, 1));

        System.out.println("\n=== Hyperbolic Functions ===");
        System.out.println("sinh(1): " + Math.sinh(1));
        System.out.println("cosh(0): " + Math.cosh(0));
        System.out.println("tanh(1): " + Math.tanh(1));

        System.out.println("\n=== Angle Conversion ===");
        System.out.println("toRadians(180): " + Math.toRadians(180));
        System.out.println("toDegrees(PI): " + Math.toDegrees(Math.PI));
        System.out.println("toDegrees(PI/2): " + Math.toDegrees(Math.PI / 2));

        System.out.println("\n=== Sign Functions ===");
        System.out.println("signum(100): " + Math.signum(100));
        System.out.println("signum(-100): " + Math.signum(-100));
        System.out.println("signum(0): " + Math.signum(0));
        System.out.println("copySign(5, -1): " + Math.copySign(5, -1));
        System.out.println("copySign(-5, 1): " + Math.copySign(-5, 1));

        System.out.println("\n=== Floating Point Operations ===");
        System.out.println("getExponent(8.0): " + Math.getExponent(8.0));
        System.out.println("ulp(1.0): " + Math.ulp(1.0));
        System.out.println("nextUp(1.0): " + Math.nextUp(1.0));
        System.out.println("nextDown(1.0): " + Math.nextDown(1.0));
        System.out.println("nextAfter(1.0, 2.0): " + Math.nextAfter(1.0, 2.0));

        System.out.println("\n=== Fused Multiply-Add ===");
        System.out.println("fma(2, 3, 4): " + Math.fma(2, 3, 4));

        System.out.println("\n=== Random Number Generation ===");
        System.out.println("random(): " + Math.random());
        System.out.println("random(): " + Math.random());
        int randomInRange = (int) (Math.random() * 100) + 1;
        System.out.println("Random (1-100): " + randomInRange);

        System.out.println("\n=== Practical Examples ===");

        double x1 = 0, y1 = 0, x2 = 3, y2 = 4;
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        System.out.println("Distance between (0,0) and (3,4): " + distance);

        double radius = 5;
        double area = Math.PI * Math.pow(radius, 2);
        System.out.println("Area of circle (r=5): " + area);

        double circumference = 2 * Math.PI * radius;
        System.out.println("Circumference of circle (r=5): " + circumference);

        double principal = 1000, rate = 0.05;
        int years = 10;
        double compoundInterest = principal * Math.pow(1 + rate, years);
        System.out.println("Compound Interest (1000, 5%, 10 years): " + compoundInterest);

        int a = 48, b = 18;
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        System.out.println("GCD of 48 and 18: " + a);
    }
}

