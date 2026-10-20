//Program to find an array element that contains the digit k a maximum number of time

package com.example.arrays;

import java.util.Scanner;

public class MaximumDigitsInElement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size = sc.nextInt();
		int[] array = new int[size];

		System.out.println("Enter the array elements:");
		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}
		
		int[] frequency=new int[array.length];
		
		System.out.println("Enter the digit to count the frequency in each element:");
		int k=sc.nextInt();
		sc.close();
      
		
		for (int i = 0; i < array.length; i++) {
			int n = array[i];
			while(n!=0) {
				int digit=n%10;
				if(digit==k) {
					frequency[i]=frequency[i]+1;
				}
				n/=10;
			}
		}
		
		int large =0;
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] > frequency[large]) {
				large = i;
			}
		}
		
		System.out.println(array[large]+" The maximum frequency is i.e. "+frequency[large]);
		
	}
}
