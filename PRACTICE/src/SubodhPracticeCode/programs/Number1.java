//Program to print numbers from 500 to 1

package com.example.programs;

public class Number1 {
    public static void print(int n) {
        if (n >= 1) {
            System.out.println(n);
            n--;
            print(n);
        }
    }

    public static void main(String[] args) {
        print(500);
    }
}
