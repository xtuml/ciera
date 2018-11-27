package io.ciera.runtime.summit.util.impl;

import java.io.PrintStream;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.types.TimeStamp;
import io.ciera.runtime.summit.util.LOG;
import io.ciera.runtime.summit.util.Utility;

public class LOGImpl<C extends IComponent<C>> extends Utility<C> implements LOG {
	
	private static PrintStream out;
	private static PrintStream err;
	
    public LOGImpl(C context) {
        super(context);
        out = System.out;
        err = System.err;
    }

    @Override
    public void LogFailure(String message) {
        err.printf("ERROR: %s\n", message);
    }

    @Override
    public void LogInfo(String message) {
        out.printf("INFO: %s\n", message);
    }

    @Override
    public void LogSuccess(String message) {
        out.printf("SUCCESS: %s\n", message);
    }

    @Override
    public void LogInteger(int message) {
        out.printf("INTEGER: %d\n", message);
    }

    @Override
    public void LogReal(String message, double r) {
        out.printf("REAL: %s %f\n", message, r);
    }

    @Override
    public void LogTime(String message, TimeStamp t) {
        out.printf("TIME: %s %s\n", message, t);
    }
    
    public static void setOut(PrintStream o) {
    	out = o;
    }
    
    public static void setErr(PrintStream e) {
    	err = e;
    }

}
