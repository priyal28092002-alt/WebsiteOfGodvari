package com.example.streams;

import java.util.*;
import java.util.stream.*;

public class StreamAPIDemo {

    static class Employee {
        private int id;
        private String name;
        private String department;
        private double salary;
        private int experience;

        public Employee(int id, String name, String department, double salary, int experience) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
            this.experience = experience;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
        public int getExperience() { return experience; }

        @Override
        public String toString() {
            return String.format("Employee{id=%d, name='%s', dept='%s', salary=%.2f, exp=%d}",
                id, name, department, salary, experience);
        }
    }

    public static void main(String[] args) {
        List<Employee> employees = getEmployeeList();

        System.out.println("========== STREAM API DEMONSTRATION ==========\n");

        demonstrateFilter(employees);
        demonstrateMap(employees);
        demonstrateFlatMap();
        demonstrateForEach(employees);
        demonstrateReduce(employees);
        demonstrateCollect(employees);
        demonstrateSort(employees);
        demonstrateMatch(employees);
        demonstrateDistinctAndLimit(employees);
        demonstrateParallelStreams(employees);
        demonstrateGroupingAndPartitioning(employees);
        demonstrateStreamGeneration();
    }

    // 1. FILTER: Selects elements that match a predicate
    private static void demonstrateFilter(List<Employee> employees) {
        System.out.println("\n1. FILTER - Get employees with salary > 50000");
        employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .forEach(System.out::println);
    }

    // 2. MAP: Transforms each element using a function
    private static void demonstrateMap(List<Employee> employees) {
        System.out.println("\n2. MAP - Extract all employee names");
        employees.stream()
            .map(Employee::getName)
            .forEach(System.out::println);

        System.out.println("\n   MAP - Convert salaries to annual bonus (10%)");
        employees.stream()
            .map(e -> e.getName() + ": $" + (e.getSalary() * 0.1))
            .forEach(System.out::println);
    }

    // 3. FLATMAP: Maps and flattens nested streams
    private static void demonstrateFlatMap() {
        System.out.println("\n3. FLATMAP - Flatten nested lists");
        List<List<Integer>> nestedList = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
        );

