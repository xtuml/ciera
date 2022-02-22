package io.ciera.runtime.application;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.ExecutionContext.ExecutionMode;
import io.ciera.runtime.api.application.ExecutionContext.ModelIntegrityMode;
import io.ciera.runtime.application.task.GeneratedEvent;

public class TestExecutionModes {

    private static class TestEvent extends AbstractEvent {
        public TestEvent(Object... data) {
            super(0, data);
        }
    }

    private EventTarget target;

    @Test
    public void testInterleavedMode() {
        // create fake application
        AbstractApplication app = new AbstractApplication("testInterleavedMode", new String[0]) {
            @Override
            public void setup() {
                addContext(new ThreadExecutionContext("SequentialContext", this, ExecutionMode.INTERLEAVED,
                        ModelIntegrityMode.STRICT));
            }
        };
        app.setup();

        app.getLogger().trace("Testing interleaved mode");

        // run test
        String[] output = runEventSequence(app);

        // check output
        assertArrayEquals(new String[] { "A1", "B1", "A2", "B2", "A3", "B3" }, output);
    }

    @Test
    public void testSequentialMode() {
        // create fake application
        AbstractApplication app = new AbstractApplication("testSequentialMode", new String[0]) {
            @Override
            public void setup() {
                addContext(new ThreadExecutionContext("SequentialContext", this, ExecutionMode.SEQUENTIAL,
                        ModelIntegrityMode.STRICT));
            }
        };
        app.setup();

        app.getLogger().trace("Testing sequential mode");

        // run test
        String[] output = runEventSequence(app);

        // check output
        assertArrayEquals(new String[] { "A1", "A2", "A3", "B1", "B2", "B3" }, output);
    }

    @Test
    public void testDefaultMode() {
        // create fake application
        AbstractApplication app = new AbstractApplication("testInterleavedMode", new String[0]) {
        };
        app.setup();

        app.getLogger().trace("Testing default (interleaved) mode");

        // run test
        String[] output = runEventSequence(app);

        // check output
        assertArrayEquals(new String[] { "A1", "B1", "A2", "B2", "A3", "B3" }, output);
    }

    private String[] runEventSequence(AbstractApplication app) {
        List<String> output = new ArrayList<>();

        // create fake events
        List<Event> Aevts = new ArrayList<>();
        for (String eventKey : new String[] { "A1", "A2", "A3" }) {
            Aevts.add(new TestEvent(eventKey));
        }
        List<Event> Bevts = new ArrayList<>();
        for (String eventKey : new String[] { "B1", "B2", "B3" }) {
            Bevts.add(new TestEvent(eventKey));
        }

        Iterator<Event> Aiter = Aevts.iterator();
        Iterator<Event> Biter = Bevts.iterator();

        // create fake event target
        target = new EventTarget() {

            @Override
            public ExecutionContext getContext() {
                return app.defaultContext();
            }

            @Override
            public void consumeEvent(Event event) {
                String key = (String) event.getData(0);
                app.getLogger().trace("Handling event: %s", key);
                output.add(key);
                if (key.startsWith("A") && Aiter.hasNext()) {
                    // generate the next event
                    app.defaultContext()
                            .execute(new GeneratedEvent(Aiter.next(), target, app.defaultContext().getExecutionMode()));
                } else if (key.startsWith("B") && Biter.hasNext()) {
                    // generate the next event
                    app.defaultContext()
                            .execute(new GeneratedEvent(Biter.next(), target, app.defaultContext().getExecutionMode()));
                }
            }

            @Override
            public void attachTo(ExecutionContext context) {
            }

            @Override
            public String toString() {
                return "TestTarget";
            }
        };

        // queue primary task for A
        app.defaultContext().execute(() -> {
            app.getLogger().trace("Executing primary task A");
            app.defaultContext()
                    .execute(new GeneratedEvent(Aiter.next(), target, app.defaultContext().getExecutionMode()));
        });

        // queue primary task for B
        app.defaultContext().execute(() -> {
            app.getLogger().trace("Executing primary task B");
            app.defaultContext()
                    .execute(new GeneratedEvent(Biter.next(), target, app.defaultContext().getExecutionMode()));
        });

        // set up idle halt
        System.setProperty("io.ciera.runtime.haltWhenIdle", "true");

        // run the context
        app.start();

        return output.toArray(new String[0]);
    }

}
