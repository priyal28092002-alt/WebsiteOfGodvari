package com.example.lambdas;

import java.util.*;
import java.util.function.*;

public class PracticalLambdaExamples {

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

        System.out.println("========== PRACTICAL LAMBDA EXAMPLES ==========\n");

        example1EventHandling();
        example2DataFiltering(students);
        example3DataTransformation(students);
        example4Sorting(students);
        example5ValidatingData(students);
        example6CallbackFunctions();
        example7ComputingStatistics(students);
        example8ThreadManagement();
        example9FunctionalChaining();
        example10RealWorldScenarios(students);
    }

    // Example 1: Event Handling
    private static void example1EventHandling() {
        System.out.println("EXAMPLE 1: Event Handling with Lambda");

        class Button {
            private String name;
            private Runnable clickHandler;

            Button(String name) { this.name = name; }
            void onClick(Runnable handler) { this.clickHandler = handler; }
            void click() {
                System.out.println("   Button '" + name + "' clicked");
                if (clickHandler != null) clickHandler.run();
            }
        }

        Button submitBtn = new Button("Submit");
        submitBtn.onClick(() -> System.out.println("   -> Form submitted"));
        submitBtn.click();

        Button cancelBtn = new Button("Cancel");
        cancelBtn.onClick(() -> {
            System.out.println("   -> Form cancelled");
            System.out.println("   -> Clearing data");
        });
        cancelBtn.click();
    }

    // Example 2: Data Filtering
    private static void example2DataFiltering(List<Student> students) {
        System.out.println("\nEXAMPLE 2: Data Filtering with Lambda");

        Predicate<Student> topScorers = s -> s.getMarks() >= 80;
        Predicate<Student> csStudents = s -> "CS".equals(s.getBranch());

        System.out.println("   Students with marks >= 80:");
        filterAndPrint(students, topScorers);

        System.out.println("   CS branch students:");
        filterAndPrint(students, csStudents);

        System.out.println("   CS students with marks >= 75:");
        filterAndPrint(students, csStudents.and(s -> s.getMarks() >= 75));
    }

    // Example 3: Data Transformation
    private static void example3DataTransformation(List<Student> students) {
        System.out.println("\nEXAMPLE 3: Data Transformation with Lambda");

        System.out.println("   Extract student names:");
        Function<Student, String> extractName = s -> s.getName();
        students.stream().limit(3).map(extractName)
            .forEach(name -> System.out.println("      " + name));

        System.out.println("   Convert marks to grades:");
        Function<Student, String> toGrade = s -> {
            int m = s.getMarks();
            return m >= 90 ? "A+" : m >= 80 ? "A" : m >= 70 ? "B" : "C";
        };
        students.stream().limit(3)
            .forEach(s -> System.out.println("      " + s.getName() + " -> " + toGrade.apply(s)));

        System.out.println("   Create student summaries:");
        Function<Student, String> summary = s ->
            s.getName() + " (" + s.getBranch() + "): " + s.getMarks() + " marks";
        students.stream().limit(3)
            .map(summary).forEach(str -> System.out.println("      " + str));
    }

    // Example 4: Sorting with Lambda
    private static void example4Sorting(List<Student> students) {
        System.out.println("\nEXAMPLE 4: Sorting with Lambda Comparators");

        System.out.println("   Sort by marks (descending):");
        List<Student> copy = new ArrayList<>(students);
        copy.sort((s1, s2) -> Integer.compare(s2.getMarks(), s1.getMarks()));
        copy.stream().limit(3).forEach(s ->
            System.out.println("      " + s.getName() + " - " + s.getMarks()));

        System.out.println("   Sort by name (ascending):");
        copy = new ArrayList<>(students);
        copy.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
        copy.stream().limit(3).forEach(s ->
            System.out.println("      " + s.getName()));

        System.out.println("   Sort by branch then marks:");
        copy = new ArrayList<>(students);
        copy.sort((s1, s2) -> {
            int branchCmp = s1.getBranch().compareTo(s2.getBranch());
            return branchCmp != 0 ? branchCmp :
                Integer.compare(s2.getMarks(), s1.getMarks());
        });
        copy.stream().forEach(s ->
            System.out.println("      " + s.getBranch() + " - " + s.getName() + " (" + s.getMarks() + ")"));
    }

    // Example 5: Validating Data
    private static void example5ValidatingData(List<Student> students) {
        System.out.println("\nEXAMPLE 5: Data Validation with Lambda");

        Predicate<Student> isValidStudent = s ->
            s.getName() != null && !s.getName().isEmpty() &&
            s.getMarks() >= 0 && s.getMarks() <= 100 &&
            s.getBranch() != null && !s.getBranch().isEmpty();

        System.out.println("   Validating students:");
        students.stream().forEach(s -> {
            boolean valid = isValidStudent.test(s);
            System.out.println("      " + s.getName() + " - " + (valid ? "VALID" : "INVALID"));
        });

        Predicate<Student> passedExam = s -> s.getMarks() >= 40;
        System.out.println("   Students who passed (marks >= 40):");
        students.stream().filter(passedExam)
            .forEach(s -> System.out.println("      " + s.getName() + " - " + s.getMarks()));
    }

    // Example 6: Callback Functions
    private static void example6CallbackFunctions() {
        System.out.println("\nEXAMPLE 6: Callback Functions with Lambda");

        class DataProcessor {
            void processData(String data, Consumer<String> onSuccess, Consumer<String> onError) {
                if (data != null && !data.isEmpty()) {
                    onSuccess.accept("Processed: " + data.toUpperCase());
                } else {
                    onError.accept("Error: Empty data");
                }
            }
        }

        DataProcessor processor = new DataProcessor();

        processor.processData("hello",
            success -> System.out.println("   Success: " + success),
            error -> System.out.println("   Error: " + error));

        processor.processData("",
            success -> System.out.println("   Success: " + success),
            error -> System.out.println("   " + error));
    }

    // Example 7: Computing Statistics
    private static void example7ComputingStatistics(List<Student> students) {
        System.out.println("\nEXAMPLE 7: Computing Statistics with Lambda");

        System.out.println("   Average marks (manual calculation):");
        double avgMarks = students.stream()
            .mapToInt(Student::getMarks)
            .average()
            .orElse(0.0);
        System.out.println("      " + String.format("%.2f", avgMarks));

        System.out.println("   Marks distribution by branch:");
        Map<String, Double> avgByBranch = new HashMap<>();
        students.stream().forEach(s -> {
            String branch = s.getBranch();
            double avg = students.stream()
                .filter(st -> st.getBranch().equals(branch))
                .mapToInt(Student::getMarks)
                .average()
                .orElse(0.0);
            avgByBranch.put(branch, avg);
        });
        avgByBranch.forEach((branch, avg) ->
            System.out.println("      " + branch + ": " + String.format("%.2f", avg)));

        System.out.println("   Count students per branch:");
        Map<String, Long> countByBranch = new HashMap<>();
        students.forEach(s -> {
            String branch = s.getBranch();
            countByBranch.put(branch,
                countByBranch.getOrDefault(branch, 0L) + 1);
        });
        countByBranch.forEach((branch, count) ->
            System.out.println("      " + branch + ": " + count + " students"));
    }

    // Example 8: Thread Management
    private static void example8ThreadManagement() {
        System.out.println("\nEXAMPLE 8: Thread Management with Lambda");

        System.out.println("   Creating threads with lambda:");
        Thread t1 = new Thread(() -> {
            System.out.println("      Thread 1 executing");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            System.out.println("      Thread 1 completed");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("      Thread 2 executing");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            System.out.println("      Thread 2 completed");
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        System.out.println("   All threads completed");
    }

    // Example 9: Functional Chaining
    private static void example9FunctionalChaining() {
        System.out.println("\nEXAMPLE 9: Functional Chaining with Lambda");

        Function<Integer, Integer> addFive = x -> {
            System.out.println("      Step 1: Add 5");
            return x + 5;
        };
        Function<Integer, Integer> multiplyTwo = x -> {
            System.out.println("      Step 2: Multiply by 2");
            return x * 2;
        };
        Function<Integer, Integer> square = x -> {
            System.out.println("      Step 3: Square");
            return x * x;
        };

        System.out.println("   Chain: add 5 -> multiply 2 -> square");
        Function<Integer, Integer> pipeline =
            addFive.andThen(multiplyTwo).andThen(square);

        int result = pipeline.apply(3);
        System.out.println("      Result: " + result);
    }

    // Example 10: Real-World Scenarios
    private static void example10RealWorldScenarios(List<Student> students) {
        System.out.println("\nEXAMPLE 10: Real-World Scenarios");

        System.out.println("   Scenario 1: Filter, sort, and display top 3 performers");
        students.stream()
            .filter(s -> s.getMarks() >= 70)
            .sorted((s1, s2) -> Integer.compare(s2.getMarks(), s1.getMarks()))
            .limit(3)
            .forEach(s -> System.out.println("      " + s.getName() + " - " + s.getMarks()));

        System.out.println("   Scenario 2: Find top scorer in each branch");
        Map<String, Student> topByBranch = new HashMap<>();
        students.forEach(s -> {
            String branch = s.getBranch();
            topByBranch.merge(branch, s, (existing, current) ->
                current.getMarks() > existing.getMarks() ? current : existing);
        });
        topByBranch.forEach((branch, student) ->
            System.out.println("      " + branch + ": " + student.getName() + " (" + student.getMarks() + ")"));

        System.out.println("   Scenario 3: Generate report with filtering and grouping");
        System.out.println("      Merit List (marks >= 80):");
        students.stream()
            .filter(s -> s.getMarks() >= 80)
            .sorted((s1, s2) -> Integer.compare(s2.getMarks(), s1.getMarks()))
            .forEach(s -> System.out.println("         " + s.getName() + " (" + s.getBranch() + ") - " + s.getMarks()));
    }

    private static void filterAndPrint(List<Student> students, Predicate<Student> predicate) {
        students.stream()
            .filter(predicate)
            .forEach(s -> System.out.println("      " + s.getName()));
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

