package com.nbcb.common.service;

import com.nbcb.common.exception.TLException;

public interface Parser {
	public Object parse(Object object) throws TLException;
}
