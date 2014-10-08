package org.nhnnext;

import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
    private static final Logger logger = LogManager.getLogger(Config.class.getName());

    private static final String CONFIG_FILENAME = "application.conf";

    private final com.typesafe.config.Config config;

    private Config() {
        logger.info("Loading " + CONFIG_FILENAME);
        config = ConfigFactory.load(CONFIG_FILENAME);
    }

    private static class LazyHolder {
        private static final Config instance = new Config();
    }

    public static Config getInstance() {
        return LazyHolder.instance;
    }

    public String getString(String key) {
        return config.getString(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }
}