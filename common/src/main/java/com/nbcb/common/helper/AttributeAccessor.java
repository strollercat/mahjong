package com.nbcb.common.helper;

import java.util.Set;

public interface AttributeAccessor {

	public void setAttribute(String key, Object value);

	public Object getAttribute(String key);
	
	public Set<String> keySet();
	
	public boolean hasAttribute(String key);
	
	public void removeAttribute(String key);
	
	

}
