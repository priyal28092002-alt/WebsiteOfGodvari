//Program to check if a number is a ArmStrong number or not
//An Armstrong number (also known as a narcissistic number) is a number that is equal to the sum of its own digits each raised to the power of the number of digits. 
//For example, 153 is an Armstrong number because 1^3 + 5^3 + 3^3 = 153.

package com.example.programs;

import java.util.Scanner;

public class ArmStrongNumber {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the number: ");
		int n = sc.nextInt();
		int temp = n;
		int sum = 0;
		int countofdigits = digitCount(n);

		while (n != 0) {
			int digit = n % 10;
			// sum=(int) (sum+Math.pow(digit,countofdigits));
			sum = sum + power(digit, countofdigits);
			n = n / 10;
		}

		if (sum == temp) {
			System.out.println("The number is a ArmStrong number");
		} else {
			System.out.println("The number is not a ArmStrong number");
		}

		sc.close();
	}

	// Method to calculate the power of the digit raised to the number of digits
	public static int power(int digit, int countofdigits) {
		int product = 1;

		for (int i = 1; i <= countofdigits; i++) {
			product = product * digit;
		}
		return product;
	}

	// Method to count the number of digits present in the number
	public static int digitCount(int n) {
		int count = 0;

		while (n != 0) {
			count++;
			n = n / 10;
		}
		return count;
	}
}
