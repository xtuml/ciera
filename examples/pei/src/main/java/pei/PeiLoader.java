package pei;

import io.ciera.runtime.instanceloading.generic.IGenericLoader;
import io.ciera.runtime.instanceloading.generic.util.LOAD;

import io.ciera.runtime.summit.exceptions.XtumlException;

public class PeiLoader implements IGenericLoader {

    @Override
    public void load(LOAD loader, String[] args) {
        for (String arg : args) {
            System.err.println("Argument: " + arg);
        }
        try {
            Object dog = loader.create("dog");
            Object owner = loader.create("dog_owner");
            loader.set_attribute(dog, "name", "Fido");
            loader.set_attribute(owner, "name", "Levi");
        } catch (XtumlException e) {
            System.err.println("Failed to load classes");
        }
    }

}
