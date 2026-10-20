//Array Operation: Total Number of decrement operations
/*You are given an array A of size N and an integer K.
You have to make all the elements of the array divisible
by K.
You can decrease any element of the array by 1 in one
operation.

Find the total number of decrement operations required
to make all the elements of the array divisible by K*/

package com.example.arrays;

import java.util.Scanner;

public class TotalNDecrementOperation {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the array size: ");
		int size=sc.nextInt();
		System.out.print("Enter the integer k: ");
		int k=sc.nextInt();
		
		int[] array=new int[size];
		
		System.out.println("Enter the array elements: ");
		for (int i = 0; i < array.length; i++) {
			array[i]=sc.nextInt();
		}
		
		int subSum=0;
		
		for (int i = 0; i < array.length; i++) {
			if(array[i]%k!=0) {
				subSum+=(array[i]%k);
			}
		}
		
		System.out.println(subSum);
		
		sc.close();
	}
}
