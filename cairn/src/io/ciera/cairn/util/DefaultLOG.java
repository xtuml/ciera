package io.ciera.cairn.util;

public class DefaultLOG {
	
	public static void LogFailure( String message ) {
		System.err.printf( "ERROR: %s\n", message );
	}
	
	public static void LogInfo( String message ) {
		System.out.printf( "INFO: %s\n", message );
	}
	
	public static void LogSuccess( String message ) {
		System.out.printf( "SUCCESS: %s\n", message );
	}

}
