package mack.com.c_framework.network.protocol.http.networkengine;

import java.util.Dictionary;

public class NetworkResponse
{
	//public Response mResponse = null;
	public NetworkRequest mRequest = null;
	public int mStatusCode = 0;
	public Dictionary<String,String> mHeaderFieldDictionary = null;
	
	public NetworkResponse(int code, Dictionary<String, String> headers)
	{
		super();
		mHeaderFieldDictionary = headers;
		mStatusCode = code;
	}
	
	public NetworkResponse()
	{
		super();
	}
	
	public Dictionary<String,String> allHeaderFields()
	{
		return mHeaderFieldDictionary;
	}
	
	public int statusCode()
	{
		return mStatusCode;
	}
	
	static public String localizedStringForStatusCode(int statusCode)
	{
		return null;
	}
}
