package com.example.programs;

import java.util.*;

//A person has many shoes of different sizes and wants to arrange them into pairs. 
//A pair consists of one left shoe (L) and one right shoe (R) of the same size. 
//Input N= 8 Shoes: 7L 7R 7L 8L 6R 7R 8R 6R Output: 3

public class ShoePairs {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of shoes:");
        int n = sc.nextInt();

        HashMap<Integer, Integer> left = new HashMap<>();
        HashMap<Integer, Integer> right = new HashMap<>();

        System.out.println("Enter shoes (like 7L 7R):");

        for(int i = 0; i < n; i++) {

            String shoe = sc.next();

            int size = Integer.parseInt(shoe.substring(0, shoe.length()-1));
            char side = shoe.charAt(shoe.length()-1);

            if(side == 'L')
                left.put(size, left.getOrDefault(size,0)+1);
            else
                right.put(size, right.getOrDefault(size,0)+1);
        }

        int pairs = 0;

        for(int size : left.keySet()) {

            int l = left.getOrDefault(size,0);
            int r = right.getOrDefault(size,0);

            pairs += Math.min(l,r);
        }

        System.out.println("Total pairs: " + pairs);
    }
}
