//Program to demonstrate various functionalities of Utility classes

package com.example.programs;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class UtilityClassesDemo {
	public static void main(String[] args) {
		
		// BigInteger utility class
		System.out.println("=== BigInteger Utility Class ===");
		BigInteger big1 = new BigInteger("123456789012345678901234567890");
		BigInteger big2 = BigInteger.valueOf(100);
		System.out.println("Addition: " + big1.add(big2));
		System.out.println("Multiplication: " + big2.multiply(BigInteger.TEN));
		System.out.println("Power: " + BigInteger.valueOf(2).pow(10));
		
		// BigDecimal utility class
		System.out.println("\n=== BigDecimal Utility Class ===");
		BigDecimal dec1 = new BigDecimal("10.25");
		BigDecimal dec2 = new BigDecimal("3.33");
		System.out.println("Addition: " + dec1.add(dec2));
		System.out.println("Division: " + dec1.divide(dec2, 2, RoundingMode.HALF_UP));
		System.out.println("Scale: " + dec1.scale());
		
		// Math utility class
		System.out.println("\n=== Math Utility Class ===");
		System.out.println("Square root: " + Math.sqrt(16));
		System.out.println("Power: " + Math.pow(2, 3));
		System.out.println("Max: " + Math.max(10, 20));
		System.out.println("Random: " + Math.random());
		System.out.println("Absolute: " + Math.abs(-15));
		
		// Arrays utility class
		System.out.println("\n=== Arrays Utility Class ===");
		int[] arr = {5, 2, 8, 1, 9};
		System.out.println("Original: " + Arrays.toString(arr));
		Arrays.sort(arr);
		System.out.println("Sorted: " + Arrays.toString(arr));
		System.out.println("Binary search: " + Arrays.binarySearch(arr, 8));
		int[] copy = Arrays.copyOf(arr, 3);
		System.out.println("Copy: " + Arrays.toString(copy));
		
		// Collections utility class
		System.out.println("\n=== Collections Utility Class ===");
		List<String> list = new ArrayList<>();
		list.add("banana");
		list.add("apple");
		list.add("cherry");
		System.out.println("Original: " + list);
		Collections.sort(list);
		System.out.println("Sorted: " + list);
		Collections.reverse(list);
		System.out.println("Reversed: " + list);
		System.out.println("Max: " + Collections.max(list));
		System.out.println("Min: " + Collections.min(list));
		
		// String utility methods
		System.out.println("\n=== String Utility Methods ===");
		String str = "Hello World";
		System.out.println("Length: " + str.length());
		System.out.println("Uppercase: " + str.toUpperCase());
		System.out.println("Substring: " + str.substring(0, 5));
		System.out.println("Contains: " + str.contains("World"));
		System.out.println("Replace: " + str.replace("World", "Java"));
	}
}