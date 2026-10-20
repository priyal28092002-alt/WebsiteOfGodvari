//Program to convert decimal into binary(base 2)

package com.example.programs;

import java.util.Scanner;

public class DecimalToBinary {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the decimal number");
		int n=sc.nextInt();
		String binary="";
		
		while(n!=0) {
			int remainder=n%2;
			binary=remainder+binary;
			n=n/2;
		}
		
		System.out.println("Binary of "+n+" is "+binary);
		sc.close();
	}
}
