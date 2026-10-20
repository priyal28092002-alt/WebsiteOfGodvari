//Program to print all the elemets present in the 2D array

package com.example.TwoDimensionalArray;

public class TwoDimensionalArray {
	public static void main(String[] args) {
		int[][] array= {{1,2,3},{2,3,5,7},{10,20,30}};
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.println(array[i][j]);
			}
		}
	}
}
