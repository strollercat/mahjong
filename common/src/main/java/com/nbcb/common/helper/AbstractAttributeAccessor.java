package com.nbcb.common.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractAttributeAccessor implements AttributeAccessor {

	private Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public void setAttribute(String key, Object value) {
		this.attributes.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return this.attributes.get(key);
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return attributes.keySet();
	}

	@Override
	public boolean hasAttribute(String key) {
		return attributes.get(key) != null;
	}

	@Override
	public void removeAttribute(String key) {
		attributes.remove(key);
	}

}
