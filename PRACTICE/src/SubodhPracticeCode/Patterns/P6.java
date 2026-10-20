package com.example.Patterns;

import java.util.Scanner;

public class P6 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the no of rows and cols:");
		int n=sc.nextInt();
		
		for(int r=1; r<=n; r++) {
			for(int c=1; c<=n; c++) //cols
			{
				if(r==n/2+1 || r==n || ((c==1 || c==n)&& r>=n/2+1) || (c==n/2+1 && r<=n/2+1)) {
					System.out.print("* ");
				}else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		sc.close();
	}
}
