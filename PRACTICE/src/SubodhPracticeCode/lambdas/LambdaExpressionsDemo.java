package com.example.lambdas;

import java.util.*;

public class LambdaExpressionsDemo {

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

        System.out.println("========== LAMBDA EXPRESSIONS DEMONSTRATION ==========\n");

        demonstrateBasicLambda();
        demonstratePredicateLambda(employees);
        demonstrateFunctionLambda(employees);
        demonstrateConsumerLambda(employees);
        demonstrateComparatorLambda(employees);
        demonstrateRunnableLambda();
        demonstrateCallableLambda();
        demonstrateCustomFunctionalInterface();
        demonstrateMethodReferences(employees);
        demonstrateLambdaWithStreams(employees);
        demonstrateNestedLambdas(employees);
        demonstrateLambdaVariableCapture();
    }

    // 1. BASIC LAMBDA SYNTAX
    private static void demonstrateBasicLambda() {
        System.out.println("1. BASIC LAMBDA SYNTAX");

        System.out.println("   No parameters:");
        Runnable r1 = () -> System.out.println("      Hello from lambda!");
        r1.run();

        System.out.println("   Single parameter (implicit type):");
        MyFunction<Integer, Integer> square = x -> x * x;
        System.out.println("      Square of 5: " + square.apply(5));

        System.out.println("   Single parameter (explicit type):");
        MyFunction<Integer, Integer> double_num = (Integer x) -> x * 2;
        System.out.println("      Double of 5: " + double_num.apply(5));

        System.out.println("   Multiple parameters:");
        MyBiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("      5 + 3 = " + add.apply(5, 3));

        System.out.println("   Multiple statements in lambda:");
        MyBiFunction<Integer, Integer, Integer> complex = (a, b) -> {
            int sum = a + b;
            int product = a * b;
            return sum + product;
        };
        System.out.println("      (5 + 3) + (5 * 3) = " + complex.apply(5, 3));
    }

    // 2. PREDICATE LAMBDA (boolean condition)
    private static void demonstratePredicateLambda(List<Employee> employees) {
        System.out.println("\n2. PREDICATE LAMBDA - Test boolean conditions");

        MyPredicate<Employee> highSalary = emp -> emp.getSalary() > 60000;
        System.out.println("   Employees with salary > 60000:");
        for (Employee emp : employees) {
            if (highSalary.test(emp)) {
                System.out.println("      " + emp.getName());
            }
        }

        MyPredicate<Employee> experienced = emp -> emp.getExperience() >= 5;
        System.out.println("   Employees with 5+ years experience:");
        for (Employee emp : employees) {
            if (experienced.test(emp)) {
                System.out.println("      " + emp.getName());
            }
        }

        System.out.println("   Combined predicates (high salary AND experienced):");
        MyPredicate<Employee> combined = emp ->
            emp.getSalary() > 60000 && emp.getExperience() >= 5;
        for (Employee emp : employees) {
            if (combined.test(emp)) {
                System.out.println("      " + emp.getName());
            }
        }
    }

    // 3. FUNCTION LAMBDA (transform input to output)
    private static void demonstrateFunctionLambda(List<Employee> employees) {
        System.out.println("\n3. FUNCTION LAMBDA - Transform data");

        MyFunction<Employee, String> getName = emp -> emp.getName();
        System.out.println("   Employee names:");
        for (Employee emp : employees) {
            System.out.println("      " + getName.apply(emp));
        }

        MyFunction<Employee, Double> getBonus = emp -> emp.getSalary() * 0.1;
        System.out.println("   Employee bonuses (10% of salary):");
        for (Employee emp : employees) {
            System.out.println("      " + emp.getName() + ": $" + getBonus.apply(emp));
        }

        MyFunction<Employee, String> getInfo = emp ->
            emp.getName() + " (" + emp.getDepartment() + ")";
        System.out.println("   Employee info:");
        for (Employee emp : employees) {
            System.out.println("      " + getInfo.apply(emp));
        }
    }

    // 4. CONSUMER LAMBDA (perform action, returns void)
    private static void demonstrateConsumerLambda(List<Employee> employees) {
        System.out.println("\n4. CONSUMER LAMBDA - Perform actions");

        MyConsumer<Employee> print = emp ->
            System.out.println("      " + emp.getName() + " earns $" + emp.getSalary());
        System.out.println("   Print all employees:");
        for (Employee emp : employees) {
            print.accept(emp);
        }

        MyConsumer<String> printWithPrefix = str ->
            System.out.println("      > " + str);
        System.out.println("   Print with prefix:");
        printWithPrefix.accept("Lambda is powerful");
        printWithPrefix.accept("Functional programming rocks");
    }

    // 5. COMPARATOR LAMBDA
    private static void demonstrateComparatorLambda(List<Employee> employees) {
        System.out.println("\n5. COMPARATOR LAMBDA - Sort data");

        List<Employee> empCopy = new ArrayList<>(employees);

        System.out.println("   Sort by name (ascending):");
        empCopy.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));
        empCopy.stream().limit(3).forEach(e -> System.out.println("      " + e.getName()));

        empCopy = new ArrayList<>(employees);
        System.out.println("   Sort by salary (descending):");
        empCopy.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        empCopy.stream().limit(3)
            .forEach(e -> System.out.println("      " + e.getName() + " - $" + e.getSalary()));

        empCopy = new ArrayList<>(employees);
        System.out.println("   Sort by experience (ascending):");
        empCopy.sort((e1, e2) -> Integer.compare(e1.getExperience(), e2.getExperience()));
        empCopy.stream().limit(3)
            .forEach(e -> System.out.println("      " + e.getName() + " (" + e.getExperience() + " years)"));
    }

    // 6. RUNNABLE LAMBDA
    private static void demonstrateRunnableLambda() {
        System.out.println("\n6. RUNNABLE LAMBDA - Execute in threads");

        System.out.println("   Create and run threads:");
        Runnable task1 = () -> System.out.println("      Thread 1 executing");
        Runnable task2 = () -> System.out.println("      Thread 2 executing");
        Runnable task3 = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("      Thread 3: iteration " + i);
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
        new Thread(task3).start();

        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }

    // 7. CALLABLE LAMBDA
    private static void demonstrateCallableLambda() {
        System.out.println("\n7. CALLABLE LAMBDA - Return values from threads");

        MyCallable<Integer> calculateSum = () -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        };

        System.out.println("   Sum of 1-10: " + calculateSum.call());

        MyCallable<String> generateMessage = () -> "Generated from callable";
        System.out.println("   Message: " + generateMessage.call());
    }

    // 8. CUSTOM FUNCTIONAL INTERFACE
    private static void demonstrateCustomFunctionalInterface() {
        System.out.println("\n8. CUSTOM FUNCTIONAL INTERFACE");

        Calculator add = (a, b) -> a + b;
        Calculator subtract = (a, b) -> a - b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> a / b;

        System.out.println("   5 + 3 = " + add.calculate(5, 3));
        System.out.println("   5 - 3 = " + subtract.calculate(5, 3));
        System.out.println("   5 * 3 = " + multiply.calculate(5, 3));
        System.out.println("   15 / 3 = " + divide.calculate(15, 3));
    }

    // 9. METHOD REFERENCES (Alternative to Lambda)
    private static void demonstrateMethodReferences(List<Employee> employees) {
        System.out.println("\n9. METHOD REFERENCES - Alternative syntax");

        System.out.println("   Static method reference:");
        MyFunction<Integer, String> toString = String::valueOf;
        System.out.println("      123 as String: " + toString.apply(123));

        System.out.println("   Instance method reference:");
        MyFunction<String, Integer> length = String::length;
        System.out.println("      Length of 'Hello': " + length.apply("Hello"));

        System.out.println("   Constructor reference:");
        MySupplier<List> listCreator = ArrayList::new;
        List<String> list = listCreator.create();
        list.add("Item 1");
        System.out.println("      Created list: " + list);
    }

    // 10. LAMBDA WITH STREAMS
    private static void demonstrateLambdaWithStreams(List<Employee> employees) {
        System.out.println("\n10. LAMBDA WITH STREAMS");

        System.out.println("   Filter using lambda:");
        employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .forEach(e -> System.out.println("      " + e.getName()));

        System.out.println("   Map using lambda:");
        employees.stream()
            .map(e -> e.getName().toUpperCase())
            .forEach(name -> System.out.println("      " + name));

        System.out.println("   Reduce using lambda:");
        double totalSalary = employees.stream()
            .map(e -> e.getSalary())
            .reduce(0.0, (sum, salary) -> sum + salary);
        System.out.println("      Total salary: $" + totalSalary);
    }

    // 11. NESTED LAMBDAS
    private static void demonstrateNestedLambdas(List<Employee> employees) {
        System.out.println("\n11. NESTED LAMBDAS - Lambda returning lambda");

        MyFunction<Integer, MyFunction<Integer, Integer>> multiplier = factor ->
            value -> value * factor;

        MyFunction<Integer, Integer> double_nums = multiplier.apply(2);
        MyFunction<Integer, Integer> triple_nums = multiplier.apply(3);

        System.out.println("   5 * 2 = " + double_nums.apply(5));
        System.out.println("   5 * 3 = " + triple_nums.apply(5));

        MyFunction<Integer, MyFunction<Integer, MyFunction<Integer, Integer>>> add3 =
            a -> b -> c -> a + b + c;

        int result = add3.apply(1).apply(2).apply(3);
        System.out.println("   1 + 2 + 3 = " + result);
    }

    // 12. VARIABLE CAPTURE
    private static void demonstrateLambdaVariableCapture() {
        System.out.println("\n12. LAMBDA VARIABLE CAPTURE");

        int multiplier = 5;
        MyFunction<Integer, Integer> multiply = x -> x * multiplier;
        System.out.println("   Using captured variable (multiplier=5):");
        System.out.println("      3 * 5 = " + multiply.apply(3));

        String prefix = "Result: ";
        MyConsumer<Integer> printWithPrefix = num ->
            System.out.println("      " + prefix + num);
        System.out.println("   Using captured variable (prefix):");
        printWithPrefix.accept(42);

        List<Integer> numbers = Arrays.asList(1, 2, 3);
        int factor = 10;
        System.out.println("   Captured in stream operations:");
        numbers.stream()
            .map(n -> n * factor)
            .forEach(n -> System.out.println("      " + n));
    }

    // FUNCTIONAL INTERFACES
    @FunctionalInterface
    interface MyFunction<T, R> {
        R apply(T t);
    }

    @FunctionalInterface
    interface MyBiFunction<T, U, R> {
        R apply(T t, U u);
    }

    @FunctionalInterface
    interface MyPredicate<T> {
        boolean test(T t);
    }

    @FunctionalInterface
    interface MyConsumer<T> {
        void accept(T t);
    }

    @FunctionalInterface
    interface MyCallable<V> {
        V call();
    }

    @FunctionalInterface
    interface Calculator {
        double calculate(double a, double b);
    }

    @FunctionalInterface
    interface MySupplier<T> {
        T create();
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

