package com.example.Patterns;

import java.util.Scanner;

public class P8 {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the no of rows:");
		int nR=sc.nextInt();
		int nC=nR*2-1;
		
		for(int r=1; r<=nR; r++) {
			for(int c=1; c<=nC; c++) //cols
			{
				if(r==nR || r+c==nR+1 || c-r==nR-1) {
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
