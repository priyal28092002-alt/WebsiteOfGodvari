//Program to print the first and last digit of a number

package com.example.programs;

import java.util.Scanner;

public class FirstAndLastDigit {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number");
		int n=sc.nextInt();
		
		int totaldigits=(int)Math.log10(n); //totaldigits=log10(n)+1
		int firstdigit=(int)(n/Math.pow(10, totaldigits)); //firstdigit=(n/10^totaldigits-1)
		
		System.out.println("First Digit: "+firstdigit);
		System.out.println("Last Digit: "+(n%10));
		
		sc.close();
	}
}
