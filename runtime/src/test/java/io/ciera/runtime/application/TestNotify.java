package io.ciera.runtime.application;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import io.ciera.runtime.AbstractDomain;
import io.ciera.runtime.AbstractEvent;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.Port;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.UniqueId;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class TestNotify {

  private static final class TestEvent extends AbstractEvent {

    private static final long serialVersionUID = 1L;

    public TestEvent(final Object... data) {
      super(0);
    }
  }

  @Test
  public void testTaskNotify() {
    try {

      // create a test app
      final BaseApplication app = new BaseApplication("TestNotify");
      app.setup();

      // run the app in a new thread to protect timeouts
      final Thread t = new Thread(() -> app.start());
      t.start();

      // delay
      Thread.sleep(100);

      // add a task after it's started and it should wake up and run it
      app.defaultContext().halt();

      t.join();
    } catch (final InterruptedException e) {
    }
  }

  @Test
  public void testTimerNotify() {
    try {

      // create a test app
      final BaseApplication app = new BaseApplication("TestNotify");
      app.addDomain(
          new AbstractDomain("TestDomain") {
            @Override
            public void initialize() {}

            @Override
            public Port getPort(final String portName) {
              return null;
            }
          });
      app.setup();

      // run the app in a new thread to protect timeouts
      final Thread t = new Thread(() -> app.start());
      t.start();

      // delay
      Thread.sleep(100);

      // Schedule a timer after it's started. It should wake up and handle the timer.
      final EventTarget target =
          new EventTarget() {
            @Override
            public ExecutionContext getContext() {
              return app.defaultContext();
            }

            @Override
            public void consumeEvent(final Event event) {
              app.defaultContext().halt();
            }

            @Override
            public String toString() {
              return "TestTarget";
            }

            @Override
            public UniqueId getTargetId() {
              return null;
            }

            @Override
            public Domain getDomain() {
              return app.getDomains().stream().findAny().orElseThrow();
            }
          };
      app.defaultContext().scheduleEvent(TestEvent::new, target, Duration.ZERO);

      t.join();
    } catch (final InterruptedException e) {
    }
  }
}
