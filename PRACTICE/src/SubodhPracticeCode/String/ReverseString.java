//Program to reverse the String

package com.example.String;

import java.util.Scanner;

public class ReverseString {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the String: ");
		String str = sc.nextLine();

		String reverse = "";

		for (int i = str.length() - 1; i >= 0; i--) {
			reverse = reverse + str.charAt(i);
		}

		System.out.println(reverse);

		sc.close();
	}
}
