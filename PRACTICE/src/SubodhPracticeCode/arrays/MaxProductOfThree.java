package com.example.arrays;

import java.util.*;

//Find the maximum product of any three numbers from an array that may contain negative values. 
//Input n = 5 ARR = [3, -2, -8, 4, 1] Output 64

public class MaxProductOfThree {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter size of array:");
        int n = sc.nextInt();

        int[] arr = new int[n];

        System.out.println("Enter array elements:");
        for(int i = 0; i < n; i++)
            arr[i] = sc.nextInt();

        Arrays.sort(arr);

        int product1 = arr[n-1] * arr[n-2] * arr[n-3];
        int product2 = arr[0] * arr[1] * arr[n-1];

        int result = Math.max(product1, product2);

        System.out.println("Maximum product of three numbers: " + result);
    }
}