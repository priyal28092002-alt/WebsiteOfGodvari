//Program to count the number of prime numbers less than n

package com.example.programs;

import java.util.Scanner;

public class PrimeRange {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int primecount = 0;

		for (int i = 2; i < n; i++) {
			int factorscount = 0;
			for (int j = 1; j <= i; j++) {
				if (i % j == 0) {
					factorscount++;
				}
			}
			if (factorscount == 2) {
				primecount++;
			}
		}
		System.out.println(primecount);
		sc.close();
	}
}