        System.out.println("   Flattened: " +
            nestedList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList())
        );

        System.out.println("   Squared and flattened: " +
            nestedList.stream()
                .flatMap(list -> list.stream().map(n -> n * n))
                .collect(Collectors.toList())
        );
    }

    // 4. FOREACH: Terminal operation to perform action on each element
    private static void demonstrateForEach(List<Employee> employees) {
        System.out.println("\n4. FOREACH - Process each employee");
        employees.stream()
            .limit(2)
            .forEach(e -> System.out.println("   Processing: " + e.getName()));
    }

    // 5. REDUCE: Aggregates stream elements to single value
    private static void demonstrateReduce(List<Employee> employees) {
        System.out.println("\n5. REDUCE - Aggregate values");

        double totalSalary = employees.stream()
            .map(Employee::getSalary)
            .reduce(0.0, Double::sum);
        System.out.println("   Total salary: $" + totalSalary);

        Optional<Double> maxSalary = employees.stream()
            .map(Employee::getSalary)
            .reduce(Double::max);
        System.out.println("   Max salary: $" + maxSalary.orElse(0.0));

        Optional<String> allNames = employees.stream()
            .map(Employee::getName)
            .reduce((a, b) -> a + ", " + b);
        System.out.println("   All names: " + allNames.orElse(""));
    }

    // 6. COLLECT: Terminal operation that gathers elements into a collection
    private static void demonstrateCollect(List<Employee> employees) {
        System.out.println("\n6. COLLECT - Gather into collections");

        List<String> names = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println("   As List: " + names);

        Set<String> departments = employees.stream()
            .map(Employee::getDepartment)
            .collect(Collectors.toSet());
        System.out.println("   Departments as Set: " + departments);

        String joined = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.joining(", ", "Employees: ", ""));
        System.out.println("   " + joined);
    }

    // 7. SORTED: Terminal operation to sort elements
    private static void demonstrateSort(List<Employee> employees) {
        System.out.println("\n7. SORTED - Sort employees");

        System.out.println("   By name (ascending):");
        employees.stream()
            .sorted(Comparator.comparing(Employee::getName))
            .limit(3)
            .forEach(e -> System.out.println("      " + e.getName()));

        System.out.println("   By salary (descending):");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
            .limit(3)
            .forEach(e -> System.out.println("      " + e.getName() + " - $" + e.getSalary()));
    }

    // 8. MATCH: Terminal operations - anyMatch, allMatch, noneMatch
    private static void demonstrateMatch(List<Employee> employees) {
        System.out.println("\n8. MATCH - Predicate matching");

        boolean anyHighSalary = employees.stream()
            .anyMatch(e -> e.getSalary() > 60000);
        System.out.println("   Any employee with salary > 60000? " + anyHighSalary);

        boolean allHighExperience = employees.stream()
            .allMatch(e -> e.getExperience() >= 2);
        System.out.println("   All have at least 2 years experience? " + allHighExperience);

        boolean noneJunior = employees.stream()
            .noneMatch(e -> e.getExperience() == 0);
        System.out.println("   None are junior (0 experience)? " + noneJunior);
    }

    // 9. DISTINCT and LIMIT
    private static void demonstrateDistinctAndLimit(List<Employee> employees) {
        System.out.println("\n9. DISTINCT & LIMIT");

        System.out.println("   First 3 employees:");
        employees.stream()
            .limit(3)
            .forEach(e -> System.out.println("      " + e.getName()));

        System.out.println("   Distinct departments:");
        employees.stream()
            .map(Employee::getDepartment)
            .distinct()
            .forEach(System.out::println);

        System.out.println("   Skip first 2, take next 2:");
        employees.stream()
            .skip(2)
            .limit(2)
            .forEach(e -> System.out.println("      " + e.getName()));
    }

    // 10. PARALLEL STREAMS: Process data in parallel
    private static void demonstrateParallelStreams(List<Employee> employees) {
        System.out.println("\n10. PARALLEL STREAMS - Process in parallel");

        long startTime = System.currentTimeMillis();
        double sequentialSum = employees.stream()
            .map(Employee::getSalary)
            .reduce(0.0, Double::sum);
        long sequentialTime = System.currentTimeMillis() - startTime;
        System.out.println("    Sequential sum: $" + sequentialSum + " (Time: " + sequentialTime + "ms)");

        startTime = System.currentTimeMillis();
        double parallelSum = employees.parallelStream()
            .map(Employee::getSalary)
            .reduce(0.0, Double::sum);
        long parallelTime = System.currentTimeMillis() - startTime;
        System.out.println("    Parallel sum: $" + parallelSum + " (Time: " + parallelTime + "ms)");
    }

    // 11. GROUPING AND PARTITIONING
    private static void demonstrateGroupingAndPartitioning(List<Employee> employees) {
        System.out.println("\n11. GROUPING & PARTITIONING");

        System.out.println("   Group by department:");
        Map<String, List<Employee>> byDept = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        byDept.forEach((dept, emps) ->
            System.out.println("      " + dept + ": " + emps.size() + " employees")
        );

        System.out.println("   Partition by salary > 50000:");
        Map<Boolean, List<Employee>> bySalary = employees.stream()
            .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000));
        System.out.println("      High salary: " + bySalary.get(true).size());
        System.out.println("      Low salary: " + bySalary.get(false).size());
    }

    // 12. STREAM GENERATION
    private static void demonstrateStreamGeneration() {
        System.out.println("\n12. STREAM GENERATION");

        System.out.println("   Stream.of():");
        Stream.of("A", "B", "C")
            .forEach(s -> System.out.print(s + " "));
        System.out.println();

        System.out.println("   Stream.generate() - first 5 random numbers:");
        Stream.generate(Math::random)
            .limit(5)
            .forEach(n -> System.out.print(String.format("%.2f ", n)));
        System.out.println();

        System.out.println("   Stream.iterate() - first 5 multiples of 2:");
        Stream.iterate(0, n -> n + 2)
            .limit(5)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
    }

    private static List<Employee> getEmployeeList() {
        return Arrays.asList(
            new Employee(1, "Alice", "Engineering", 75000, 5),
            new Employee(2, "Bob", "Sales", 55000, 3),
            new Employee(3, "Charlie", "Engineering", 80000, 7),
            new Employee(4, "Diana", "HR", 45000, 2),
            new Employee(5, "Eve", "Sales", 60000, 4),
            new Employee(6, "Frank", "Engineering", 70000, 3),
            new Employee(7, "Grace", "HR", 48000, 1),
            new Employee(8, "Henry", "Sales", 52000, 2)
        );
    }
}

