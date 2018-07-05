package io.ciera.cairn.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import io.ciera.cairn.application.tasks.HaltExecutionTask;
import io.ciera.summit.application.IApplicationTask;
import io.ciera.summit.application.IExceptionHandler;
import io.ciera.summit.application.IRunContext;
import io.ciera.summit.exceptions.BadArgumentException;
import io.ciera.summit.exceptions.XtumlException;

public class ApplicationExecutor extends Thread implements IRunContext {

    private IExceptionHandler handler;
    private BlockingQueue<IApplicationTask> tasks;
    private boolean running;
    
    private String[] args;
    
    private Stack<Map<String, Object>> symbols;

    public ApplicationExecutor( String name ) {
        this( name, new String[0] );
    }

    public ApplicationExecutor( String name, String[] args ) {
        super( name );
        handler = new DefaultExceptionHandler();
        tasks = new PriorityBlockingQueue<>();
        running = false;
        symbols = new Stack<>();
        this.args = args;
    }

    @Override
    public void execute( IApplicationTask task ) {
        tasks.add( task );
    }

    @Override
    public void run() {
        running = true;
        while ( running ) {
            try {
                IApplicationTask task = tasks.take();
                if ( task instanceof HaltExecutionTask ) {
                    running = false;
                }
                else {
                    try {
                      task.run();
                    }
                    catch ( XtumlException e ) {
                      handler.handle( e );
                    }
                }
            }
            catch ( InterruptedException e ) {}
        }
    }

    @Override
    public IExceptionHandler getExceptionHandler() {
        return handler;
    }

    @Override
    public void setExceptionHandler( IExceptionHandler h ) {
        if ( null != h ) handler = h;
    }

    @Override
    public Object setSymbol( String name, Object value ) {
        Map<String,Object> symbolTable = symbols.peek();
        symbolTable.put( name, value );
        return value;
    }

    @Override
    public Object getSymbol( String name ) throws XtumlException {
        Map<String,Object> symbolTable = symbols.peek();
        if ( symbolTable.containsKey( name ) ) return symbolTable.get( name );
        else throw new BadArgumentException( "Access of undeclared variable." );
    }

    @Override
    public void pushSymbolTable() {
        symbols.push( new HashMap<>() );
    }

    @Override
    public void popSymbolTable() {
        symbols.pop();
    }

    @Override
    public String[] args() {
        return args;
    }

}
