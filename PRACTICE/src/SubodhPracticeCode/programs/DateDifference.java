package com.example.programs;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Program that prints the difference from a specified date till today's date
 * in years, months, and days.
 */

public class DateDifference {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate inputDate = (args != null && args.length > 0)
                ? parseOrExit(args[0])
                : readFromStdin();

        if (inputDate.isEqual(today)) {
            System.out.println("The specified date is today: 0 years, 0 months, 0 days");
            return;
        }

        boolean future = inputDate.isAfter(today);
        LocalDate start = future ? today : inputDate;
        LocalDate end = future ? inputDate : today;

        Period p = Period.between(start, end);

        String direction = future ? "from today until" : "since";
        System.out.printf("%s %s: %d years, %d months, %d days%n",
                direction,
                future ? inputDate : inputDate + " till today",
                p.getYears(), p.getMonths(), p.getDays());
    }

    private static LocalDate parseOrExit(String text) {
        try {
            return LocalDate.parse(text, FMT);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date. Expected format: yyyy-MM-dd (e.g., 2001-09-13)");
            System.exit(1);
            return null; // Unreachable
        }
    }

    private static LocalDate readFromStdin() {
        System.out.print("Enter a date (yyyy-MM-dd): ");
        try (Scanner sc = new Scanner(System.in)) {
            String line = sc.nextLine().trim();
            return parseOrExit(line);
        }
    }
}
