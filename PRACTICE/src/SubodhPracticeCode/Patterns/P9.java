package com.example.Patterns;

import java.util.Scanner;

public class P9 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the no of rows:");
		int n=sc.nextInt();
		
		for(int r=1; r<=n; r++) {
			for(int c=1; c<=r; c++) //cols
			{
				System.out.print("* ");
			}
			System.out.println();
		}
		sc.close();
	}
}
