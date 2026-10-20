package com.example.lambdas;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class AdvancedLambdaExpressionsDemo {

    static class Product {
        private int id;
        private String name;
        private double price;
        private String category;
        private int stock;

        public Product(int id, String name, double price, String category, int stock) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
            this.stock = stock;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public int getStock() { return stock; }

        @Override
        public String toString() {
            return String.format("%s ($%.2f)", name, price);
        }
    }

    public static void main(String[] args) {
        List<Product> products = getProductList();

        System.out.println("========== ADVANCED LAMBDA EXPRESSIONS ==========\n");

        demonstrateBuiltInFunctionalInterfaces();
        demonstrateComposingLambdas();
        demonstrateStatefulLambdas(products);
        demonstrateExceptionHandlingInLambdas();
        demonstrateHigherOrderFunctions();
        demonstrateLambdaWithCollections(products);
        demonstrateFunctionalComposition();
        demonstratePartialApplication();
        demonstrateRecursionWithLambdas();
        demonstrateLambdaVsAnonymousClass();
    }

    // 1. BUILT-IN FUNCTIONAL INTERFACES
    private static void demonstrateBuiltInFunctionalInterfaces() {
        System.out.println("1. BUILT-IN FUNCTIONAL INTERFACES");

        // Predicate<T>
        Predicate<Integer> isEven = n -> n % 2 == 0;
        System.out.println("   Predicate - Is 4 even? " + isEven.test(4));
        System.out.println("   Predicate - Is 5 even? " + isEven.test(5));

        // Function<T, R>
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("   Function - Length of 'Hello': " + stringLength.apply("Hello"));

        // BiFunction<T, U, R>
        BiFunction<Integer, Integer, Integer> max = (a, b) -> a > b ? a : b;
        System.out.println("   BiFunction - Max of 5 and 3: " + max.apply(5, 3));

        // Consumer<T>
        Consumer<String> print = str -> System.out.println("   Consumer - " + str);
        print.accept("Print this message");

        // Supplier<T>
        Supplier<Double> randomNum = () -> Math.random();
        System.out.println("   Supplier - Random: " + randomNum.get());

        // BinaryOperator<T>
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        System.out.println("   BinaryOperator - 5 + 3 = " + sum.apply(5, 3));
    }

    // 2. COMPOSING LAMBDAS (Combining multiple lambdas)
    private static void demonstrateComposingLambdas() {
        System.out.println("\n2. COMPOSING LAMBDAS");

        Function<Integer, Integer> addOne = x -> x + 1;
        Function<Integer, Integer> multiply = x -> x * 2;

        System.out.println("   Compose: addOne then multiply");
        Function<Integer, Integer> composed = addOne.andThen(multiply);
        System.out.println("      (5 + 1) * 2 = " + composed.apply(5));

        System.out.println("   Compose: multiply then addOne");
        Function<Integer, Integer> composed2 = addOne.compose(multiply);
        System.out.println("      (5 * 2) + 1 = " + composed2.apply(5));

        Predicate<Integer> greaterThan5 = n -> n > 5;
        Predicate<Integer> lessThan10 = n -> n < 10;

        System.out.println("   Predicate AND:");
        Predicate<Integer> between = greaterThan5.and(lessThan10);
        System.out.println("      Is 7 between 5 and 10? " + between.test(7));
        System.out.println("      Is 3 between 5 and 10? " + between.test(3));

        System.out.println("   Predicate OR:");
        Predicate<Integer> orPred = greaterThan5.or(lessThan10);
        System.out.println("      Is 15 > 5 OR < 10? " + orPred.test(15));

        System.out.println("   Predicate NEGATE:");
        Predicate<Integer> notGreater5 = greaterThan5.negate();
        System.out.println("      Is NOT 7 > 5? " + notGreater5.test(7));
    }

    // 3. STATEFUL LAMBDAS
    private static void demonstrateStatefulLambdas(List<Product> products) {
        System.out.println("\n3. STATEFUL LAMBDAS - Maintaining state");

        class Counter {
            int count = 0;
        }
        Counter counter = new Counter();

        Consumer<Product> countItems = product -> {
            counter.count++;
            System.out.println("   " + counter.count + ". " + product.getName());
        };

        System.out.println("   Count as we process:");
        products.stream().limit(3).forEach(countItems);

        class Accumulator {
            double total = 0;
        }
        Accumulator acc = new Accumulator();

        products.stream()
            .forEach(p -> {
                acc.total += p.getPrice();
            });
        System.out.println("   Total price accumulated: $" + acc.total);
    }

    // 4. EXCEPTION HANDLING IN LAMBDAS
    private static void demonstrateExceptionHandlingInLambdas() {
        System.out.println("\n4. EXCEPTION HANDLING IN LAMBDAS");

        List<String> numbers = Arrays.asList("10", "20", "abc", "30", "xyz", "40");

        System.out.println("   Safe conversion with try-catch wrapper:");
        numbers.stream()
            .forEach(str -> {
                try {
                    int num = Integer.parseInt(str);
                    System.out.println("      Converted: " + num);
                } catch (NumberFormatException e) {
                    System.out.println("      Invalid: " + str);
                }
            });

        System.out.println("   Using filter to avoid exceptions:");
        numbers.stream()
            .filter(str -> {
                try {
                    Integer.parseInt(str);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            })
            .map(Integer::parseInt)
            .forEach(num -> System.out.println("      " + num));
    }

    // 5. HIGHER ORDER FUNCTIONS
    private static void demonstrateHigherOrderFunctions() {
        System.out.println("\n5. HIGHER ORDER FUNCTIONS");

        // Function that returns a function
        Function<Integer, Function<Integer, Integer>> multiply =
            factor -> value -> value * factor;

        Function<Integer, Integer> double_num = multiply.apply(2);
        Function<Integer, Integer> triple_num = multiply.apply(3);

        System.out.println("   Function returning function:");
        System.out.println("      5 * 2 = " + double_num.apply(5));
        System.out.println("      5 * 3 = " + triple_num.apply(5));

        // Function that takes a function as parameter
        Function<Function<Integer, Integer>, Integer> applyTwice =
            func -> func.apply(func.apply(5));

        Function<Integer, Integer> addTwo = x -> x + 2;
        System.out.println("   Function taking function as parameter:");
        System.out.println("      Apply 'add 2' twice to 5: " + applyTwice.apply(addTwo));
    }

    // 6. LAMBDA WITH COLLECTIONS
    private static void demonstrateLambdaWithCollections(List<Product> products) {
        System.out.println("\n6. LAMBDA WITH COLLECTIONS");

        System.out.println("   removeIf using lambda:");
        List<Product> modifiable = new ArrayList<>(products);
        modifiable.removeIf(p -> p.getPrice() < 50);
        System.out.println("      Products >= $50: " + modifiable.size());

        System.out.println("   forEach on Map:");
        Map<String, Double> prices = new HashMap<>();
        products.forEach(p -> prices.put(p.getName(), p.getPrice()));
        prices.forEach((name, price) ->
            System.out.println("      " + name + ": $" + price));

        System.out.println("   replaceAll using lambda:");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.replaceAll(n -> n * n);
        System.out.println("      Squared: " + numbers);

        System.out.println("   computeIfAbsent in Map:");
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = {"java", "lambda", "java", "functional"};
        for (String word : words) {
            wordCount.computeIfAbsent(word, k -> 0);
            wordCount.put(word, wordCount.get(word) + 1);
        }
        wordCount.forEach((word, count) ->
            System.out.println("      " + word + ": " + count));
    }

    // 7. FUNCTIONAL COMPOSITION (Combining functions)
    private static void demonstrateFunctionalComposition() {
        System.out.println("\n7. FUNCTIONAL COMPOSITION");

        Function<Integer, Integer> addFive = x -> x + 5;
        Function<Integer, Integer> multiplyTwo = x -> x * 2;
        Function<Integer, Integer> squared = x -> x * x;

        System.out.println("   Chain: add 5, then multiply 2, then square");
        Function<Integer, Integer> pipeline =
            addFive.andThen(multiplyTwo).andThen(squared);
        System.out.println("      ((3 + 5) * 2)^2 = " + pipeline.apply(3));

        System.out.println("   Reverse chain: square, then multiply 2, then add 5");
        Function<Integer, Integer> reversePipeline =
            squared.andThen(multiplyTwo).andThen(addFive);
        System.out.println("      (3^2 * 2) + 5 = " + reversePipeline.apply(3));
    }

    // 8. PARTIAL APPLICATION (Fixing some arguments)
    private static void demonstratePartialApplication() {
        System.out.println("\n8. PARTIAL APPLICATION");

        // Curried function
        Function<Integer, Function<Integer, Function<Integer, Integer>>> curry =
            a -> b -> c -> a + b + c;

        System.out.println("   Currying - Creating partial functions:");
        Function<Integer, Function<Integer, Integer>> addFive = curry.apply(5);
        Function<Integer, Integer> addFiveAndThree = addFive.apply(3);
        System.out.println("      5 + 3 + 2 = " + addFiveAndThree.apply(2));

        // Using bind pattern
        Function<Integer, Integer> add10 = x -> x + 10;
        Function<Integer, Integer> add10Then =
            add10.andThen(y -> y * 2);
        System.out.println("   Partial application: (x + 10) * 2");
        System.out.println("      (5 + 10) * 2 = " + add10Then.apply(5));
    }

    // 9. RECURSION WITH LAMBDAS
    private static void demonstrateRecursionWithLambdas() {
        System.out.println("\n9. RECURSION WITH LAMBDAS");

        // Factorial using lambda (self-referential)
        Function<Integer, Long> factorial = null;
        final Function<Integer, Long>[] factRef = new Function[1];
        factRef[0] = n -> n <= 1 ? 1 : n * factRef[0].apply(n - 1);

        System.out.println("   Factorial using recursive lambda:");
        System.out.println("      5! = " + factRef[0].apply(5));
        System.out.println("      10! = " + factRef[0].apply(10));

        // Fibonacci
        final Function<Integer, Long>[] fibRef = new Function[1];
        fibRef[0] = n -> n <= 1 ? n :
            fibRef[0].apply(n - 1) + fibRef[0].apply(n - 2);

        System.out.println("   Fibonacci using recursive lambda:");
        System.out.println("      fib(10) = " + fibRef[0].apply(10));
    }

    // 10. LAMBDA VS ANONYMOUS CLASS
    private static void demonstrateLambdaVsAnonymousClass() {
        System.out.println("\n10. LAMBDA vs ANONYMOUS CLASS");

        System.out.println("   Anonymous class (older way):");
        Runnable anonymousClass = new Runnable() {
            @Override
            public void run() {
                System.out.println("      Running from anonymous class");
            }
        };
        anonymousClass.run();

        System.out.println("   Lambda expression (newer way):");
        Runnable lambda = () -> System.out.println("      Running from lambda");
        lambda.run();

        System.out.println("   Comparator - Anonymous class:");
        Comparator<Integer> compAnon = new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        };
        System.out.println("      Compare 5 and 3: " + compAnon.compare(5, 3));

        System.out.println("   Comparator - Lambda:");
        Comparator<Integer> compLambda = (a, b) -> a - b;
        System.out.println("      Compare 5 and 3: " + compLambda.compare(5, 3));
    }

    private static List<Product> getProductList() {
        return Arrays.asList(
            new Product(1, "Laptop", 999.99, "Electronics", 5),
            new Product(2, "Mouse", 25.50, "Electronics", 50),
            new Product(3, "Keyboard", 79.99, "Electronics", 30),
            new Product(4, "Monitor", 299.99, "Electronics", 10),
            new Product(5, "Desk Chair", 199.99, "Furniture", 15)
        );
    }
}

