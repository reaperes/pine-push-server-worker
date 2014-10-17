package org.nhnnext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * This class handle configuration.
 */
public class Config {
    private static final Logger logger = LogManager.getLogger(Config.class.getName());

    public static String ENV;

    public static String UNIQUSH_HOST = "localhost";
    public static int    UNIQUSH_PORT = 9898;
    public static String UNIQUSH_SERVICE = "pine";

    public static String RABBITMQ_HOST = "localhost";
    public static int    RABBITMQ_PORT = 5672;
    public static String RABBITMQ_QUEUE = "pine";

    static {
        loadEnvIfNotExit();
        setEnvironments();
    }

    private static void loadEnvIfNotExit() {
        String env;
        try {
            env = System.getenv("PUSH_SERVER_WORKER_ENV").toLowerCase();
            switch (env) {
                case "local":
                case "dev":
                case "production":
                    ENV = env;
                    break;

                case "":
                default:
                    ENV = "error";
                    throw new RuntimeException("PUSH_SERVER_WORKER_ENV environment must set one of (local, dev, production)");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("PUSH_SERVER_WORKER_ENV environment must set one of (local, dev, production)");
        }
        logger.info("Config set environment : " + ENV);
    }

    private static void setEnvironments() {
        Map envMap = System.getenv();
        if (envMap.containsKey("UNIQUSH_HOST")) UNIQUSH_HOST = (String) envMap.get("UNIQUSH_HOST");
        if (envMap.containsKey("UNIQUSH_PORT")) UNIQUSH_PORT = Integer.parseInt((String) envMap.get("UNIQUSH_PORT"));
        if (envMap.containsKey("UNIQUSH_SERVICE")) UNIQUSH_SERVICE = (String) envMap.get("UNIQUSH_SERVICE");
        if (envMap.containsKey("RABBITMQ_HOST")) RABBITMQ_HOST = (String) envMap.get("RABBITMQ_HOST");
        if (envMap.containsKey("RABBITMQ_PORT")) RABBITMQ_PORT = Integer.parseInt((String) envMap.get("RABBITMQ_PORT"));
        if (envMap.containsKey("RABBITMQ_QUEUE")) RABBITMQ_QUEUE = (String) envMap.get("RABBITMQ_QUEUE");
    }

    private Config() {
    }
}