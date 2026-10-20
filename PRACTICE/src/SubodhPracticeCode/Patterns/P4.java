package com.example.Patterns;

import java.util.Scanner;

public class P4 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter no of rows and cols:");
		int n = sc.nextInt();

		for (int r = 1; r <= n; r++) // rows
		{
			for (int c = 1; c <= n; c++) // cols
			{
				if (r == c || r + c == n + 1) {
					System.out.print("* ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		sc.close();
	}
}
