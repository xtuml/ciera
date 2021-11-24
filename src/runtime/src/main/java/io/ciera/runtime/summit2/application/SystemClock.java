package io.ciera.runtime.summit2.application;

import java.time.Instant;

public class SystemClock {
    
    private boolean simulatedTime;
    
    public SystemClock(boolean simulatedTime) {
        this.simulatedTime = simulatedTime;
    }

    public long getTime() {
        return 0l; // TODO
    }

    public Instant getEpoch() {
        return null;
    }
    
    public void registerTimer(Timer timer) {
        // TODO
    }

}
