package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;
    private static ConfigReader configReader;

    private ConfigReader() {
        String propertyFilePath = "src/main/java/config/configuration.properties";
        properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath))) {
            properties.load(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Configuration properties are not found at " + propertyFilePath, e.fillInStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigReader getInstance( ) {
        if(configReader == null) {
            configReader = new ConfigReader();
        }
        return configReader;
    }

    public String getProperty(String property) {
        String baseUrl = properties.getProperty(property);
        if(baseUrl != null) return baseUrl;
        else throw new RuntimeException("" + property + "is not specified in the configuration properties file.");
    }


}
