//Searching -> Linear Search (Sequential Search)

//Program to print the index of the element, if present in the array or else print -1

package com.example.searching;

import java.util.Scanner;

public class LinearSearch {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size = sc.nextInt();
		int[] array = new int[size];

		System.out.println("Enter the array elements:");
		for (int i = 0; i < array.length; i++) {
			array[i] = sc.nextInt();
		}

		System.out.print("Enter the element that you want to search: ");
		int element = sc.nextInt();
		sc.close();

		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if (element == array[i]) {
				index = i;
				break;
			}
		}
		System.out.println(index);
	}
}
