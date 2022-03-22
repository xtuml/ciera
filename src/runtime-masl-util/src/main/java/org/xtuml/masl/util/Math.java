package org.xtuml.masl.util;

public class Math {

    public Math(final Object domain) {
        System.loadLibrary("Math_interface");
    }
    
    public native double sqrt(final double v);

}