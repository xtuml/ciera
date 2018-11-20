package io.ciera.runtime.summit.time;

import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public final class TimerHandle extends UniqueId {
	
	public TimerHandle() {}
	
	public TimerHandle(UniqueId id) {
		super(id);
	}
	
	public static TimerHandle random() {
        return new TimerHandle(UniqueId.random());
	}

	public static TimerHandle deserialize(Object o) throws XtumlException {
      if (o instanceof TimerHandle) {
          return (TimerHandle)o;
      }
      else return new TimerHandle(UniqueId.deserialize(o));
	}

}
