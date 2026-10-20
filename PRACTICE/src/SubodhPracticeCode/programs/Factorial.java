//Program to print the factorial of a number

package com.example.programs;
import java.util.Scanner;

public class Factorial {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter the number");
		int num=sc.nextInt();
		int fact=1;
		for(int i=2;i<=num;i++) {
			fact=fact*i;
		}
		System.out.println(fact);
		
		sc.close();
	}
}
