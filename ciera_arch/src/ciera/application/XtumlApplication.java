package ciera.application;

public abstract class XtumlApplication {
    
    public static XtumlApplication app;
    
    private ApplicationThread[] threadPool;
    
    public abstract void setup();
    
    public void start() {
        for ( ApplicationThread thread : threadPool ) {
            thread.start();
        }
    }

    public void stop() {
        for ( ApplicationThread thread : threadPool ) {
            thread.terminate();
        }
    }
    
    public void createThreadPool( int size ) {
        threadPool = new ApplicationThread[size];
        for ( int i = 0; i < size; i++ ) {
            threadPool[i] = new ApplicationThread();
        }
    }
    
    public ApplicationThread getThreadFromPool( int index ) {
        return threadPool[index];
    }
    
    public ApplicationThread getDefaultThread( Class<?> object ) {
        for ( ApplicationThread thread : threadPool ) {
            if ( thread.defaultFor( object ) ) return thread;
        }
        return null;
    }

}
