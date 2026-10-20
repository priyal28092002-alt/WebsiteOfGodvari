package com.example.ArraySorting;

//Java program to sort an array of integers using Quick Sort algorithm.

public class QuickSort {
	public static void main(String[] args) {
		int[] arr = { 9, 14, 3, 2, 43, 11, 58, 22 };
		QuickSort obj = new QuickSort();
		obj.quickSort(arr, 0, arr.length - 1);
		System.out.println("Sorted Array");
		obj.printArray(arr);
	}
	
	void quickSort(int[] arr, int low, int high) {
		if (low < high) {
			int pi = partition(arr, low, high);
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
		}
	}
	
	int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (arr[j] < pivot) {
				i++;
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		int temp = arr[i + 1];
		arr[i + 1] = arr[high];
		arr[high] = temp;
		return i + 1;
	}
	
	void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
}
