package com.nbcb.common.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Object decode(String json, Class clazz)
			throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(json, clazz);
	}

	public static String encode(Object o) throws JsonGenerationException,
			JsonMappingException, IOException {
		return objectMapper.writeValueAsString(o);
	}
}
