package com.example.arrays;

import java.util.*;

//Problem: Print Missing Ranges
//You are given:
//An integer L representing the lower limit
//An integer R representing the upper limit
//An integer N representing the size of the array
//N space-separated integers representing elements inside the range
//Your task is to print all ranges between L and R that do NOT contain the given array elements.

public class MissingRanges {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter L:");
        int L = sc.nextInt();

        System.out.println("Enter R:");
        int R = sc.nextInt();

        System.out.println("Enter N:");
        int N = sc.nextInt();

        int[] arr = new int[N];

        System.out.println("Enter elements:");

        for(int i = 0; i < N; i++)
            arr[i] = sc.nextInt();

        Arrays.sort(arr);

        int start = L;

        for(int i = 0; i < N; i++) {

            if(arr[i] > start) {

                if(start == arr[i] - 1)
                    System.out.println(start);
                else
                    System.out.println(start + "-" + (arr[i] - 1));
            }

            start = arr[i] + 1;
        }

        if(start <= R) {

            if(start == R)
                System.out.println(start);
            else
                System.out.println(start + "-" + R);
        }
    }
}
