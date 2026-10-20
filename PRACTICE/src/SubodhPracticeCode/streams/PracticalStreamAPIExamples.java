package com.example.streams;

import java.util.*;
import java.util.stream.*;

public class PracticalStreamAPIExamples {

    static class Student {
        private int id;
        private String name;
        private int marks;
        private String branch;

        public Student(int id, String name, int marks, String branch) {
            this.id = id;
            this.name = name;
            this.marks = marks;
            this.branch = branch;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getMarks() { return marks; }
        public String getBranch() { return branch; }

        @Override
        public String toString() {
            return String.format("Student{%d, %s, %d, %s}", id, name, marks, branch);
        }
    }

    public static void main(String[] args) {
        List<Student> students = getStudentList();

        System.out.println("========== PRACTICAL STREAM API EXAMPLES ==========\n");

        example1FilterTopScorers(students);
        example2RankStudents(students);
        example3GenerateReport(students);
        example4FindWithConditions(students);
        example5TransformData(students);
        example6BranchStatistics(students);
        example7ProcessingPipeline();
        example8FilterMapReducePattern(students);
    }

    // Example 1: Filter top scorers
    private static void example1FilterTopScorers(List<Student> students) {
        System.out.println("EXAMPLE 1: Find top scorers (marks > 80)");
        students.stream()
            .filter(s -> s.getMarks() > 80)
            .sorted(Comparator.comparingInt(Student::getMarks).reversed())
            .forEach(s -> System.out.println("   " + s.getName() + " - " + s.getMarks()));
    }

    // Example 2: Rank students by marks
    private static void example2RankStudents(List<Student> students) {
        System.out.println("\nEXAMPLE 2: Rank all students by marks (descending)");
        students.stream()
            .sorted(Comparator.comparingInt(Student::getMarks).reversed())
            .forEach(s -> System.out.println("   " + s.getName() + " (" + s.getMarks() + ")"));
    }

    // Example 3: Generate report with grouping
    private static void example3GenerateReport(List<Student> students) {
        System.out.println("\nEXAMPLE 3: Branch-wise report");

        Map<String, List<Student>> byBranch = students.stream()
            .collect(Collectors.groupingBy(Student::getBranch));

        byBranch.forEach((branch, branchStudents) -> {
            System.out.println("   " + branch + ":");
            branchStudents.stream()
                .sorted(Comparator.comparingInt(Student::getMarks).reversed())
                .forEach(s -> System.out.println("      " + s.getName() + " - " + s.getMarks()));
        });
    }

    // Example 4: Find students with conditions
    private static void example4FindWithConditions(List<Student> students) {
        System.out.println("\nEXAMPLE 4: Find with complex conditions");

        System.out.println("   Find first CS student with marks >= 75:");
        Optional<Student> csStudent = students.stream()
            .filter(s -> "CS".equals(s.getBranch()) && s.getMarks() >= 75)
            .findFirst();
        System.out.println("   " + csStudent.map(Student::getName).orElse("Not found"));

        System.out.println("   Check if any student has 100 marks:");
        boolean hasTopper = students.stream()
            .anyMatch(s -> s.getMarks() == 100);
        System.out.println("   " + hasTopper);

        System.out.println("   Check if all students passed (marks >= 40):");
        boolean allPassed = students.stream()
            .allMatch(s -> s.getMarks() >= 40);
        System.out.println("   " + allPassed);
    }

    // Example 5: Transform data
    private static void example5TransformData(List<Student> students) {
        System.out.println("\nEXAMPLE 5: Transform student data");

        System.out.println("   Extract names only:");
        List<String> names = students.stream()
            .map(Student::getName)
            .collect(Collectors.toList());
        System.out.println("   " + names);

        System.out.println("   Convert marks to grade:");
        Map<String, String> grades = students.stream()
            .collect(Collectors.toMap(
                Student::getName,
                s -> getGrade(s.getMarks())
            ));
        grades.forEach((name, grade) ->
            System.out.println("   " + name + " - Grade: " + grade));
    }

    // Example 6: Branch statistics
    private static void example6BranchStatistics(List<Student> students) {
        System.out.println("\nEXAMPLE 6: Statistics by branch");

        Map<String, IntSummaryStatistics> stats = students.stream()
            .collect(Collectors.groupingBy(
                Student::getBranch,
                Collectors.summarizingInt(Student::getMarks)
            ));

        stats.forEach((branch, branchStats) -> {
            System.out.println("   " + branch + ":");
            System.out.println("      Count: " + branchStats.getCount());
            System.out.println("      Avg: " + String.format("%.2f", branchStats.getAverage()));
            System.out.println("      Max: " + branchStats.getMax());
            System.out.println("      Min: " + branchStats.getMin());
        });
    }

    // Example 7: Processing pipeline - Multi-step transformation
    private static void example7ProcessingPipeline() {
        System.out.println("\nEXAMPLE 7: Multi-step processing pipeline");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("   Transform: Filter even, square, sum:");
        int result = numbers.stream()
            .filter(n -> n % 2 == 0)          // Intermediate: Filter even
            .map(n -> n * n)                  // Intermediate: Square
            .peek(n -> System.out.print(n + " "))  // Intermediate: Debug
            .reduce(0, Integer::sum);         // Terminal: Sum
        System.out.println("\n   Sum of squares of even numbers: " + result);
    }

    // Example 8: Filter-Map-Reduce pattern
    private static void example8FilterMapReducePattern(List<Student> students) {
        System.out.println("\nEXAMPLE 8: Filter-Map-Reduce pattern");

        // Calculate average marks for CS branch students
        double avgCSMarks = students.stream()
            .filter(s -> "CS".equals(s.getBranch()))  // Filter
            .mapToInt(Student::getMarks)               // Map to int
            .average()                                 // Reduce
            .orElse(0.0);
        System.out.println("   Average marks in CS branch: " + String.format("%.2f", avgCSMarks));

        // Get top scorer name by branch
        System.out.println("   Top scorer by branch:");
        students.stream()
            .collect(Collectors.groupingBy(
                Student::getBranch,
                Collectors.maxBy(Comparator.comparingInt(Student::getMarks))
            ))
            .forEach((branch, topStudent) ->
                System.out.println("      " + branch + ": " +
                    topStudent.map(Student::getName).orElse("N/A")));

        // Count distinctions (marks >= 90)
        long distinctions = students.stream()
            .filter(s -> s.getMarks() >= 90)
            .count();
        System.out.println("   Students with distinctions (>= 90): " + distinctions);
    }

    private static String getGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        return "F";
    }

    private static List<Student> getStudentList() {
        return Arrays.asList(
            new Student(1, "Raj", 92, "CS"),
            new Student(2, "Priya", 85, "CS"),
            new Student(3, "Amit", 78, "EC"),
            new Student(4, "Neha", 95, "EC"),
            new Student(5, "Arjun", 88, "CS"),
            new Student(6, "Sakshi", 72, "ME"),
            new Student(7, "Vikram", 81, "ME"),
            new Student(8, "Disha", 91, "EC"),
            new Student(9, "Rohan", 65, "CS"),
            new Student(10, "Pooja", 79, "ME")
        );
    }
}

