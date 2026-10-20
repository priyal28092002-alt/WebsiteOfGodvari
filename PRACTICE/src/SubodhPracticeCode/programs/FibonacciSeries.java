//Program to print the Fibonacci Series upto nth term
package com.example.programs;

import java.util.Scanner;

public class FibonacciSeries {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the value of n: ");
        int n = sc.nextInt();

        int firstTerm = 0;
        int secondTerm = 1;

        System.out.print("Fibonacci Series up to " + n + " terms: " + firstTerm + ", " + secondTerm);

        for (int i = 2; i < n; i++) {
            int nextTerm = firstTerm + secondTerm;
            System.out.print(", " + nextTerm);
            firstTerm = secondTerm;
            secondTerm = nextTerm;
        }
        
        sc.close();
    }
}
