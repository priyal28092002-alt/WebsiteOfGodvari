//Sorting -> Selection Sort in ascending order

package com.example.ArraySorting;

import java.util.Scanner;

public class SelectionSortASC {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the array size:");
		int size=sc.nextInt();
		int[] array=new int[size];
		
		System.out.println("Enter the array elements:");
		for(int i=0; i<array.length; i++ ) {
			array[i]=sc.nextInt();
		}
		sc.close();
		
		System.out.println("Original array before sorting: ");
		printarray(array);
		
		for(int i=0; i<array.length; i++) {
			
			int min=i; //Assuming that the ith element is the smallest element
			for(int j=i+1; j<array.length; j++) {
				if(array[j]<array[min]) {
					min=j;
				}
			}
			int temp=array[i];
			array[i]=array[min];
			array[min]=temp;
		}
		
		System.out.println("Array after sorting the elemnts (Selection Sort):");
		printarray(array);
	}
	
	public static void printarray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

}
