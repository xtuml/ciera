package io.ciera.runtime.application;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import io.ciera.runtime.application.task.Halt;
import io.ciera.runtime.types.Duration;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class TestNotify {

    private static final class TestEvent extends Event {
        public TestEvent(Object... data) {
            super(null);
        }
    }

    @Test
    public void testTaskNotify() {
        try {

            // create a test app
            final Application app = new Application("TestNotify", new String[0]) {
            };
            app.setup();

            // run the app in a new thread to protect timeouts
            Thread t = new Thread(() -> app.start());
            t.start();

            // delay
            Thread.sleep(100);

            // add a task after it's started and it should wake up and run it
            app.defaultContext().addTask(new Halt(app.defaultContext()));

            t.join();
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void testTimerNotify() {
        try {

            // create a test app
            final Application app = new Application("TestNotify", new String[0]) {
            };
            app.setup();

            // run the app in a new thread to protect timeouts
            Thread t = new Thread(() -> app.start());
            t.start();

            // delay
            Thread.sleep(100);

            // Schedule a timer after it's started. It should wake up and handle the timer.
            EventTarget target = new EventTarget() {
                @Override
                public ExecutionContext getContext() {
                    return app.defaultContext();
                }

                @Override
                public void consumeEvent(Event event) {
                    app.defaultContext().addTask(new Halt(app.defaultContext()));
                }

                @Override
                public void attachTo(ExecutionContext context) {
                }

                @Override
                public String toString() {
                    return "TestTarget";
                }
            };
            app.defaultContext().scheduleEvent(TestEvent.class, target, Duration.ZERO);

            t.join();
        } catch (InterruptedException e) {
        }
    }

}
