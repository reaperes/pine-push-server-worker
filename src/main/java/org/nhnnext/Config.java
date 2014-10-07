package org.nhnnext;

import com.typesafe.config.ConfigFactory;

public class Config {
    private static final String CONFIG_FILENAME = "application.conf";

    private final com.typesafe.config.Config config = ConfigFactory.load(CONFIG_FILENAME);

    private Config() {
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