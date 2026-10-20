package com.example.programs;

import java.util.*;

// Question
/*You are given N bank transactions.
Each transaction has:
Sender Receiver Amount Timestamp

A transaction is considered fraud if:

. Sender, Receiver, and Amount are same

. Timestamp difference ≤ 60 seconds

Find and print all such fraud transactions.*/

public class FraudDetectionSimple {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of transactions:");
        int n = sc.nextInt();

        HashMap<String, Integer> map = new HashMap<>();

        System.out.println("Enter transactions (Sender Receiver Amount Timestamp):");

        for(int i = 0; i < n; i++) {

            String sender = sc.next();
            String receiver = sc.next();
            int amount = sc.nextInt();
            int time = sc.nextInt();

            String key = sender + "-" + receiver + "-" + amount;

            if(map.containsKey(key)) {

                int prevTime = map.get(key);

                if(time - prevTime <= 60) {
                    System.out.println("Fraud: " + key + " at time " + time);
                }
            }

            // update latest time
            map.put(key, time);
        }
    }
}