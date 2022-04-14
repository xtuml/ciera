package io.ciera.runtime.api.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface PersistentDomain extends Domain {

  public void persist(ObjectOutputStream out) throws IOException;

  public void load(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
