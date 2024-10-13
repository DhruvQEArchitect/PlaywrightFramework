package com.bookstore.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LOGGER {

    private static Logger LOG = LogManager.getLogger(LOGGER.class.getName());

    public static void INFO(String message) {
        LOG.info(message);
    }

    public static void ERROR(String message) {
        LOG.error(message);
    }

    public static void WARNING(String message) {
        LOG.warn(message);
    }

}
