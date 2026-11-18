package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class TestConfig {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    public static final String STAND_URL = getRequired("stand.url");
    public static final String API_URL = getRequired("api.url");
    public static final long ELEMENT_TIMEOUT = Long.parseLong(getRequired("element.timeout"));
    public static final String LOGGING_MODE = getRequired("logging.mode");
    public static final String ADMIN_USERNAME = getRequired("admin.username");
    public static final String ADMIN_PASSWORD = getRequired("admin.password");
    public static final String START_PRODUCT_NAME = getRequired("start.product.name");
    public static final String START_PRODUCT_PRICE = getRequired("start.product.price");

    static {
        printConfig();
    }

    private TestConfig() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream input = TestConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load configuration file: " + CONFIG_FILE, e);
        }

        return properties;
    }

    private static String getRequired(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required configuration parameter: " + key);
        }

        return value.trim();
    }

    public static void printConfig() {
        System.out.println("=== Test configuration ===");
        System.out.println("stand.url = " + STAND_URL);
        System.out.println("api.url = " + API_URL);
        System.out.println("element.timeout = " + ELEMENT_TIMEOUT);
        System.out.println("logging.mode = " + LOGGING_MODE);
        System.out.println("start.product.name = " + START_PRODUCT_NAME);
        System.out.println("start.product.price = " + START_PRODUCT_PRICE);
        System.out.println("admin.credentials = [hidden]");
        System.out.println("==========================");
    }
}
