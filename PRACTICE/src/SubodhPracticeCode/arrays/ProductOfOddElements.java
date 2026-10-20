//Program to find the product of odd elements

package com.example.arrays;

import java.util.Scanner;

public class ProductOfOddElements {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the array size: ");
		int size=sc.nextInt();
		
		int[] array=new int[size];
		
		System.out.println("Enter the array elements: ");
		for(int i=0; i<array.length; i++) {
			array[i]=sc.nextInt();
		}
		
		int product=1;
		for(int i=0; i<array.length; i++) {
			if(array[i]%2!=0) {
				product=product*array[i];
			}
		}
		
		System.out.println("The product of odd elemnts is even: "+product);
		
		sc.close();
	}
}
