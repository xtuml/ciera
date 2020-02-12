package io.ciera.runtime.instanceloading.generic.util;

import io.ciera.runtime.summit.exceptions.XtumlException;

/**
 * The LOAD utility provides a hook from hand written Java loaders into the
 * instance population of a component.
 */
public interface LOAD {

    /**
     * Create a new loader with a given set of arguments and kick off the
     * loading process. An instance of the class specified by the
     * {@code java_class} parameter is created. The specified class is expected
     * to implement the {@link
     * io.ciera.runtime.instanceloading.generic.IGenericLoader IGenericLoader}
     * interface. Once created, the {@code load} method of the loader will be
     * invoked, passing {@code args} and an instance of the {@code LOAD} class
     * to link call back in to the instance population.
     *
     * @param java_class the fully qualified class name of the loader class to
     * create
     * @param args the list of arguments to pass to the loader class
     * @throws XtumlException if the class specified by {@code java_class}
     * cannot be loaded or if it does not implement the {@link
     * io.ciera.runtime.instanceloading.generic.IGenericLoader IGenericLoader}
     * interface
     * @see io.ciera.runtime.instanceloading.generic.IGenericLoader IGenericLoader
     */
    public void load(String java_class, String[] args) throws XtumlException;

    /**
     * Create an xtUML class instance.
     *
     * @param key_letters the key letters of the xtUML class
     * @return an instance handle to the newly created class instance
     * @throws XtumlException if no class with matching key letters can be found
     * in the component
     */
	public Object create(String key_letters) throws XtumlException;

    /**
     * Relate two xtUML instances together across the given relationship. For
     * non-reflexive relationships, {@code inst1} and {@code inst2} are
     * interchangeable and the value of {@code phrase} has no effect. It may be
     * {@code null}. For reflexive relationships, {@code inst1} and {@code inst2}
     * will "read across" according to the value of {@code phrase} with the same
     * semantics as OAL.
     *
     * @param inst1 the first instance to relate
     * @param inst2 the second instance to relate
     * @param rel_num the relationship number to create
     * @param phrase the text phrase used to disambiguate relates of reflexive
     * relationships
     * @throws XtumlException if the relationship specified does not exist
     * between inst1 and inst2 or if the act of relating the instances results in
     * a model integrity violation
     */
    public void relate(Object inst1, Object inst2, int rel_num, String phrase) throws XtumlException;

    /**
     *
     * Relate three xtUML instances together across the given associative
     * relationship. For non-reflexive relationships, {@code inst1} and {@code
     * inst2} are interchangeable and the value of {@code phrase} has no effect.
     * It may be {@code null}. For reflexive relationships, {@code inst1} and
     * {@code inst2} will "read across" according to the value of {@code phrase}
     * with the same semantics as OAL.
     *
     * @param inst1 the first instance to relate
     * @param inst2 the second instance to relate
     * @param link the associative instance to relate
     * @param rel_num the relationship number to create
     * @param phrase the text phrase used to disambiguate relates of reflexive
     * relationships
     * @throws XtumlException if the relationship specified does not exist
     * between inst1 and inst2 or if the act of relating the instances results in
     * a model integrity violation
     */
    public void relate_using(Object inst1, Object inst2, Object link, int rel_num, String phrase) throws XtumlException;

    /**
     * Set the value of an attribute on an instance of an xtUML class.
     *
     * @param instance the model class instance
     * @param attribute_name the name of the attribute to set
     * @param value the value to assign to the specified attribute
     * @throws XtumlException if the specified attribute does not exist on the
     * class or if the type of the passed value is not compatible with the
     * attribute type
     */
    public void set_attribute(Object instance, String attribute_name, Object value) throws XtumlException;

    /**
     * Invoke an xtUML domain function in the same component which originally
     * created the instance of {@code LOAD}.
     *
     * @param function_name The name of the domain function to invoke
     * @param args The argument list in modeled order
     * @return The result of the function invocation or {@code null} for
     * functions with void return type
     * @throws XtumlException if the a domain function could not be found with
     * the given names, or if the number of arguments or types of arguments
     * mismatch
     */
	public Object call_function(String function_name, Object ... args) throws XtumlException;



}
