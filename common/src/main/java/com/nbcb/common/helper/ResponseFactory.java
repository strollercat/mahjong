package com.nbcb.common.helper;


/**
 * 
 * @author zhengbinhui
 * 
 */
public class ResponseFactory {
	public static Response newResponse(String errCode, String errMsg,
			Object body) {
		BodyResponse response = new BodyResponse();
		response.setErrCode(errCode);
		response.setErrMsg(errMsg);
		response.setBody(body);
		return response;
	}

	public static Response newResponse(String errCode, String errMsg) {
		Response response = new Response();
		response.setErrCode(errCode);
		response.setErrMsg(errMsg);
		return response;
	}

//	public static Map<String, Object> newWebSocketResponse(String code,
//			Object... objects) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("code", code);
//		if (objects == null || objects.length == 0) {
//			return map;
//		}
//		for(int i = 0;i<objects.length;i++){
//			map.put(objects[i]);
//		}
//	}
}
