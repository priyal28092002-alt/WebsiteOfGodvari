package com.example.lambdas;

import java.util.*;
import java.util.function.*;

public class LambdaExpressionsCheatSheet {

    public static void main(String[] args) {
        System.out.println("========== LAMBDA EXPRESSIONS CHEAT SHEET ==========\n");

        demonstrateLambdaSyntax();
        demonstrateBuiltInInterfaces();
        demonstrateMethodReferences();
        demonstrateComparators();
        demonstrateCollectionOperations();
        demonstrateStreamOperations();
        demonstrateComposition();
        demonstratePracticalPatterns();
        demonstrateCommonMistakes();
        demonstratePerformance();
    }

    // 1. LAMBDA SYNTAX VARIATIONS
    private static void demonstrateLambdaSyntax() {
        System.out.println("1. LAMBDA SYNTAX VARIATIONS");

        // No parameters
        Runnable noParams = () -> System.out.println("   No parameters");
        noParams.run();

        // Single parameter (implicit type)
        Function<Integer, Integer> square = x -> x * x;
        System.out.println("   Single param implicit type: 5^2 = " + square.apply(5));

        // Single parameter (explicit type)
        Function<Integer, Integer> double_it = (Integer x) -> x * 2;
        System.out.println("   Single param explicit type: 5*2 = " + double_it.apply(5));

        // Multiple parameters
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("   Multiple params: 5+3 = " + add.apply(5, 3));

        // Single expression body
        Function<String, Integer> length = str -> str.length();
        System.out.println("   Single expression: 'hello'.length = " + length.apply("hello"));

        // Block body with multiple statements
        Function<Integer, Integer> complex = x -> {
            int doubled = x * 2;
            int squared = doubled * doubled;
            return squared;
        };
        System.out.println("   Block body: ((5*2)^2) = " + complex.apply(5));
    }

    // 2. BUILT-IN FUNCTIONAL INTERFACES
    private static void demonstrateBuiltInInterfaces() {
        System.out.println("\n2. BUILT-IN FUNCTIONAL INTERFACES");

        // Predicate<T> - test(T) returns boolean
        Predicate<Integer> isPositive = x -> x > 0;
        System.out.println("   Predicate: Is 5 positive? " + isPositive.test(5));

        // Function<T, R> - apply(T) returns R
        Function<String, Integer> strlen = str -> str.length();
        System.out.println("   Function: Length of 'lambda' = " + strlen.apply("lambda"));

        // BiFunction<T, U, R> - apply(T, U) returns R
        BiFunction<Integer, Integer, String> concat =
            (a, b) -> "Result: " + (a + b);
        System.out.println("   BiFunction: " + concat.apply(3, 4));

        // Consumer<T> - accept(T) returns void
        Consumer<String> print = str -> System.out.println("   Consumer: " + str);
        print.accept("Hello Lambda");

        // Supplier<T> - get() returns T
        Supplier<Double> random = () -> Math.random();
        System.out.println("   Supplier: Random = " + random.get());

        // BinaryOperator<T> - apply(T, T) returns T
        BinaryOperator<Integer> max = (a, b) -> a > b ? a : b;
        System.out.println("   BinaryOperator: Max of 5,3 = " + max.apply(5, 3));

        // UnaryOperator<T> - apply(T) returns T
        UnaryOperator<Integer> negate = x -> -x;
        System.out.println("   UnaryOperator: Negate 5 = " + negate.apply(5));
    }

    // 3. METHOD REFERENCES
    private static void demonstrateMethodReferences() {
        System.out.println("\n3. METHOD REFERENCES (Alternative to Lambda)");

        // Static method reference: ClassName::staticMethod
        Function<Integer, String> toStr = String::valueOf;
        System.out.println("   Static method: String::valueOf(123) = " + toStr.apply(123));

        // Instance method reference: instance::method
        String str = "hello";
        Function<String, Integer> length = str::compareTo;
        System.out.println("   Instance method: 'hello'.compareTo('world') = " +
            length.apply("world"));

        // Constructor reference: ClassName::new
        Supplier<List<String>> createList = ArrayList::new;
        List<String> list = createList.get();
        list.add("Item");
        System.out.println("   Constructor: Created ArrayList: " + list);

        // Arbitrary instance method: Type::method
        Function<String, Integer> getLength = String::length;
        System.out.println("   Type method: 'lambda'.length() = " + getLength.apply("lambda"));
    }

    // 4. COMPARATORS WITH LAMBDA
    private static void demonstrateComparators() {
        System.out.println("\n4. COMPARATORS WITH LAMBDA");

        List<String> words = Arrays.asList("java", "lambda", "functional", "programming");

        System.out.println("   Sort by length:");
        words.stream()
            .sorted((w1, w2) -> Integer.compare(w1.length(), w2.length()))
            .forEach(w -> System.out.println("      " + w));

        System.out.println("   Sort by length descending:");
        words.stream()
            .sorted((w1, w2) -> Integer.compare(w2.length(), w1.length()))
            .forEach(w -> System.out.println("      " + w));

        System.out.println("   Sort alphabetically with comparingInt:");
        words.stream()
            .sorted(Comparator.comparingInt(String::length))
            .forEach(w -> System.out.println("      " + w));

        System.out.println("   Reverse sort:");
        words.stream()
            .sorted(Comparator.comparingInt(String::length).reversed())
            .forEach(w -> System.out.println("      " + w));
    }

