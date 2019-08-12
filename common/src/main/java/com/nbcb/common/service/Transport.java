package com.nbcb.common.service;

import com.nbcb.common.exception.TLCommunicationException;

public interface Transport {
	public Object submit(Object object) throws TLCommunicationException;
}
