package io.ciera.runtime.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Architecture {

  private static final Architecture INSTANCE = new Architecture();

  public static final UUID NULL_ID = new UUID(0, 0);

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private boolean isShutdown = false;

  @ArchService private IdAssigner idAssigner = UUID::randomUUID;
  @ArchService private SystemClock clock = Instant::now;

  private Architecture() {}

  public Supplier<UUID> getIdAssigner() {
    return idAssigner;
  }

  public SystemClock getClock() {
    return clock;
  }

  public void loadConfig(Path propertiesFile) {
    // load properties
    final Properties props = new Properties();
    try (final InputStream is = new FileInputStream(propertiesFile.toFile())) {
      props.load(is);
    } catch (IOException e) {
      logger.error("Failed to load properties file");
      e.printStackTrace();
    }
    Stream.of(getClass().getDeclaredFields())
        .filter(f -> f.getAnnotation(ArchService.class) != null)
        .forEach(
            f -> {
              Object service =
                  ServiceLoader.load(f.getType()).stream()
                      .filter(
                          p ->
                              p.type()
                                  .getName()
                                  .equals(props.getProperty(f.getType().getName(), "")))
                      .map(ServiceLoader.Provider::get)
                      .findAny()
                      .orElse(null);
              if (service != null) {
                try {
                  f.set(this, service);
                  logger.debug(
                      "ARCH: {} supplied by {}",
                      f.getType().getName(),
                      service.getClass().getName());
                } catch (IllegalArgumentException | IllegalAccessException e) {
                  logger.error("Failed to configure architecture service");
                  e.printStackTrace();
                }
              }
            });
  }

  public void shutdown() {
    isShutdown = true;
  }

  public boolean isShutdown() {
    return isShutdown;
  }

  public static Architecture getInstance() {
    return INSTANCE;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  private @interface ArchService {}
}
