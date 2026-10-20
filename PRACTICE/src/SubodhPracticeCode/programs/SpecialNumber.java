//Program to check if the number is a special number or not
//A number is said to be a special number if the addition of sum of all the digits 
//and product of all digits is equal to the number

package com.example.programs;

import java.util.Scanner;

public class SpecialNumber {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number.");
		int num=sc.nextInt();
		int temp=num;
		int sum=0;
		int product=1;
		
		while(num!=0) {
			int digit=num%10;
			sum=sum+digit;
			product=product*digit;
			num=num/10;
		}
		
		//After separating the numbers the end number is 0
		if((sum+product)==temp) {
			System.out.println("Its a special number");
		}else {
			System.out.println("Its not a special number");
		}
		sc.close();
	}
}
