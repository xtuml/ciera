package io.ciera.runtime;

public class Version implements IVersioned{
	
	private static final String VERSION = "v1.0";
	private static final String DATE = "2018-11-01";
	
	@Override
	public String getVersion() {
		return VERSION;
	}

	@Override
	public String getVersionDate() {
		return DATE;
	}

  public static void printVersion() {
		Version version = new Version();
		System.out.printf("io.ciera.runtime: %s (%s)\n", version.getVersion(), version.getVersionDate());
  }
	
	public static void main(String[] args) {
    printVersion();
	}

}
