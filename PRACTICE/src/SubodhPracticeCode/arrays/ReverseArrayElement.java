//Program to reverse each element of the array

/*Input - 
5 ===> Size of the array
85 34 61 27 96 ===> values of array

Output - 
58 43 16 72 69*/

package com.example.arrays;

import java.util.Scanner;

public class ReverseArrayElement {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the size of array:");
		int size = sc.nextInt();
		int[] array = new int[size];

		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}

		for (int i = 0; i < array.length; i++) {
			int reverse = 0;
			while (array[i] != 0) {
				int digit = array[i] % 10;
				reverse = reverse * 10 + digit;
				array[i] /= 10;
			}
			array[i] = reverse;
		}

		System.out.print("The array with each element revesed:");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}

		sc.close();
	}
}
