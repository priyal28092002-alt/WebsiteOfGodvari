package com.example.collections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demonstrates sophisticated Comparator chains and ordering utilities.
 */
public class CustomOrderingDemo {
    private record Employee(String name, String department, LocalDate hiredOn, int rating) {}

    public static void main(String[] args) {
        System.out.println("=== Custom Ordering Demo ===\n");
        Comparator<Employee> byDepartmentThenRating = Comparator
                .comparing(Employee::department)
                .thenComparing(Comparator.comparingInt(Employee::rating).reversed())
                .thenComparing(Employee::hiredOn);

        List<Employee> employees = new ArrayList<>(List.of(
                new Employee("Asha", "Engineering", LocalDate.of(2020, 5, 12), 5),
                new Employee("Ravi", "Engineering", LocalDate.of(2019, 3, 30), 3),
                new Employee("Farah", "Product", LocalDate.of(2021, 10, 2), 4),
                new Employee("Lia", "Engineering", LocalDate.of(2022, 1, 5), 5),
                new Employee("Noah", "Product", LocalDate.of(2020, 7, 19), 5)
        ));

        employees.sort(byDepartmentThenRating);
        employees.forEach(emp -> System.out.println(emp.department + " -> " + emp.name + " (rating=" + emp.rating + ")"));
        System.out.println();

        Employee topEngineer = Collections.max(employees, byDepartmentThenRating
                .thenComparing(Employee::name));
        System.out.println("Top engineer per comparator: " + topEngineer);

        Map<Employee, String> evaluationNotes = new TreeMap<>(byDepartmentThenRating);
        employees.forEach(emp -> evaluationNotes.put(emp, emp.name + " ready for next level"));
        System.out.println("Ordered map size: " + evaluationNotes.size());
        evaluationNotes.forEach((emp, note) -> System.out.println(emp + " -> " + note));
        System.out.println();

        Comparator<Employee> nullSafe = Comparator.nullsLast(Comparator.comparing(Employee::name));
        List<Employee> withNulls = new ArrayList<>(employees);
        withNulls.add(null);
        withNulls.sort(nullSafe);
        System.out.println("Null-safe ordering: " + withNulls);
    }
}
