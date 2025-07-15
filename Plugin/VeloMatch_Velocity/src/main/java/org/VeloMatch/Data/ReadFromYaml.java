package org.VeloMatch.Data;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ReadFromYaml {

    private static final Path CONFIG_PATH = Paths.get("plugins/velomatch/config.yml");

    // Read from the config.yml easily and fast

    public static String YamlRead(String key) {
        try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(in);

            Object value = data.get(key);
            if (value != null) {
                return value.toString();
            } else {
                throw new RuntimeException("Key '" + key + "' not found in config.yml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading config.yml, make sure that the file exist and is not corrupted, more info about the error: ", e);
        }
    }



    /*
    public static boolean YamlReadBoolean(String key) {
        try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(in);

            Object value = data.get(key);
            if (value != null) {
                if (value instanceof Boolean) {
                    return (Boolean) value;
                } else {
                    return Boolean.parseBoolean(value.toString());
                }
            } else {
                throw new RuntimeException("Key '" + key + "' not found in config.yml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error reading config.yml, make sure that the file exists and is not corrupted. More info: ", e);
        }
    }
    */
}
