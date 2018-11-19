package io.ciera.runtime.summit.components;

import io.ciera.runtime.summit.application.IApplication;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.application.tasks.GeneratedEventTask;
import io.ciera.runtime.summit.application.tasks.GeneratedEventToSelfTask;
import io.ciera.runtime.summit.exceptions.StateMachineException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.EventHandle;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.statemachine.IEventTarget;

public abstract class Component<C extends IComponent<C>> implements IComponent<C> {

	private IApplication app;
    private IRunContext runContext;
    private int populationId;

    public Component(IApplication app, IRunContext runContext, int populationId) {
    	this.app = app;
        this.runContext = runContext;
        this.populationId = populationId;
    }

    @Override
    public IApplication getApp() {
    	return app;
    }

    @Override
    public IRunContext getRunContext() {
        return runContext;
    }
    
    @Override
    public int getId() {
    	return populationId;
    }

    @Override
    public boolean equality(C value) throws XtumlException {
        return equals(value);
    }

    @Override
    public void generate(EventHandle e) throws XtumlException {
    	if ( null != e ) {
    		IEvent event = getRunContext().getEvent(e);
    		if ( null != event && null != event.getTarget() ) {
                if (event.toSelf()) {
                    generateToSelf(event, event.getTarget());
                }
                else {
                    generate(event, event.getTarget());
                }
                getRunContext().deregisterEvent(e);
    		}
    		else {
                throw new StateMachineException("Event has not target");
    		}
    	}
    	else {
            throw new StateMachineException("Could not acquire event instance");
    	}
    }

    private void generate(IEvent event, IEventTarget target) {
        getRunContext().execute(new GeneratedEventTask() {
            @Override
            public void run() throws XtumlException {
                target.accept(event);
            }
        });
    }

    private void generateToSelf(IEvent event, IEventTarget target) {
        getRunContext().execute(new GeneratedEventToSelfTask() {
            @Override
            public void run() throws XtumlException {
                target.accept(event);
            }
        });
    }

}
