package io.ciera.runtime.instanceloading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeLog implements IChangeLog {
	
	private Map<Object, IModelDelta> changes;
	
	public ChangeLog() {
		changes = new HashMap<>();
	}

	@Override
	public List<IModelDelta> getChanges() {
		List<IModelDelta> returnList = new ArrayList<>();
	    for (IModelDelta delta : changes.values()) {
			returnList.add(delta);
		}
		return returnList;
	}

	@Override
	public void addChange(IModelDelta delta) {
		if (null != delta) {
            IModelDelta currentDelta = null;
            if (changes.containsKey(delta.getModelElement())) {
              currentDelta = changes.get(delta.getModelElement());
            }
            if (delta instanceof IInstanceDeletedDelta && currentDelta instanceof IInstanceCreatedDelta) {
            	changes.remove(delta.getModelElement()); // create and delete negate one another
            }
            else if (null == currentDelta || delta instanceof IInstanceCreatedDelta || delta instanceof IInstanceDeletedDelta) {
				changes.put(delta.getModelElement(), delta);
			}
			else if (delta instanceof IAttributeChangedDelta && currentDelta instanceof IAttributeChangedDelta) {
                changes.put(delta.getModelElement(), new AttributeChangedDelta((IAttributeChangedDelta)currentDelta, (IAttributeChangedDelta)delta));
			}
		}
	}

}
