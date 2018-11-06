package io.ciera.runtime.summit.components;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GeneratedEventTask;
import io.ciera.runtime.summit.application.tasks.GeneratedEventToSelfTask;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.statemachine.IEventTarget;

public abstract class Component<C extends IComponent<C>> implements IComponent<C> {

    private IRunContext runContext;

    public Component(IRunContext runContext) {
        this.runContext = runContext;
    }

    @Override
    public IRunContext getRunContext() {
        return runContext;
    }

    @Override
    public boolean equality(C value) throws XtumlException {
        return equals(value);
    }
    
    @Override
    public void generate(IEvent event, IEventTarget target) {
    	runContext.execute(new GeneratedEventTask() {
			@Override
			public void run() throws XtumlException {
				target.accept(event);
			}
		});
    }

    @Override
    public void generateToSelf(IEvent event, IEventTarget target) {
    	runContext.execute(new GeneratedEventToSelfTask() {
			@Override
			public void run() throws XtumlException {
				target.accept(event);
			}
		});
    }

}
