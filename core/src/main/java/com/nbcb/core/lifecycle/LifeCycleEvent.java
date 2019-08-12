package com.nbcb.core.lifecycle;

public class LifeCycleEvent {

	public static final int BEFORESTART = 0;
	public static final int AFTERSTART = 1;
	public static final int BEFORESTOP = 2;
	public static final int AFTERSTOP = 3;

	private Object object;
	private int event;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

}
