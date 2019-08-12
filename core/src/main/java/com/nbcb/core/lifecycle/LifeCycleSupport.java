package com.nbcb.core.lifecycle;

import java.util.ArrayList;
import java.util.List;

public class LifeCycleSupport implements LifeCycleNotify{

	private List<LifeCycleListener> listeners = new ArrayList<LifeCycleListener>();

	public void addLifeCycleListener(LifeCycleListener listener) {
		listeners.add(listener);
	}

	public void notify(LifeCycleEvent event) {
		if (event.getEvent() == LifeCycleEvent.BEFORESTART) {
			for (LifeCycleListener listener : listeners) {
				listener.beforeStart(event);
			}
		}
		if (event.getEvent() == LifeCycleEvent.AFTERSTART) {
			for (LifeCycleListener listener : listeners) {
				listener.afterStart(event);
			}
		}
		if (event.getEvent() == LifeCycleEvent.BEFORESTOP) {
			for (LifeCycleListener listener : listeners) {
				listener.beforeStop(event);
			}
		}
		if (event.getEvent() == LifeCycleEvent.AFTERSTOP) {
			for (LifeCycleListener listener : listeners) {
				listener.afterStop(event);
			}
		}

	}

}
