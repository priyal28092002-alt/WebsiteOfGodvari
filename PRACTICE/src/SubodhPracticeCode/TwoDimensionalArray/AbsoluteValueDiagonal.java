/*Given a square matrix, calculate the absolute difference between the sums of its diagonals. 
For example, the square matrix arr is shown below: 1 2 3 4 5 6 9 8 9 
The left-to-right diagonal = 1 + 5 + 9 = 15. The right to left diagonal = 3 + 5 + 9. 
Their absolute difference is |15 - 17| = 2 */

package com.example.TwoDimensionalArray;

public class AbsoluteValueDiagonal {
	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 9, 8, 9 } };

		int sumPri = 0;
		int sumSec = 0;
		
		//TODO: Calculating the sum of the elements of the primary diagonal
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i == j) {
					sumPri += matrix[i][j];
				}
			}
		}
		
		//TODO: Calculating the sum of the elements of secondary diagonal
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (i + j == matrix.length - 1) {
					sumSec += matrix[i][j];
				}
			}
		}

		System.out.println("The sum of the elements in primary diagonal: "+sumPri);
		System.out.println("The sum of the elements in primary diagonal: "+sumSec);
		
		/*int absValue = sumPri - sumSec;
		if (absValue < 0) {
			absValue *= -1;
		}*/
		
		int absValue=Math.abs(sumPri-sumSec);
		System.out.println("The absolute difference between the sum of the diagonals (Primary & Secondary): "+absValue);
	}
}
