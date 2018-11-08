package io.ciera.runtime.summit.statemachine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import io.ciera.runtime.summit.application.IInstanceActionHome;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.CantHappenException;
import io.ciera.runtime.summit.exceptions.XtumlException;

public abstract class StateMachine<T extends IModelInstance<T, C>, C extends IComponent<C>>
        implements IStateMachine<T, C>, IInstanceActionHome<T, C> {

    public static final int NON_EXISTENT = -1;
    public static final ITransition CANT_HAPPEN = new CantHappen();
    public static final ITransition IGNORE = new Ignore();

    private int currentState;
    private C context;

    public StateMachine(C context) {
        currentState = NON_EXISTENT;
        this.context = context;
    }

    public StateMachine(C context, int initialState) {
        currentState = initialState;
        this.context = context;
    }

    @Override
    public void transition(IEvent event) throws XtumlException {
        ITransition entry;
        ITransition[][] sem = getStateEventMatrix();
        if (currentState >= 0 && currentState < sem.length && event.getId() >= 0
                && event.getId() < sem[currentState].length) {
            entry = sem[currentState][event.getId()];
        } else {
            throw new XtumlException("Could not get state event matrix entry");
        }
        if (entry instanceof CantHappen) {
            throw new CantHappenException(getClassName() + ": Event '" + event.getName() + "' cannot happen in state '"
                    + getStateName(currentState) + "'");
        } else if (entry instanceof Ignore) {
            /* do nothing */
        } else if (entry instanceof ITransition) {
            ITransition txn = entry;
            int prevState = currentState;
            currentState = txn.execute(event);
            System.out.printf("TXN: %-15s: %-50s -> %-50s\n", getClassName(), getStateName(prevState),
                    getStateName(currentState));
        } else {
            throw new XtumlException("Unknown state event matrix entry");
        }

    }

    @Override
    public String getStateName(int state) {
        String name = "Unknown";
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers()) && field.getType() == Integer.TYPE
                        && field.getInt(this) == state) {
                    name = field.getName();
                }
            } catch (IllegalAccessException e) {
                /* do nothing */ }
        }
        return name;
    }

    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public C context() {
        return context;
    }

    private static final class CantHappen implements ITransition {
        @Override
        public int execute(IEvent event) throws XtumlException {
            return StateMachine.NON_EXISTENT;
        }
    }

    private static final class Ignore implements ITransition {
        @Override
        public int execute(IEvent event) throws XtumlException {
            return StateMachine.NON_EXISTENT;
        }
    }

}
