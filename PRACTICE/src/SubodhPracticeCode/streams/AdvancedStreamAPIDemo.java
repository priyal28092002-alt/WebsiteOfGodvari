package com.example.streams;

import java.util.*;
import java.util.stream.*;

public class AdvancedStreamAPIDemo {

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

        System.out.println("========== ADVANCED STREAM API CONCEPTS ==========\n");

        demonstratePeek(products);
        demonstrateChaining(products);
        demonstrateCollectingAndSummarizing(products);
        demonstrateFindOperations(products);
        demonstrateCustomCollectors(products);
        demonstrateStringStreams();
        demonstrateIntermediateVsTerminal(products);
        demonstrateMapToInt(products);
        demonstrateOptional();
    }

    // 1. PEEK: Debug operation to see intermediate values
    private static void demonstratePeek(List<Product> products) {
        System.out.println("1. PEEK - Debug stream operations");
        products.stream()
            .filter(p -> p.getPrice() > 100)
            .peek(p -> System.out.println("   After filter: " + p.getName()))
            .map(Product::getName)
            .peek(name -> System.out.println("   After map: " + name))
            .limit(2)
            .forEach(name -> System.out.println("   Final: " + name));
    }

    // 2. METHOD CHAINING: Complex operations combined
    private static void demonstrateChaining(List<Product> products) {
        System.out.println("\n2. METHOD CHAINING - Complex pipeline");
        products.stream()
            .filter(p -> p.getStock() > 5)
            .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
            .map(Product::getName)
            .limit(3)
            .forEach(name -> System.out.println("   " + name));
    }

    // 3. COLLECTING AND SUMMARIZING
    private static void demonstrateCollectingAndSummarizing(List<Product> products) {
        System.out.println("\n3. COLLECTING & SUMMARIZING");

        // Count
        long totalProducts = products.stream().count();
        System.out.println("   Total products: " + totalProducts);

        // Sum
        double totalValue = products.stream()
            .collect(Collectors.summingDouble(p -> p.getPrice() * p.getStock()));
        System.out.println("   Total inventory value: $" + totalValue);

        // Average
        double avgPrice = products.stream()
            .collect(Collectors.averagingDouble(Product::getPrice));
        System.out.println("   Average price: $" + avgPrice);

        // Statistics
        DoubleSummaryStatistics stats = products.stream()
            .collect(Collectors.summarizingDouble(Product::getPrice));
        System.out.println("   Price statistics:");
        System.out.println("      Min: $" + stats.getMin());
        System.out.println("      Max: $" + stats.getMax());
        System.out.println("      Avg: $" + stats.getAverage());
        System.out.println("      Count: " + stats.getCount());

        // GroupingBy with counting
        Map<String, Long> countByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.counting()
            ));
        System.out.println("   Products by category: " + countByCategory);

        // GroupingBy with mapping
        Map<String, List<String>> namesByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.mapping(Product::getName, Collectors.toList())
            ));
        System.out.println("   Names by category: " + namesByCategory);
    }

    // 4. FIND OPERATIONS: findFirst, findAny
    private static void demonstrateFindOperations(List<Product> products) {
        System.out.println("\n4. FIND OPERATIONS");

        Optional<Product> firstExpensive = products.stream()
            .filter(p -> p.getPrice() > 200)
            .findFirst();
        System.out.println("   First product > $200: " +
            firstExpensive.map(Product::getName).orElse("Not found"));

        Optional<Product> anyInStock = products.stream()
            .filter(p -> p.getStock() > 50)
            .findAny();
        System.out.println("   Any product with stock > 50: " +
            anyInStock.map(Product::getName).orElse("Not found"));

        // Using ifPresentOrElse (Java 9+)
        products.stream()
            .filter(p -> p.getCategory().equals("Electronics"))
            .findFirst()
            .ifPresentOrElse(
                p -> System.out.println("   Found Electronics: " + p.getName()),
                () -> System.out.println("   No Electronics found")
            );
    }

    // 5. CUSTOM COLLECTORS
    private static void demonstrateCustomCollectors(List<Product> products) {
        System.out.println("\n5. CUSTOM COLLECTORS");

        // Collect to custom list
        LinkedList<String> linkedNames = products.stream()
            .map(Product::getName)
            .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("   As LinkedList: " + linkedNames);

        // Collect with downstream operations
        Map<String, DoubleSummaryStatistics> pricesByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.summarizingDouble(Product::getPrice)
            ));
        System.out.println("   Price stats by category:");
        pricesByCategory.forEach((cat, stats) ->
            System.out.println("      " + cat + ": $" + String.format("%.2f", stats.getAverage()))
        );

        // Joining with conditions
        String expensiveProducts = products.stream()
            .filter(p -> p.getPrice() > 150)
            .map(Product::getName)
            .collect(Collectors.joining(", ", "Expensive: [", "]"));
        System.out.println("   " + expensiveProducts);
    }

    // 6. STRING STREAMS
    private static void demonstrateStringStreams() {
        System.out.println("\n6. STRING STREAMS");

        String text = "Stream API is powerful for functional programming";

        System.out.println("   Character count:");
        long charCount = text.chars().count();
        System.out.println("      Total: " + charCount);

        System.out.println("   Unique characters:");
        text.chars()
            .distinct()
            .sorted()
            .mapToObj(c -> (char) c)
            .forEach(System.out::print);
        System.out.println();

        System.out.println("   Words as stream:");
        String sentence = "Java Stream API demo";
        Arrays.stream(sentence.split(" "))
            .sorted()
            .forEach(word -> System.out.println("      " + word));
    }

    // 7. INTERMEDIATE vs TERMINAL OPERATIONS
    private static void demonstrateIntermediateVsTerminal(List<Product> products) {
        System.out.println("\n7. INTERMEDIATE vs TERMINAL OPERATIONS");

        System.out.println("   Lazy evaluation demo:");
        products.stream()
            .filter(p -> {
                System.out.print("      [filter] ");
                return p.getPrice() > 100;
            })
            .map(p -> {
                System.out.print("[map] ");
                return p.getName();
            })
            .limit(2)
            .forEach(name -> System.out.println("[terminal] " + name));

        System.out.println("\n   Notice: Operations only execute until terminal operation.");
        System.out.println("   limit(2) stops processing after 2 results.");
    }

    // 8. MAP TO INT (Primitive streams)
    private static void demonstrateMapToInt(List<Product> products) {
        System.out.println("\n8. PRIMITIVE STREAMS (mapToInt, mapToDouble, mapToLong)");

        System.out.println("   Total stock (mapToInt):");
        int totalStock = products.stream()
            .mapToInt(Product::getStock)
            .sum();
        System.out.println("      Total: " + totalStock + " units");

        System.out.println("   Stock statistics:");
        IntSummaryStatistics stats = products.stream()
            .mapToInt(Product::getStock)
            .summaryStatistics();
        System.out.println("      Min stock: " + stats.getMin());
        System.out.println("      Max stock: " + stats.getMax());
        System.out.println("      Avg stock: " + String.format("%.2f", stats.getAverage()));

        System.out.println("   Double stream - prices:");
        double totalPrice = products.stream()
            .mapToDouble(Product::getPrice)
            .sum();
        System.out.println("      Total: $" + totalPrice);
    }

    // 9. OPTIONAL - Handling null safely
    private static void demonstrateOptional() {
        System.out.println("\n9. OPTIONAL - Null-safe operations");

        Optional<String> opt1 = Optional.of("Present value");
        System.out.println("   Present: " + opt1.get());

        Optional<String> opt2 = Optional.empty();
        System.out.println("   Empty with default: " + opt2.orElse("Default"));

        Optional<String> opt3 = Optional.of("Test");
        opt3.ifPresent(val -> System.out.println("   Value present: " + val));

        Optional<String> opt4 = Optional.empty();
        System.out.println("   Empty or else get: " + opt4.orElseGet(() -> "Generated default"));

        Optional<String> opt5 = Optional.of("Stream API");
        Optional<Integer> length = opt5.map(String::length);
        System.out.println("   Length of 'Stream API': " + length.get());

        Optional<String> opt6 = Optional.empty();
        try {
            opt6.orElseThrow(() -> new IllegalStateException("Value not found"));
        } catch (IllegalStateException e) {
            System.out.println("   Caught exception: " + e.getMessage());
        }
    }

    private static List<Product> getProductList() {
        return Arrays.asList(
            new Product(1, "Laptop", 999.99, "Electronics", 5),
            new Product(2, "Mouse", 25.50, "Electronics", 50),
            new Product(3, "Keyboard", 79.99, "Electronics", 30),
            new Product(4, "Monitor", 299.99, "Electronics", 10),
            new Product(5, "Desk Chair", 199.99, "Furniture", 15),
            new Product(6, "Desk Lamp", 45.00, "Furniture", 25),
            new Product(7, "Standing Desk", 599.99, "Furniture", 3),
            new Product(8, "Notebook", 5.99, "Stationery", 100),
            new Product(9, "Pen Set", 12.50, "Stationery", 200)
        );
    }
}

