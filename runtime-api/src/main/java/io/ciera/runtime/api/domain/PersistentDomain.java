package io.ciera.runtime.api.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface PersistentDomain extends Domain {

  void persist(ObjectOutputStream out) throws IOException;

  void load(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
