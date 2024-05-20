package io.ciera.runtime.summit.util;

/**
 * Provides a hook into the xtUML architecutre.
 */
public interface ARCH {

    /**
     * Cause the application to exit.
     */
    public void shutdown();

    /**
     * Exit the process immediately with the specified code
     * 
     * @param code The exit code
     */
    public void exit(int code);
    
    /**
     * Open a URL in the default browser (not supported)
     * 
     * @param url The url
     */
    public void openURL(String url);

}
