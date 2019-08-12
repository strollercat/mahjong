package com.nbcb.core.io;

import java.util.List;

import com.nbcb.common.helper.Response;

public interface UserActionApi {

	public Response requestDismissRoom(String account,boolean dismiss);

	public Response enterRoom(String account, String roomId);

	public Response leaveRoom(String account);

	public Response ready(String account);

	public Response unReady(String account);

	public Response mjAction(String account, int action, int card0, int card1);
	
	public Response chatText(String account,String text);
	
	public Response chatVoice(String account,String serverId,String localId);
	
	public Response bullet(String account,int authorDir,int dir,int index);
	
	public Response cancalManaged(String account);
	
	public Response threeWaterShoot(String account,List cns);


}
