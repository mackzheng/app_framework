package mack.com.c_framework.network.protocol.http.networkengine;

/**
 * @author alex
 *
 */
public class NetworkEngine
{
	protected static NetworkEngineImplement networkEngineImplement = null;

	public static INetworkEngineInterface getEngine()
    {
	    if (networkEngineImplement == null)
        {
	        networkEngineImplement = new NetworkEngineImplement();
        }
	    return networkEngineImplement;
    }

    public static INetworkEngineInterface getEnginByOkHttp()
	{
		if (networkEngineImplement == null)
		{
			networkEngineImplement = new NetworkEngineImplement();
		}
		return networkEngineImplement;
	}

	public static INetworkEngineInterface getEnginByVolley()
	{
		if (networkEngineImplement == null)
		{
			networkEngineImplement = new NetworkEngineImplement();
		}
		return networkEngineImplement;
	}
}
