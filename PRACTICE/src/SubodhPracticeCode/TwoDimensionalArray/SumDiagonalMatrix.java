//Program to find the sum of the elements of the diagonals (primary + secondary) of the matrix 
//where if the element of the secondary diagonal is present in primary diagonal 
//then the element should not be added twice

package com.example.TwoDimensionalArray;

public class SumDiagonalMatrix {
	public static void main(String[] args) {
		int[][] matrix= {{11,22,33},{44,55,66},{77,88,99}};
		
		int sum=0;
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if(i==j) {
					sum+=matrix[i][j];
				}else if(i+j ==matrix.length-1) {
					sum+=matrix[i][j];
				}
			}
		}
		
		System.out.println(sum);
	}
}
