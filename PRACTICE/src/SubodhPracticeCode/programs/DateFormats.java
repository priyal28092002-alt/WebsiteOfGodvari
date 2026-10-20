package com.example.programs;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormats {
    public static void main(String[] args) {
        Date now = new Date();

        // Default format
        System.out.println("Default format: " + now.toString());

        // Custom formats
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Format 1: " + sdf1.format(now));

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd-yyyy");
        System.out.println("Format 2: " + sdf2.format(now));

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println("Format 3: " + sdf3.format(now));

        SimpleDateFormat sdf4 = new SimpleDateFormat("EEE, MMM d, ''yy");
        System.out.println("Format 4: " + sdf4.format(now));

        SimpleDateFormat sdf5 = new SimpleDateFormat("h:mm a");
        System.out.println("Format 5: " + sdf5.format(now));

        SimpleDateFormat sdf6 = new SimpleDateFormat("hh 'o''clock' a, zzzz");
        System.out.println("Format 6: " + sdf6.format(now));

        SimpleDateFormat sdf7 = new SimpleDateFormat("K:mm a, z");
        System.out.println("Format 7: " + sdf7.format(now));

        SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyy.MMMMM.dd GGG hh:mm aaa");
        System.out.println("Format 8: " + sdf8.format(now));

        // LocalDate format
        LocalDate localDate = LocalDate.now();
        System.out.println("LocalDate default format: " + localDate);

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("LocalDate format 1: " + localDate.format(dtf1));

        // LocalDateTime format
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime default format: " + localDateTime);

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("LocalDateTime format 1: " + localDateTime.format(dtf2));

        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println("LocalDateTime format 2: " + localDateTime.format(dtf3));

        // ZonedDateTime format
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("ZonedDateTime default format: " + zonedDateTime);

        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        System.out.println("ZonedDateTime format 1: " + zonedDateTime.format(dtf4));

        // OffsetDateTime format
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        System.out.println("OffsetDateTime default format: " + offsetDateTime);

        DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z");
        System.out.println("OffsetDateTime format 1: " + offsetDateTime.format(dtf5));
    }
}
