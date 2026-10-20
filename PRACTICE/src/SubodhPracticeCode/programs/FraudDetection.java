package com.example.programs;

import java.util.*;

//Question
/*You are given N bank transactions.
Each transaction has:
Sender Receiver Amount Timestamp

A transaction is considered fraud if:

. Sender, Receiver, and Amount are same

. Timestamp difference ≤ 60 seconds

Find and print all such fraud transactions.*/

class Transaction {
    String sender;
    String receiver;
    int amount;
    int time;

    Transaction(String s, String r, int a, int t) {
        sender = s;
        receiver = r;
        amount = a;
        time = t;
    }
}

public class FraudDetection {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of transactions:");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        List<Transaction> list = new ArrayList<>();

        System.out.println("Enter transactions (Sender Receiver Amount Timestamp):");

        for (int i = 0; i < n; i++) {
            String sender = sc.next();
            String receiver = sc.next();
            int amount = sc.nextInt();
            int time = sc.nextInt();

            list.add(new Transaction(sender, receiver, amount, time));
        }

        // HashMap: key -> list of timestamps
        HashMap<String, List<Integer>> map = new HashMap<>();

        for (Transaction t : list) {
            String key = t.sender + "-" + t.receiver + "-" + t.amount;

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(t.time);
        }

        System.out.println("\nFraud Transactions:");

        for (String key : map.keySet()) {

            List<Integer> times = map.get(key);
            Collections.sort(times);

            for (int i = 1; i < times.size(); i++) {

                if (times.get(i) - times.get(i - 1) <= 60) {
                    System.out.println(key + " at time " + times.get(i));
                }
            }
        }
    }
}
