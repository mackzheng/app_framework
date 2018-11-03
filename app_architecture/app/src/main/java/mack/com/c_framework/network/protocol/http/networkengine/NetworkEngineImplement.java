package mack.com.c_framework.network.protocol.http.networkengine;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class NetworkEngineImplement implements INetworkEngineInterface 
{
	private static final String TAG = "NetworkEngineImplement";
	protected static final String HTTP_POST = "POST";
	protected static final String HTTP_GET = "GET";
	private AsyncHttpClient mClient = null;
	private HashMap<NetworkRequest,RequestHandle> mRequest2HandelDict = null;
	
	protected class ResponseHandler extends AsyncHttpResponseHandler
	{
		protected NetworkRequest mRequest = null;
		protected INetworkEngineDelegate mDelegate = null;
		
		public ResponseHandler(NetworkRequest request)
		{
			mRequest = request;
			mDelegate = mRequest.mDelegate;
		}
		/* (non-Javadoc)
		 * @see com.loopj.android.http.AsyncHttpResponseHandler#onSuccess(int, org.apache.http.Header[], byte[])
		 */
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                byte[] responseBody)
        {
        	if(mDelegate!=null)
	        {
        		ByteBuffer byteBuff = ByteBuffer.wrap(responseBody);
	        	mDelegate.onRequestFinish(mRequest, byteBuff);
	        }
        }

		/* (non-Javadoc)
		 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFailure(int, org.apache.http.Header[], byte[], java.lang.Throwable)
		 */
        @Override
        public void onFailure(int statusCode, Header[] headers,
                byte[] responseBody, Throwable error)
        {
	        if(mDelegate!=null)
	        {
	        	//TODO 这里code跟原有代码的对应关系要处理
	        	mDelegate.onRequestFail(mRequest,statusCode);
	        }
	        
        }
        
        /* (non-Javadoc)
         * @see com.loopj.android.http.AsyncHttpResponseHandler#onProgress(int, int)
         */
        /*TODO android-async-http的发送和下载混在一起的回调，这里后续处理分开*/
        @Override
        public void onProgress(long bytesWritten, long totalSize)
        { 
        	//super.onProgress(bytesWritten, totalSize);
            if(mDelegate!=null && (mDelegate instanceof INetworkEngineDelegate.INetworkEngineDelegateOptional))
            {
            	((INetworkEngineDelegate.INetworkEngineDelegateOptional)mDelegate).onRequestProgress(mRequest, totalSize, bytesWritten);
            }
        }

		/* (non-Javadoc)
         * @see com.loopj.android.http.AsyncHttpResponseHandler#onStart()
         */
        @Override
        public void onStart()
        {
            //暂时不需要处理这个
            super.onStart();
        }
        
        /* (non-Javadoc)
         * @see com.loopj.android.http.AsyncHttpResponseHandler#onCancel()
         */
        @Override
        public void onCancel()
        {
        	mDelegate = null;
        	mRequest = null;
            super.onCancel();
        }
        
        /* (non-Javadoc)
         * @see com.loopj.android.http.AsyncHttpResponseHandler#onRetry(int)
         */
        @Override
        public void onRetry(int retryNo)
        {
            //Fired when a retry occurs, override to handle in your own code
            super.onRetry(retryNo);
        }
        
        /* (non-Javadoc)
         * @see com.loopj.android.http.AsyncHttpResponseHandler#onFinish()
         */
        @Override
        public void onFinish()
        {
        	//Notifies callback, that request was completed and is being removed from thread pool
        	//我们处理onSuccess和onFinish即可，这里不需要额外处理
        	//TODO 这样调用好么？直接调用外部类
    		if(mRequest2HandelDict.containsKey(mRequest))
    			mRequest2HandelDict.remove(mRequest);
    		else
    			Log.w(TAG, "Finish Request an goto delete，but cant find it");
        	mDelegate = null;
        	mRequest = null;
            super.onFinish();
        }
		
	}
	
	public NetworkEngineImplement()
	{
		mClient = new AsyncHttpClient();
		mClient.setTimeout(15 * 1000);
		//mClient = new SyncHttpClient();//server commnu做了线程池，我先用同步
		mClient.setMaxRetriesAndTimeout(2, 15*1000);
		mRequest2HandelDict = new HashMap<NetworkRequest, RequestHandle>();
	}
	
	protected boolean setRequestParamViaNetworkRequest(RequestParams param, NetworkRequest req)
	{
		try
		{
			if(param==null || req==null)
				return false;
			if(req.mDataDict!=null)
			{
				for (String key : req.mDataDict.keySet())
		        {
					String value = req.mDataDict.get(key);
//					String encodeKey = URLEncoder.encode(key, "utf-8");
//					String encodeValue = URLEncoder.encode(value, "utf-8");
					param.put(key, value);
		        }
			}
			if(req.mBinaryDataDict!=null)
			{
				Enumeration<String> keys = req.mBinaryDataDict.keys();
				while (keys.hasMoreElements())
				{
					String key = keys.nextElement();
					NetworkRequest.NetworkBinaryData binary = req.mBinaryDataDict.get(key);
					InputStream inputStream = new ByteArrayInputStream(binary.data.array());
					param.put(key,inputStream,binary.filename,binary.contentType);
				}
			}
			if(req.mFileDataDict!=null)
			{
				Enumeration<String> keys = req.mFileDataDict.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					NetworkRequest.NetworkFileData filedata = req.mFileDataDict.get(key);
					//File file = new File(path)
					try
		            {
			            InputStream inputStream = new FileInputStream(filedata.filepath);
			            param.put(key, inputStream, filedata.filename, filedata.contentType);
		            }
		            catch (FileNotFoundException e)
		            {
			            Log.e(TAG, "FileNotFoundException", e);
			            return false;
		            }
				}
			}
		}catch(Exception e)
		{
			Log.w(TAG, e.toString());
			return false;
		}
		return true;
	}
	public boolean addNetworkRequest(NetworkRequest request)
    {
		//创建handler
		ResponseHandlerInterface responseHandler = new ResponseHandler(request);
		//创建请求参数
		RequestParams params = new RequestParams();
		boolean paramResult = setRequestParamViaNetworkRequest(params,request);
		if(paramResult==false)
			return false;
		
		RequestHandle requestHandle = null; 
		if(request.mMethod.equalsIgnoreCase(HTTP_POST) || request.mBinaryDataDict!=null || request.mFileDataDict!=null)
			requestHandle = mClient.post(request.mUrl, params, responseHandler);
		else
			requestHandle = mClient.get(request.mUrl, params, responseHandler);
		
		if(requestHandle!=null)
		{
			mRequest2HandelDict.put(request, requestHandle);
			return true;
		}
		else
		{
			return false;
		}
    }
	
	public void cancelNetworkRequest(NetworkRequest request)
    {
		RequestHandle handle = mRequest2HandelDict.get(request);
		if(handle!=null)
		{
			handle.cancel(false);
			mRequest2HandelDict.remove(request);
		}
		else
			Log.w(TAG, "Cancel Request，but cant find it");
    }

	/* (non-Javadoc)
	 * @see com.tencent.ibg.foundation.network.INetworkEngineInterface#setClientName(java.lang.String)
	 */
    @Override
    public void setClientName(String clientName)
    {
    	//AsyncHttpClientConfig.AHC_CLIENT_NAME = clientName;	    
    }

	/* (non-Javadoc)
	 * @see com.tencent.ibg.foundation.network.INetworkEngineInterface#setClientVersion(java.lang.String)
	 */
    @Override
    public void setClientVersion(String clientVersion)
    {
    	//AsyncHttpClientConfig.AHC_VERSION = clientVersion;
    }
    
    /* (non-Javadoc)
     * @see com.tencent.ibg.foundation.network.INetworkEngineInterface#setCompressedEnable(java.lang.Boolean)
     */
    @Override
    public void setCompressedEnable(Boolean enable)
    {
        //AsyncHttpClientConfig.AHC_COMPRESS_ENABLE = enable;
    }
}
