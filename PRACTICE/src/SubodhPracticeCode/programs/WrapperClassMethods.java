//Program to demonstrate inbuilt methods of wrapper classes

package com.example.programs;

public class WrapperClassMethods {
	public static void main(String[] args) {
		
		// Integer wrapper class methods
		System.out.println("=== Integer Methods ===");
		Integer num = 42;
		System.out.println("parseInt: " + Integer.parseInt("123"));
		System.out.println("valueOf: " + Integer.valueOf("456"));
		System.out.println("toString: " + Integer.toString(789));
		System.out.println("compareTo: " + num.compareTo(50));
		System.out.println("MAX_VALUE: " + Integer.MAX_VALUE);
		System.out.println("MIN_VALUE: " + Integer.MIN_VALUE);
		
		// Double wrapper class methods
		System.out.println("\n=== Double Methods ===");
		Double d = 3.14;
		System.out.println("parseDouble: " + Double.parseDouble("2.5"));
		System.out.println("valueOf: " + Double.valueOf("1.8"));
		System.out.println("isNaN: " + Double.isNaN(d));
		System.out.println("isInfinite: " + Double.isInfinite(d));
		System.out.println("doubleValue: " + d.doubleValue());
		
		// Boolean wrapper class methods
		System.out.println("\n=== Boolean Methods ===");
		Boolean bool = true;
		System.out.println("parseBoolean: " + Boolean.parseBoolean("false"));
		System.out.println("valueOf: " + Boolean.valueOf("true"));
		System.out.println("toString: " + Boolean.toString(false));
		System.out.println("compareTo: " + bool.compareTo(false));
		
		// Character wrapper class methods
		System.out.println("\n=== Character Methods ===");
		Character ch = 'A';
		System.out.println("isDigit: " + Character.isDigit('5'));
		System.out.println("isLetter: " + Character.isLetter('Z'));
		System.out.println("isUpperCase: " + Character.isUpperCase(ch));
		System.out.println("toLowerCase: " + Character.toLowerCase(ch));
		System.out.println("toUpperCase: " + Character.toUpperCase('b'));
		
		// Float wrapper class methods
		System.out.println("\n=== Float Methods ===");
		Float f = 2.5f;
		System.out.println("parseFloat: " + Float.parseFloat("1.2"));
		System.out.println("valueOf: " + Float.valueOf("3.4"));
		System.out.println("floatValue: " + f.floatValue());
		System.out.println("intValue: " + f.intValue());
		
		// Long wrapper class methods
		System.out.println("\n=== Long Methods ===");
		Long lng = 1000L;
		System.out.println("parseLong: " + Long.parseLong("5000"));
		System.out.println("valueOf: " + Long.valueOf("7000"));
		System.out.println("longValue: " + lng.longValue());
		System.out.println("MAX_VALUE: " + Long.MAX_VALUE);
	}
}