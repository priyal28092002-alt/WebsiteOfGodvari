package com.example.String;

import java.util.*;

//Given a list of strings representing different words, remove duplicates and print the unique words in ascending and descending order. 
//Input should be taken from the user. Example Input: Watermelon, Apple, Orange, Banana, Orange, Lemon, Apple

public class UniqueWords {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter words separated by comma:");
        String input = sc.nextLine();

        String[] words = input.split(",");

        // TreeSet removes duplicates and sorts automatically
        TreeSet<String> set = new TreeSet<>();

        for(String word : words) {
            set.add(word.trim());
        }

        // Ascending order
        System.out.println("\nUnique words in Ascending Order:");
        for(String word : set) {
            System.out.println(word);
        }

        // Descending order
        System.out.println("\nUnique words in Descending Order:");
        for(String word : set.descendingSet()) {
            System.out.println(word);
        }
    }
}
