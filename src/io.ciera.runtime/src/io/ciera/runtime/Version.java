package io.ciera.runtime;

import java.io.IOException;
import java.util.Properties;

public class Version implements IVersioned{
    
    private static final String PROPERTIES_FILE = "io.ciera.runtime.properties";
    private Properties properties;
    
    public Version() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) { /* do nothing */ }
    }
	
	@Override
	public String getVersion() {
		return properties.getProperty("version", "Unknown");
	}

	@Override
	public String getVersionDate() {
		return properties.getProperty("version_date", "Unknown");
	}

  public static void printVersion() {
		Version version = new Version();
		System.out.printf("io.ciera.runtime: %s (%s)\n", version.getVersion(), version.getVersionDate());
  }
	
	public static void main(String[] args) {
    printVersion();
	}

}
