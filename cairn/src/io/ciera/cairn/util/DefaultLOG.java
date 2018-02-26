package io.ciera.cairn.util;

import io.ciera.summit.components.IComponent;

public class DefaultLOG {
	
	public static void LogFailure( IComponent context, String message ) {
		System.err.printf( "ERROR: %s\n", message );
	}
	
	public static void LogInfo( IComponent context, String message ) {
		System.out.printf( "INFO: %s\n", message );
	}
	
	public static void LogSuccess( IComponent context, String message ) {
		System.out.printf( "SUCCESS: %s\n", message );
	}

}
