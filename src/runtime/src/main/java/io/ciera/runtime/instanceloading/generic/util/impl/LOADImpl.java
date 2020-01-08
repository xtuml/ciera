package io.ciera.runtime.instanceloading.generic.util.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.ciera.runtime.instanceloading.generic.util.LOAD;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;

public class LOADImpl<C extends IComponent<C>> extends Utility<C> implements LOAD {

	public LOADImpl(C context) {
		super(context);
	}

	@Override
	public Object create(String key_letters) throws XtumlException {
		// TODO validate args
		try {
			return context().getClassByKeyLetters(key_letters).getMethod("create", context().getClass()).invoke(null, context());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new XtumlException(String.format("Could create instance of class with key letters '%s'", key_letters));
		}
	}

	@Override
    public void load(String java_class, String[] args) throws XtumlException {
		// TODO validate args
    	try {
			Class<?> loader = ClassLoader.getSystemClassLoader().loadClass(java_class);
			Method loadMethod = loader.getMethod("load", LOAD.class, String[].class);
			loadMethod.invoke(loader.newInstance(), this, (Object)args);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
			throw new XtumlException(String.format("Could not load or run class '%s'", java_class));
		}
    }

	@Override
    public void relate(Object form, Object part, int rel_num, String phrase) throws XtumlException {
    	// TODO
    }

	@Override
    public void relate_using(Object form, Object part, Object link, int rel_num, String phrase) throws XtumlException {
    	// TODO
    }

	@Override
    public void set_attribute(Object instance, String attribute_name, Object value) throws XtumlException {
		// TODO validate args
		Method setter = null;
		for (Method method : instance.getClass().getMethods()) {
			if (method.getName().equals("set" + attribute_name.substring(0, 1).toUpperCase() + attribute_name.substring(1))) {
				setter = method;
				break;
			}
		}
		if (setter != null) {
			try {
				setter.invoke(instance, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw new XtumlException(String.format("Could not set attribute '%s' on class '%s'", attribute_name, instance.getClass().getName()));
			}
		}
		else {
			// TODO
		}
    }

}
