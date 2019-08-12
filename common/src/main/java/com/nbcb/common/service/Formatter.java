package com.nbcb.common.service;

import com.nbcb.common.exception.TLException;

public interface Formatter {
	public Object format(Object object) throws TLException;
}
