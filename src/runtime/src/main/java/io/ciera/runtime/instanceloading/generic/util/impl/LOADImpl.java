package io.ciera.runtime.instanceloading.generic.util.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
        if (function_name == null || Arrays.asList(args).contains(null)) {
            throw new XtumlException("Invalid arguments to 'call_function'");
        }
        for (Method domainFunction : context().getClass().getMethods()) {
            if (domainFunction.getName().equals(function_name) && domainFunction.getParameterCount() == args.length) {
                try {
                    Object[] parameterList = new Object[args.length];
                    for (int i = 0; i < args.length; i++) {
                        try {
                            // try the constructor with the same type as the passed value
                            Constructor<?> valueConstructor = domainFunction.getParameterTypes()[i].getConstructor(args[i].getClass());
                            parameterList[i] = valueConstructor.newInstance(args[i]);
                        }
                        catch (NoSuchMethodException e1) {
                            try {
                                // try the constructor with the same generic Object type
                                Constructor<?> valueConstructor = domainFunction.getParameterTypes()[i].getConstructor(Object.class);
                                parameterList[i] = valueConstructor.newInstance(args[i]);
                            }
                            catch (NoSuchMethodException e2) {
                                try {
                                    // try to find the deserialize method
                                    Method deserialize = domainFunction.getParameterTypes()[i].getMethod("deserialize", Object.class);
                                    parameterList[i] = deserialize.invoke(null, args[i]);
                                }
                                catch (NoSuchMethodException e3) {
                                    // no casting
                                    parameterList[i] = args[i];
                                }
                            }
                        }
                    }
                    return domainFunction.invoke(context(), parameterList);
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | SecurityException e) {
                    if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getCause() instanceof XtumlException) {
                        throw (XtumlException)((InvocationTargetException)e).getCause();
                    }
                    throw new XtumlException(String.format("Could not invoke domain function '%s' on component '%s'.", function_name, context().getClass().getName()), e);
                }
            }
        }
        throw new XtumlException(String.format("Could not find domain function '%s' on component '%s'.", function_name, context().getClass().getName()));
    }

    @Override
    public Object create(String key_letters) throws XtumlException {
        if (key_letters == null) {
            throw new XtumlException("Invalid arguments to 'create'");
        }
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
            if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getCause() instanceof XtumlException) {
                throw (XtumlException)((InvocationTargetException)e).getCause();
            }
            throw new XtumlException(String.format("Could not create instance of class with key letters '%s'", key_letters), e);
        }
    }

    @Override
    public void load(String java_class, String[] args) throws XtumlException {
        if (java_class == null || args == null) {
            throw new XtumlException("Invalid arguments to 'load'");
        }
        try {
            Class<?> loader = ClassLoader.getSystemClassLoader().loadClass(java_class);
            Method loadMethod = loader.getMethod("load", LOAD.class, String[].class);
            loadMethod.invoke(loader.newInstance(), this, (Object)args);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e) {
            if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getCause() instanceof XtumlException) {
                throw (XtumlException)((InvocationTargetException)e).getCause();
            }
            throw new XtumlException(String.format("Could not load or run class '%s'", java_class), e);
        }
    }

    @Override
    public void relate(Object inst1, Object inst2, int rel_num, String phrase) throws XtumlException {
        if (inst1 == null || inst2 == null) {
            throw new XtumlException("Invalid arguments to 'relate'");
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
                    if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getCause() instanceof XtumlException) {
                        throw (XtumlException)((InvocationTargetException)e).getCause();
                    }
                    throw new XtumlException(String.format("Could not relate instances across 'R%d' in component '%s'.", rel_num, context().getClass().getName()), e);
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
        if (instance == null || attribute_name == null || value == null) {
            throw new XtumlException("Invalid arguments to 'set_attribute'");
        }
        for (Method setter : instance.getClass().getMethods()) {
            if (setter.getName().equals("set" + attribute_name.substring(0, 1).toUpperCase() + attribute_name.substring(1))
                && setter.getParameterCount() == 1) {
                try {
                    try {
                        // try the constructor with the same type as the passed value
                        Constructor<?> valueConstructor = setter.getParameterTypes()[0].getConstructor(value.getClass());
                        setter.invoke(instance, valueConstructor.newInstance(value));
                    }
                    catch (NoSuchMethodException e1) {
                        try {
                            // try the constructor with the same generic Object type
                            Constructor<?> valueConstructor = setter.getParameterTypes()[0].getConstructor(Object.class);
                            setter.invoke(instance, valueConstructor.newInstance(value));
                        }
                        catch (NoSuchMethodException e2) {
                            try {
                                // try to find the deserialize method
                                Method deserialize = setter.getParameterTypes()[0].getMethod("deserialize", Object.class);
                                setter.invoke(instance, deserialize.invoke(null, value));
                            }
                            catch (NoSuchMethodException e3) {
                                // no casting
                                setter.invoke(instance, value);
                            }
                        }
                    }
                    return;
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | SecurityException e) {
                    if (e instanceof InvocationTargetException && ((InvocationTargetException)e).getCause() instanceof XtumlException) {
                        throw (XtumlException)((InvocationTargetException)e).getCause();
                    }
                    throw new XtumlException(String.format("Could not set attribute '%s' on class '%s'", attribute_name, instance.getClass().getName()), e);
                }
            }
        }
        throw new XtumlException(String.format("Could not find setter method for attribute '%s' on class '%s'", attribute_name, instance.getClass().getName()));
    }

}
