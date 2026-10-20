//Program to find the matrix multiplication

package com.example.TwoDimensionalArray;

import java.util.Scanner;

public class MatrixMultiplication {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		// Input dimensions of the first matrix
		System.out.print("Enter the no. of rows for the Matrix 1: ");
		int row1=sc.nextInt();
		System.out.print("Enter the no. of columns for the Matrix 1:");
		int col1=sc.nextInt();
		
		// Input dimensions of the second matrix
		System.out.print("Enter the no. of rows for the Matrix 2: ");
		int row2=sc.nextInt();
		System.out.print("Enter the no. of columns for the Matrix 2:");
		int col2=sc.nextInt();
		
		// Check if matrix multiplication is possible
		//To perform multiplication of two matrices, we should make sure that the 
		//number of columns in the 1st matrix is equal to the rows in the 2nd matrix. 
		//row1 * col1 = row2 * col2
		//col1 == row2
		if(col1 != row2) {
			System.out.println("Matrix multiplication is not possible.");
			System.out.println("Columns of the first matrix must be equal to the rows of the second matrix.");
		}else {
			int[][] matrix1=inputMatrix(row1, col1, "Second");
			
			int[][] matrix2=inputMatrix(row2, col2, "Second");
			
			int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);
			
			System.out.println("Resultant matrix after multiplication:");
            displayMatrix(resultMatrix);
		}
		sc.close();
	}
	
	// Method to input elements for a matrix
	private static int[][] inputMatrix(int rows, int cols, String ordinal) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter elements for the " + ordinal + " matrix:");

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Enter element at position (" + (i + 1) + ", " + (j + 1) + "): ");
                matrix[i][j] = sc.nextInt();
            }
        }
        sc.close();
        
        return matrix;
    }
	
	// Method to multiply two matrices
	public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
		int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        //int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;
        
        //The resulting matrix product will have a number of rows of the 1st matrix 
        //and a number of columns of the 2nd matrix.
        //row1 * col1 = row2 * col2
        int[][] resultMatrix = new int[rows1][cols2];
        
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return resultMatrix;
		
	}
	
	// Method to display a matrix
	private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}
