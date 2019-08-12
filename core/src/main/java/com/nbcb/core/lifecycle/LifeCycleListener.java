package com.nbcb.core.lifecycle;

public interface LifeCycleListener {
	
	public void beforeStart(LifeCycleEvent event);

	public void afterStart(LifeCycleEvent event);

	public void beforeStop(LifeCycleEvent event);

	public void afterStop(LifeCycleEvent event);
}
