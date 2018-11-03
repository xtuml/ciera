package io.ciera.runtime.summit.util;

public class ProgressBar {

    private final Thread displayThread;
    private final int count;
    private int done;
    private int lastPercentage;

    public ProgressBar() {
        this(1);
    }

    public ProgressBar(int count) {
        this(count, 100);
    }

    public ProgressBar(int count, long refreshRate) {
        this.count = count > 0 ? count : 1;
        done = 0;
        lastPercentage = 0;
        displayThread = new Thread() {
            @Override
            public void run() {
                int done;
                do {
                    synchronized (ProgressBar.this) {
                        done = ProgressBar.this.done;
                    }
                    char[] progressBar = new char[50];
                    char lineEnder;
                    int i, percentage;
                    percentage = done * 100 / ProgressBar.this.count;
                    lineEnder = (percentage >= 100) ? '\n' : '\r';
                    for (i = 0; i < 50; i++)
                        progressBar[i] = (percentage >= (i + 1) * 2) ? '=' : ' ';
                    if (percentage > lastPercentage) {
                        i = 0;
                        System.err.printf(
                                "[%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c] %3d%% %d/%d.%c",
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], progressBar[i++], progressBar[i++],
                                progressBar[i++], progressBar[i++], percentage, done, ProgressBar.this.count,
                                lineEnder);
                    }
                    lastPercentage = percentage;
                    try {
                        Thread.sleep(refreshRate);
                    } catch (InterruptedException e) {
                    }
                } while (done < ProgressBar.this.count);
            }
        };
        displayThread.start();
    }

    public synchronized void step() {
        done++;
    }

    public void join() {
        try {
            displayThread.join();
        } catch (InterruptedException e) {
            /* do nothing */ }
    }

}
