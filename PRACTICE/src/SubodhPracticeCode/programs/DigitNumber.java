//Program to print the digits of a number

package com.example.programs;
import java.util.Scanner;

public class DigitNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		
		while(num!=0) {
			int digit=num%10;
			System.out.println(digit);
			num=num/10;
		}
		
		sc.close();
	}
}