    // 5. COLLECTION OPERATIONS WITH LAMBDA
    private static void demonstrateCollectionOperations() {
        System.out.println("\n5. COLLECTION OPERATIONS WITH LAMBDA");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("   forEach:");
        nums.forEach(n -> System.out.print("   " + n));
        System.out.println();

        System.out.println("   removeIf:");
        List<Integer> copy = new ArrayList<>(nums);
        copy.removeIf(n -> n % 2 == 0);
        System.out.println("      After removing even: " + copy);

        System.out.println("   replaceAll:");
        copy = new ArrayList<>(nums);
        copy.replaceAll(n -> n * n);
        System.out.println("      After squaring: " + copy);

        System.out.println("   compute operations on Map:");
        Map<String, Integer> map = new HashMap<>();
        map.compute("count", (k, v) -> v == null ? 1 : v + 1);
        System.out.println("      " + map);

        System.out.println("   forEach on Map:");
        map.put("key", 100);
        map.forEach((k, v) -> System.out.println("      " + k + " = " + v));
    }

    // 6. STREAM OPERATIONS WITH LAMBDA
    private static void demonstrateStreamOperations() {
        System.out.println("\n6. STREAM OPERATIONS WITH LAMBDA");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("   filter and forEach:");
        nums.stream()
            .filter(n -> n % 2 == 0)
            .forEach(n -> System.out.print("   " + n));
        System.out.println();

        System.out.println("   map:");
        nums.stream()
            .map(n -> n * n)
            .forEach(n -> System.out.print("   " + n));
        System.out.println();

        System.out.println("   reduce (sum):");
        int sum = nums.stream().reduce(0, (a, b) -> a + b);
        System.out.println("      Sum = " + sum);

        System.out.println("   reduce (product):");
        int product = nums.stream()
            .filter(n -> n <= 5)
            .reduce(1, (a, b) -> a * b);
        System.out.println("      Product of 1-5 = " + product);

        System.out.println("   sorted:");
        nums.stream()
            .sorted((a, b) -> Integer.compare(b, a))
            .limit(3)
            .forEach(n -> System.out.print("   " + n));
        System.out.println();
    }

    // 7. FUNCTION COMPOSITION
    private static void demonstrateComposition() {
        System.out.println("\n7. FUNCTION COMPOSITION");

        Function<Integer, Integer> add5 = x -> x + 5;
        Function<Integer, Integer> mult2 = x -> x * 2;

        System.out.println("   andThen: add5 then mult2");
        Function<Integer, Integer> pipeline = add5.andThen(mult2);
        System.out.println("      (3 + 5) * 2 = " + pipeline.apply(3));

        System.out.println("   compose: mult2 then add5");
        Function<Integer, Integer> pipeline2 = add5.compose(mult2);
        System.out.println("      (3 * 2) + 5 = " + pipeline2.apply(3));

        Predicate<Integer> gt5 = x -> x > 5;
        Predicate<Integer> lt10 = x -> x < 10;

        System.out.println("   and: between 5 and 10");
        Predicate<Integer> between = gt5.and(lt10);
        System.out.println("      Is 7 between 5,10? " + between.test(7));

        System.out.println("   or/negate:");
        Predicate<Integer> orPred = gt5.or(x -> x < 0);
        System.out.println("      Is 15 > 5 OR < 0? " + orPred.test(15));
    }

    // 8. PRACTICAL PATTERNS
    private static void demonstratePracticalPatterns() {
        System.out.println("\n8. PRACTICAL PATTERNS");

        System.out.println("   Callback pattern:");
        class Task {
            void execute(Consumer<String> onComplete) {
                onComplete.accept("Task completed!");
            }
        }
        Task task = new Task();
        task.execute(msg -> System.out.println("      " + msg));

        System.out.println("   Filter-map-reduce pattern:");
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        int result = nums.stream()
            .filter(n -> n > 2)
            .map(n -> n * n)
            .reduce(0, Integer::sum);
        System.out.println("      Sum of squares > 2: " + result);

        System.out.println("   Sorting with chained comparators:");
        List<String> words = Arrays.asList("aa", "b", "ccc", "dd");
        words.sort(Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        System.out.println("      " + words);
    }

    // 9. COMMON MISTAKES
    private static void demonstrateCommonMistakes() {
        System.out.println("\n9. COMMON MISTAKES & HOW TO AVOID");

        System.out.println("   Mistake 1: Modifying captured variables");
        System.out.println("      Only effectively final variables can be captured");
        int num = 5; // Must be effectively final
        Function<Integer, Integer> mult = x -> x * num;
        System.out.println("      Multiplied: " + mult.apply(3));

        System.out.println("   Mistake 2: Forgetting return in block");
        Function<Integer, Integer> correct = x -> {
            int result = x * 2;
            return result; // Must explicitly return
        };
        System.out.println("      Result: " + correct.apply(5));

        System.out.println("   Mistake 3: Using null instead of Optional");
        String value = "hello";
        // Better: Optional.ofNullable(value).ifPresent(System.out::println);
        System.out.println("      Value: " + value);
    }

    // 10. PERFORMANCE TIPS
    private static void demonstratePerformance() {
        System.out.println("\n10. PERFORMANCE TIPS");

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("   Avoid creating unnecessary objects:");
        System.out.println("      Bad: nums.stream().filter(n -> new Integer(n) > 2)");
        System.out.println("      Good: nums.stream().filter(n -> n > 2)");

        System.out.println("   Use parallelStream for large datasets:");
        long start = System.nanoTime();
        nums.stream().filter(n -> n > 0).count();
        System.out.println("      Sequential time: " + (System.nanoTime() - start) + "ns");

        System.out.println("   Prefer method references to lambdas when available:");
        System.out.println("      String::length instead of s -> s.length()");

        System.out.println("   Short-circuit operations:");
        System.out.println("      .findFirst() stops at first match");
        System.out.println("      .limit(n) stops after n elements");
    }
}

