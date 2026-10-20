package com.example.programs;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerExamples {
    private static final Logger LOGGER = Logger.getLogger(LoggerExamples.class.getName());

    public static void main(String[] args) {
        // Configure a dedicated console handler so we control what appears
        configureLogger(LOGGER);

        LOGGER.info("=== Java Util Logging: Levels & Methods Demo ===");

        demonstrateLevels(LOGGER);
        demonstrateParameterMessages(LOGGER);
        demonstrateExceptionLogging(LOGGER);
        demonstrateEnteringExiting(LOGGER);
        demonstrateFacade();
        demonstratePropagationVsOwnHandlers();

        LOGGER.info("=== Demo complete ===");
    }

    // Configure logger to show everything on console without duplicate parent logs
    private static void configureLogger(Logger logger) {
        logger.setUseParentHandlers(false); // avoid duplicate output from root handlers
        logger.setLevel(Level.ALL);         // allow all messages to pass the logger

        ConsoleHandler console = new ConsoleHandler();
        console.setFormatter(new SimpleFormatter());
        console.setLevel(Level.ALL);        // allow fine-grained messages on the handler
        logger.addHandler(console);
    }

    // 1) Differences between levels: which messages appear at which threshold
    private static void demonstrateLevels(Logger logger) {
        logger.info("-- Levels demonstration (logger & handler are set to ALL) --");
        logAtAllLevels(logger, "Visible because Level.ALL allows all");

        // Temporarily raise the level to INFO and show that fine-grained logs disappear
        Level original = logger.getLevel();
        logger.info("Raising logger level to INFO; FINE/FINER/FINEST/CONFIG will be filtered out");
        logger.setLevel(Level.INFO);
        logAtAllLevels(logger, "Some messages will now be filtered out");
        logger.setLevel(original);
        logger.info("Restored logger level to " + original);
    }

    private static void logAtAllLevels(Logger logger, String suffix) {
        logger.finest("FINEST: most verbose " + suffix);
        logger.finer("FINER: very detailed " + suffix);
        logger.fine("FINE: debug-level detail " + suffix);
        logger.config("CONFIG: static config/initialization details " + suffix);
        logger.info("INFO: high-level app progress " + suffix);
        logger.warning("WARNING: something unexpected but non-fatal " + suffix);
        logger.severe("SEVERE: serious problem " + suffix);
    }

    // 2) Parameterized messages (MessageFormat style: {0}, {1}, ...)
    private static void demonstrateParameterMessages(Logger logger) {
        logger.info("-- Parameterized messages demonstration --");
        Object[] params = {"alice", "127.0.0.1", 3};
        logger.log(Level.INFO, "User {0} logged in from {1}; attempts={2}", params);

        // Late-evaluated message only if level enabled (using lambda available since Java 8)
        logger.log(Level.FINE, () -> "FINE: Expensive to compute details: sample=" + expensiveSample());
    }

    private static String expensiveSample() {
        // Pretend this is expensive; here we just generate an array and stringify it
        int[] data = new Random().ints(5, 0, 100).toArray();
        return Arrays.toString(data);
    }

    // 3) Exception logging with stack traces
    private static void demonstrateExceptionLogging(Logger logger) {
        logger.info("-- Exception logging demonstration --");
        try {
            int x = 10 / maybeZero();
            logger.info("Computation result: " + x);
        } catch (ArithmeticException ex) {
            logger.log(Level.SEVERE, "Computation failed due to invalid divisor", ex);
        }
    }

    private static int maybeZero() {
        return new Random().nextBoolean() ? 0 : 2;
    }

    // 4) Entering/Exiting logs (helpful for tracing; logged at FINER by default)
    private static void demonstrateEnteringExiting(Logger logger) {
        logger.info("-- Entering/Exiting demonstration (requires FINER to be visible) --");
        Level original = logger.getLevel();
        logger.setLevel(Level.ALL);

        logger.entering(LoggerExamples.class.getName(), "computeSum", new Object[]{5, 7});
        int sum = computeSum(5, 7);
        logger.exiting(LoggerExamples.class.getName(), "computeSum", sum);

        // Also demonstrate throwing()
        try {
            mightThrow(true);
        } catch (IllegalStateException ex) {
            logger.throwing(LoggerExamples.class.getName(), "mightThrow", ex);
        }

        logger.setLevel(original);
    }

    private static int computeSum(int a, int b) {
        return a + b;
    }

    private static void mightThrow(boolean trigger) {
        if (trigger) throw new IllegalStateException("Triggered for demo");
    }

    // 5) Handler propagation vs. child logger having its own handler
    private static void demonstratePropagationVsOwnHandlers() {
        LOGGER.info("-- Propagation vs. own handlers --");
        Logger parent = Logger.getLogger("com.example");
        parent.setUseParentHandlers(false);
        parent.setLevel(Level.ALL);
        ConsoleHandler parentHandler = new ConsoleHandler();
        parentHandler.setLevel(Level.ALL);
        parentHandler.setFormatter(new SimpleFormatter());
        parent.addHandler(parentHandler);

        Logger child = Logger.getLogger("com.example.programs.child");
        child.setLevel(Level.ALL);

        // Case A: use parent handlers (messages go to parent's console)
        child.setUseParentHandlers(true);
        child.info("Child logger (A) uses parent handlers");

        // Case B: disable propagation and add its own handler
        child.setUseParentHandlers(false);
        ConsoleHandler own = new ConsoleHandler();
        own.setLevel(Level.ALL);
        own.setFormatter(new SimpleFormatter());
        child.addHandler(own);
        child.warning("Child logger (B) with its own handler only");
    }

    // 6) Facade demo: debug/info/warn methods similar to SLF4J/Log4j naming
    private static void demonstrateFacade() {
        LoggerFacade log = LoggerFacade.getLogger(LoggerExamples.class);
        LOGGER.info("-- Facade (debug/info/warn) demonstration --");

        log.debug("Debug details: a={0}, b={1}", 10, 20);
        log.info("App started for user {0}", "alice");
        log.warn("Low disk space: {0} MB remaining", 512);

        try {
            int x = 5 / 0;
            log.debug("Result = {0}", x);
        } catch (ArithmeticException ex) {
            log.warn("Handled divide by zero", ex);
        }
    }
}