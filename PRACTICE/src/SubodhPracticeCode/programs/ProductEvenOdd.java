//Program to print the product of sum of all even digits and sum of all odd digits

package com.example.programs;
import java.util.Scanner;

public class ProductEvenOdd {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		int sumEven=0;
		int sumOdd=0;
		
		while(num!=0) {
			int digit=num%10;
			if(digit%2==0) {
				sumEven=sumEven+digit;
			}else {
				sumOdd=sumOdd+digit;
			}
			num=num/10;
		}
		
		System.out.println(sumEven*sumOdd);
		sc.close();
	}
}
