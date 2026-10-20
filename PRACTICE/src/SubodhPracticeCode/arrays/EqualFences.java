package com.example.arrays;

import java.util.*;

//You are given an array rods[] representing the lengths of rods. You want to build two fences of equal total length. 
//Rules: 
//1. You can combine multiple rods to form a fence. 
//2. Each rod can be used at most once. 
//3. You cannot break rods. 
//4. Some rods may remain unused. 
//Your task is to determine the maximum possible height of the two equal fences. If it is not possible to build two fences of equal height, return 0. 
//Input: rods = [1,2,3,4,5,6] Output: 10

public class EqualFences {

    static int maxHeight = 0;

    public static void solve(int[] rods, int index, int sum1, int sum2) {

        if(index == rods.length) {
            if(sum1 == sum2)
                maxHeight = Math.max(maxHeight, sum1);
            return;
        }

        // Option 1: put rod in fence1
        solve(rods, index + 1, sum1 + rods[index], sum2);

        // Option 2: put rod in fence2
        solve(rods, index + 1, sum1, sum2 + rods[index]);

        // Option 3: ignore rod
        solve(rods, index + 1, sum1, sum2);
    }

    public static void main(String[] args) {

        int[] rods = {1,2,3,4,5,6};

        solve(rods, 0, 0, 0);

        System.out.println("Maximum Fence Height: " + maxHeight);
    }
}
