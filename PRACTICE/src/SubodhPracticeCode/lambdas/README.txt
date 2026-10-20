Lambda Expressions - Comprehensive Learning Guide
==================================================

This package contains four comprehensive programs demonstrating Java Lambda Expressions in detail.

OVERVIEW
--------
Lambda Expressions (introduced in Java 8) provide a concise way to implement functional interfaces
using a clear, readable syntax. They enable functional programming paradigms and work seamlessly
with the Stream API.

FILES
-----
1. LambdaExpressionsDemo.java
   - Basic Lambda syntax and concepts with Employee data
   - Demonstrates: basic syntax, predicates, functions, consumers, comparators
   - Includes: runnable, callable, custom functional interfaces, method references

2. AdvancedLambdaExpressionsDemo.java
   - Advanced Lambda patterns and techniques with Product data
   - Demonstrates: built-in functional interfaces, composing lambdas, stateful lambdas
   - Includes: exception handling, higher-order functions, functional composition
   - Shows: partial application, recursion, lambda vs anonymous classes

3. PracticalLambdaExamples.java
   - Real-world use cases with Student data
   - Demonstrates: event handling, data filtering, transformation, sorting, validation
   - Includes: callback functions, statistics computation, thread management
   - Shows: functional chaining, real-world scenarios

4. LambdaExpressionsCheatSheet.java
   - Quick reference for common Lambda patterns
   - 10 Quick examples covering syntax, interfaces, references, and operations
   - Perfect for quick lookup and learning

CORE CONCEPTS COVERED
---------------------

1. LAMBDA SYNTAX
   - No parameters: () -> expression
   - Single parameter: x -> expression
   - Multiple parameters: (x, y) -> expression
   - Single expression or block body with return

2. FUNCTIONAL INTERFACES (Single Abstract Method)
   - Predicate<T>: test(T) returns boolean
   - Function<T, R>: apply(T) returns R
   - BiFunction<T, U, R>: apply(T, U) returns R
   - Consumer<T>: accept(T) returns void
   - Supplier<T>: get() returns T
   - BinaryOperator<T>: apply(T, T) returns T
   - UnaryOperator<T>: apply(T) returns T

3. CUSTOM FUNCTIONAL INTERFACES
   - @FunctionalInterface annotation
   - Single abstract method requirement
   - Default methods allowed
   - Static methods allowed

4. METHOD REFERENCES
   - Static method: ClassName::staticMethod
   - Instance method: instance::method
   - Type method: Type::method
   - Constructor: ClassName::new

5. LAMBDA WITH STREAMS
   - Filter: stream.filter(lambda)
   - Map: stream.map(lambda)
   - Reduce: stream.reduce(lambda)
   - ForEach: stream.forEach(lambda)
   - Sorted: stream.sorted(lambda)

6. FUNCTION COMPOSITION
   - andThen(): Function composition
   - compose(): Reverse composition
   - and(): Predicate AND logic
   - or(): Predicate OR logic
   - negate(): Predicate negation

7. VARIABLE CAPTURE
   - Effectively final variables
   - Captured variables in lambdas
   - Scope and visibility
   - Memory implications

8. ADVANCED PATTERNS
   - Higher-order functions (function returning function)
   - Partial application and currying
   - Recursion with lambdas
   - Callback functions
   - Event handling

EXECUTION
---------
To run the demonstrations:

1. Compile:
   javac -d bin src/com/example/lambdas/*.java

2. Run each demo:
   java -cp bin com.example.lambdas.LambdaExpressionsDemo
   java -cp bin com.example.lambdas.AdvancedLambdaExpressionsDemo
   java -cp bin com.example.lambdas.PracticalLambdaExamples
   java -cp bin com.example.lambdas.LambdaExpressionsCheatSheet

KEY FEATURES OF LAMBDA EXPRESSIONS
-----------------------------------
• Concise syntax for implementing functional interfaces
• Enable functional programming style in Java
• Improve code readability and maintainability
• Work seamlessly with Stream API
• Support function composition
• Enable callback patterns
• Support variable capture
• Type inference for parameters

BEST PRACTICES
--------------
1. Use lambda expressions for functional interfaces
2. Keep lambda expressions short and simple
3. Extract complex logic into separate methods
4. Use method references when applicable
5. Capture only effectively final variables
6. Avoid modifying captured variables
7. Use appropriate functional interface for the task
8. Prefer lambdas over anonymous classes

COMMON PATTERNS
---------------

Filter and Process:
  collection.stream()
    .filter(item -> condition)
    .forEach(item -> action)

Transform Data:
  collection.stream()
    .map(item -> transformation)
    .collect(Collectors.toList())

Filter, Map, Reduce:
  collection.stream()
    .filter(condition)
    .map(transformation)
    .reduce(identity, aggregation)

Sort with Lambda:
  list.sort((a, b) -> compareLogic)

Event Handling:
  button.setOnClick(event -> handleClick())

Callback Pattern:
  executeAsync(result -> processResult(result))

Function Composition:
  function1.andThen(function2).andThen(function3)

Predicate Composition:
  predicate1.and(predicate2).or(predicate3)

COMPARISON: LAMBDA vs ANONYMOUS CLASS
--------------------------------------

Anonymous Class:
  button.setOnClick(new ClickListener() {
      @Override
      public void onClick(Event e) {
          handleClick();
      }
  });

Lambda Expression:
  button.setOnClick(event -> handleClick());

Benefits:
  • More concise and readable
  • Less boilerplate code
  • Type inference
  • Captures variables efficiently

LAMBDA vs STREAM API
--------------------
Lambda expressions and Stream API work together:
  • Lambdas provide the operations
  • Streams provide the data pipeline
  • Combine for powerful, functional data processing
  • Prefer declarative style over imperative loops

PERFORMANCE CONSIDERATIONS
--------------------------
• Lambdas have minimal overhead
• Method references may be slightly faster
• Avoid creating objects unnecessarily
• Use parallelStream() for large datasets only
• Variable capture is efficient
• Short-circuit operations improve performance

