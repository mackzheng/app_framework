package mack.com.c_framework.network.protocol.http.networkengine;

import java.nio.ByteBuffer;

public interface INetworkEngineDelegate
{
	/*@deprecated*/
	public void onRequestFinish(NetworkRequest request, ByteBuffer data);
	public void onRequestFail(NetworkRequest request, int errorCode);

	public interface INetworkEngineDelegateOptional extends INetworkEngineDelegate
	{
		//服务器返回数据的进度，函数名要改
		public void onRequestProgress(NetworkRequest request, long total, long downloaded);
		//发送post请求的进度
		public void onSendRequestProgress(NetworkRequest request, long total, long uploaded);
		//收到http请求的头
		public void onReceiveResponse(NetworkRequest request, NetworkResponse response);
	}
}


