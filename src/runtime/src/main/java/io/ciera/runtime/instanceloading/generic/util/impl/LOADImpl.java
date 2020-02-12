package io.ciera.runtime.instanceloading.generic.util.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.ciera.runtime.instanceloading.generic.util.LOAD;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.util.Utility;


// TODO null checks on all inputs

public class LOADImpl<C extends IComponent<C>> extends Utility<C> implements LOAD {

    public LOADImpl(C context) {
        super(context);
    }

    @Override
    public Object call_function(String function_name, Object ... args) throws XtumlException {
        Method domainFunction = null;
        for (Method method : context().getClass().getMethods()) {
            if (method.getName().equals(function_name)) {
                domainFunction = method;
                break;
            }
        }
        if (domainFunction != null && domainFunction.getParameterCount() == args.length) {
            try {
                Object[] parameterList = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    try {
                        Constructor<?> valueConstructor = domainFunction.getParameterTypes()[i].getConstructor(Object.class);
                        parameterList[i] = valueConstructor.newInstance(args[i]);
                    }
                    catch (NoSuchMethodException e) {
                        parameterList[i] = args[i];
                    }
                }
                return domainFunction.invoke(context(), parameterList);
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | SecurityException e) {
                e.printStackTrace();
                throw new XtumlException(String.format("Could not invoke domain function '%s' on component '%s'.", function_name, context().getClass().getName()));
            }
        }
        else {
            throw new XtumlException(String.format("Could not find domain function '%s' on component '%s'.", function_name, context().getClass().getName()));
        }
    }

    @Override
    public Object create(String key_letters) throws XtumlException {
        try {
            Class<?> modelClass = context().getClassByKeyLetters(key_letters);
            if (modelClass != null) {
                return modelClass.getMethod("create", context().getClass()).invoke(null, context());
            }
            else {
                throw new XtumlException(String.format("No class found with key letters '%s'", key_letters));
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new XtumlException(String.format("Could not create instance of class with key letters '%s'", key_letters));
        }
    }

    @Override
    public void load(String java_class, String[] args) throws XtumlException {
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
    public void relate(Object inst1, Object inst2, int rel_num, String phrase) throws XtumlException {
        if (inst1 == null || inst2 == null) {
            throw new XtumlException("Invalid arguments to relate");
        }
        for (Method relator : context().getClass().getMethods()) {
            if (relator.getName().startsWith(String.format("relate_R%d", rel_num)) && relator.getParameterCount() == 2) {
                Class<?>[] parameterTypes = relator.getParameterTypes();
                Object form;
                Object part;
                // non-reflexive binary
                if (!parameterTypes[0].equals(parameterTypes[1])) {
                    // types match in the order given
                    if (parameterTypes[0].isInstance(inst1) && parameterTypes[1].isInstance(inst2)) {
                        form = inst1;
                        part = inst2;
                    }
                    // types match if flipped
                    else if (parameterTypes[0].isInstance(inst2) && parameterTypes[1].isInstance(inst1)) {
                        form = inst2;
                        part = inst1;
                    }
                    // types do not match
                    else {
                        continue;
                    }
                }
                // reflexive binary
                else if (parameterTypes[0].isInstance(inst1)) {
                    if (phrase == null) {
                        throw new XtumlException("Invalid arguments to relate");
                    }
                    // the phrase is found in the relator name, this means the order is as given
                    if (relator.getName().indexOf(phrase.replaceAll("\\s", "_")) >= 0) {
                        form = inst1;
                        part = inst2;
                    }
                    // order must bbe flipped
                    else {
                        form = inst2;
                        part = inst1;
                    }
                }
                else {
                    throw new XtumlException(String.format("Expected instances of types '%s' and '%s', but recieved incompatible types '%s' and '%s' for 'R%d' in component '%s'.",
                                                           parameterTypes[0].getName(), parameterTypes[1].getName(), inst1.getClass().getName(), inst2.getClass().getName(),
                                                           rel_num, context().getClass().getName()));
                }
                // perform relate
                try {
                    relator.invoke(context(), form, part);
                    return;  // early return here to break out of loop and avoid throwing an exception
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new XtumlException(String.format("Could not relate instances across 'R%d' in component '%s'.", rel_num, context().getClass().getName()));
                }
     
            }
        }
        // if it gets this far, a matching relate function was not found
        throw new XtumlException(String.format("Could not relate instances of types '%s' and '%s' across 'R%d' in component '%s'.",
                                               inst1.getClass().getName(), inst2.getClass().getName(), rel_num, context().getClass().getName()));
    }

    @Override
    public void relate_using(Object inst1, Object inst2, Object link, int rel_num, String phrase) throws XtumlException {
        // TODO does not work for reflexive associatives
        relate(inst1, link, rel_num, phrase);
        relate(link, inst2, rel_num, phrase);
    }

    @Override
    public void set_attribute(Object instance, String attribute_name, Object value) throws XtumlException {
        Method setter = null;
        for (Method method : instance.getClass().getMethods()) {
            if (method.getName().equals("set" + attribute_name.substring(0, 1).toUpperCase() + attribute_name.substring(1))) {
                setter = method;
                break;
            }
        }
        if (setter != null && setter.getParameterCount() == 1) {
            try {
                try {
                    Constructor<?> valueConstructor = setter.getParameterTypes()[0].getConstructor(Object.class);
                    setter.invoke(instance, valueConstructor.newInstance(value));
                }
                catch (NoSuchMethodException e) {
                    setter.invoke(instance, value);
                }
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | SecurityException e) {
                e.printStackTrace();
                throw new XtumlException(String.format("Could not set attribute '%s' on class '%s'", attribute_name, instance.getClass().getName()));
            }
        }
        else {
            throw new XtumlException(String.format("Could not find setter method for attribute '%s' on class '%s'", attribute_name, instance.getClass().getName()));
        }
    }

}
