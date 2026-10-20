package com.example.ArraySorting;

import java.util.*;

/*You are given n pairs. Sort them:

1. By first element (ascending)

2. If first elements are equal > sort by second element (ascending)

Input:

3
36, 24, 78

Output:

24, 36,78*/

public class SortPairs {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of pairs:");
        int n = sc.nextInt();

        int[][] arr = new int[n][2];

        System.out.println("Enter pairs:");

        for(int i = 0; i < n; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
        }

        // Sorting logic
        Arrays.sort(arr, (a, b) -> {
            if(a[0] != b[0])
                return a[0] - b[0];   // sort by first element
            else
                return a[1] - b[1];   // sort by second element
        });

        System.out.println("Sorted Pairs:");

        for(int i = 0; i < n; i++) {
            System.out.println(arr[i][0] + " " + arr[i][1]);
        }
    }
}
