package io.ciera.runtime.summit.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PerformanceUtil {
    
    private static PerformanceUtil instance = null;

    private PerformanceUtil() {
        durations = new long[OTHER+1];
        for ( int i = 0; i < durations.length; i++ ) durations[i] = 0;
        counts = new int[OTHER+1];
        for ( int i = 0; i < counts.length; i++ ) counts[i] = 0;
        currentStartTime = 0;
        currentStatementType = NULL;
        callStack = new Stack<>();
        callTime = new Stack<>();
        callDurations = new HashMap<>();
        callCounts = new HashMap<>();
    }

    public static PerformanceUtil getInstance() {
        if ( null == instance ) instance = new PerformanceUtil();
        return instance;
    }
    
    public static final int NULL            = -1;
    public static final int BRIDGE          =  0;
    public static final int FUNCTION        =  1;
    public static final int OPERATION       =  2;
    public static final int SIGNAL          =  3;
    public static final int IF              =  4;
    public static final int WHILE           =  5;
    public static final int BREAK           =  6;
    public static final int CONTINUE        =  7;
    public static final int ASSIGN          =  8;
    public static final int FOR             =  9;
    public static final int SELECT_FIO      = 10;
    public static final int SELECT_FIW      = 11;
    public static final int SELECT_REL      = 12;
    public static final int CREATE          = 13;
    public static final int CREATE_NV       = 14;
    public static final int CONTROL         = 15;
    public static final int DELETE          = 16;
    public static final int RETURN          = 17;
    public static final int RELATE          = 18;
    public static final int RELATE_USING    = 19;
    public static final int UNRELATE        = 20;
    public static final int UNRELATE_USING  = 21;
    public static final int OTHER           = 22;
    
    private long[] durations;
    private int[] counts;
    
    private long currentStartTime;
    private int currentStatementType;
    
    private Stack<String> callStack;
    private Stack<Long> callTime;
    private Map<String,Long> callDurations;
    private Map<String,Integer> callCounts;
    
    private void internalMark( int type ) {
        if ( currentStatementType > NULL && currentStatementType <= OTHER ) {
            durations[currentStatementType] += System.currentTimeMillis() - currentStartTime;
            counts[currentStatementType]++;
        }
        if ( type > NULL && type <= OTHER ) {
            currentStartTime = System.currentTimeMillis();
            currentStatementType = type;
        }
    }
    
    public static void mark( int type ) {
        getInstance().internalMark(type);
    }
    
    private void internalEnter( String name ) {
        if ( null != name && !"".equals(name) ) {
            callStack.push(name);
            callTime.push(System.currentTimeMillis());
        }
    }
    
    private void internalExit() {
        if ( !callStack.isEmpty() ) {
            String name = callStack.pop();
            long duration = System.currentTimeMillis() - callTime.pop();
            if ( callDurations.containsKey(name) ) callDurations.put( name, callDurations.get(name) + duration );
            else callDurations.put( name, duration );
            if ( callCounts.containsKey(name) ) callCounts.put( name, callCounts.get(name) + 1 );
            else callCounts.put( name, 1 );
        }
    }
    
    public static void enter( String name ) {
        getInstance().internalEnter(name);
    }
    
    public static void exit() {
        getInstance().internalExit();
    }

    public static void generateReport() {
        PerformanceUtil self = getInstance();
        Field[] fields = PerformanceUtil.class.getFields();
        List<Field> constants = new ArrayList<>(OTHER+1);
        for ( Field field : fields ) {
            if ( Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) ) {
                try {
                    if ( -1 != field.getInt(null) ) constants.add(field);
                } catch ( Exception e ) {/* do nothing */}
            }
        }
        constants.sort( (f1, f2) -> {
            try {
                return f1.getInt(null) - f2.getInt(null);
            } catch ( Exception e ) {
                return 0;
            }
        });
        System.out.println( "|----------------------------------------------------------------------------------------------|" );
        for ( Field constant : constants ) {
            int constantValue = NULL;
            String constantName = "";
            try {
                constantValue = constant.getInt(null);
                constantName = constant.getName();
            } catch ( Exception e ) {/* do nothing */}
            if ( constantValue > NULL && constantValue <= OTHER ) {
                System.out.printf( " %-14s : occurances: %10d, total time: %10dms, average time: %10dms\n",
                                   constantName, self.counts[constantValue], self.durations[constantValue],
                                   self.counts[constantValue] == 0 ? 0 : self.durations[constantValue] / self.counts[constantValue] );
            }
        }
        System.out.println( "|----------------------------------------------------------------------------------------------|" );
        for ( String name : self.callDurations.keySet() ) {
            long duration = self.callDurations.get(name);
            int count = self.callCounts.get(name);
            System.out.printf( " %-75s : occurances: %10d, total time: %10dms, average time: %10dms\n",
                               name, count, duration, count == 0 ? 0 : duration / count );
        }
        System.out.println( "|----------------------------------------------------------------------------------------------|" );
        if ( !self.callStack.isEmpty() ) {
            System.err.printf( "WARNING: call stack is not empty. %d items remain.\n", self.callStack.size() );
            while ( !self.callStack.isEmpty() ) {
                System.err.println( self.callStack.pop() );
            }
        }
    }

}
