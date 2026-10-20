package com.example.programs;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple facade exposing debug/info/warn methods backed by java.util.logging.
 */
public class LoggerFacade {
    private final Logger logger;

    private LoggerFacade(Class<?> cls) {
        this.logger = Logger.getLogger(cls.getName());
    }

    public static LoggerFacade getLogger(Class<?> cls) {
        return new LoggerFacade(cls);
    }

    // debug
    public void debug(String msg) {
        logger.fine(msg);
    }

    public void debug(String pattern, Object... args) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(MessageFormat.format(pattern, args));
        }
    }

    public void debug(String msg, Throwable t) {
        logger.log(Level.FINE, msg, t);
    }

    // info
    public void info(String msg) {
        logger.info(msg);
    }

    public void info(String pattern, Object... args) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(MessageFormat.format(pattern, args));
        }
    }

    // warn
    public void warn(String msg) {
        logger.warning(msg);
    }

    public void warn(String pattern, Object... args) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.warning(MessageFormat.format(pattern, args));
        }
    }

    public void warn(String msg, Throwable t) {
        logger.log(Level.WARNING, msg, t);
    }
}
