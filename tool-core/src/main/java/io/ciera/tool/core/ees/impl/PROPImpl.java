package io.ciera.tool.core.ees.impl;

import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.util.Utility;
import io.ciera.tool.core.ees.PROP;

public class PROPImpl<C extends IComponent<C>> extends Utility<C> implements PROP {

  public PROPImpl(C context) {
    super(context);
  }

  public boolean getFlag(final String key) {
    return !System.getProperty(key, "").isEmpty();
  }

  public String getProperty(final String key) {
    return System.getProperty(key, "");
  }

  public void setProperty(final String key, final String value) {
    System.setProperty(key, value);
  }
}
