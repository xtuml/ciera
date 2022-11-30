package io.ciera.runtime.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LOG {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public LOG(Object domain) {}

  public void LogFailure(final String message) {
    logger.error(message);
  }

  public void LogInfo(final String message) {
    logger.info(message);
  }

  public void LogSuccess(final String message) {
    logger.info(message);
  }

  public void LogInteger(final int message) {
    logger.info("%d", message);
  }

  public void LogReal(final String message, final double r) {
    logger.info("%s %f", message, r);
  }

  public void LogTime(final String message, final long t) {
    logger.info("%s %d", message, t);
  }
}
