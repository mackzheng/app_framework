package mack.com.c_framework.network.protocol.http.networkengine;

public interface INetworkEngineInterface
{
	public boolean addNetworkRequest(NetworkRequest request);
	public void cancelNetworkRequest(NetworkRequest request);
	/**
	 * 设置客户端名（放在header中）
	 * @param clientName
	 */
	public void setClientName(String clientName);
	/**
	 * 设置客户端版本（放在header中）
	 * @param clientVersion
	 */
	public void setClientVersion(String clientVersion);
	/**
	 * 设置包是否使用gzip压缩
	 * @param enable
	 */
	public void setCompressedEnable(Boolean enable);
}
