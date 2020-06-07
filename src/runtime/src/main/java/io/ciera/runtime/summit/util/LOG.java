package io.ciera.runtime.summit.util;

/**
 * Standard logging interface.
 */
public interface LOG {

    /**
     * Log a failure message.
     *
     * @param message the message to log
     */
    public void LogFailure(final String message);

    /**
     * Log an information message.
     *
     * @param message the message to log
     */
    public void LogInfo(final String message);

    /**
     * Log a success message.
     *
     * @param message the message to log
     */
    public void LogSuccess(final String message);

    /**
     * Log an integer message.
     *
     * @param message the integer to log
     */
    public void LogInteger(final int message);

    /**
     * Log an real number message.
     *
     * @param message the text to log
     * @param r the real number to log
     */
    public void LogReal(final String message, final double r);

    /**
     * Log an timestamp message.
     *
     * @param message the text to log
     * @param t the timestamp to log
     */
    public void LogTime(final String message, final long t);

}
