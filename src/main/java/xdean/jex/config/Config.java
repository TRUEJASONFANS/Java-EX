package xdean.jex.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Config {
  private static final Properties CONFIG = new Properties();
  private static Path configFile;

  public static void locate(Path configPath, Path defaultConfig) {
    try {
      if (Files.notExists(configPath)) {
        if (Files.exists(defaultConfig)) {
          Files.copy(defaultConfig, configPath);
        } else {
          Files.createFile(configPath);
        }
      }
      CONFIG.load(Files.newBufferedReader(configPath));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    log.debug(CONFIG.toString());
    configFile = configPath;
  }

  public static Optional<String> getProperty(String key) {
    return Optional.ofNullable(CONFIG.getProperty(key));
  }

  public static Optional<String> getProperty(Object key) {
    return getProperty(key.toString());
  }

  public static String getProperty(Object key, String defaultValue) {
    return getProperty(key.toString(), defaultValue);
  }

  public static String getProperty(String key, String defaultValue) {
    return getProperty(key).orElse(defaultValue);
  }

  public static void setProperty(Object key, String value) {
    setProperty(key.toString(), value);
  }

  public static void setProperty(String key, String value) {
    CONFIG.setProperty(key, value);
    save();
  }

  private static void save() {
    if (configFile == null) {
      return;
    }
    try {
      CONFIG.store(Files.newOutputStream(configFile), "");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}