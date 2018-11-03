package mack.com.c_framework.network.protocol.http.networkengine;

import java.nio.ByteBuffer;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class NetworkRequest
{
	//private static final String TAG = "NetworkRequest";
	
	enum NETWORK_REQUEST_PRIORITY
	{
		HIGH_NETWORK_REQUEST_PRIORITY, NORMAL_NETWORK_REQUEST_PRIORITY, LOW_NETWORK_REQUEST_PRIORITY,
	}

	enum PostFormat
	{
		MultipartFormDataPostFormat, URLEncodedPostFormat,
	}

	public class NetworkBinaryData
	{
		ByteBuffer data = null;
		String filename = null;
		String contentType = null;
		String key = null;
	}

	public class NetworkFileData
	{
		String filepath = null;
		String filename = null;
		String contentType = null;
		String key = null;
	}

	public static final int UNKNOWN_LENGTH = -1;

	public String mUrl = null;
	public String mMethod = null;
	public Map<String, String> mDataDict = null;
	public PostFormat mPostFormat = null;
	public Dictionary<String, NetworkBinaryData> mBinaryDataDict = null;
	public Dictionary<String, NetworkFileData> mFileDataDict = null;
	public Dictionary<String, String> mCookieDict = null;
	public Dictionary<String, String> mHeaderDict = null;
	public NETWORK_REQUEST_PRIORITY mPriority = null;
	public INetworkEngineDelegate mDelegate = null;
//	public AsyncHttpClient mConnectionClient = null;
//	public Request mRequest = null;
//	public NetworkHandler mHandler = null;
//	public Looper mCallerLooper = null;

	public int mReceivedLength = 0;
	public int mContentLength = UNKNOWN_LENGTH;

	// protected static int MAX_BYTEBUFFER_SIZE = 400 *1024 * 8;

//	public static class NetworkHandler extends
//	        AsyncCompletionHandler<NetworkResponse>
//	{
//		public NetworkRequest mRequest = null;
//		public NetworkResponse mResponse = null;
//		private static final String TAG = "NetworkHandler";
//
//		enum AsynHttpMsgHandlerType
//		{
//			AHMHT_StatusReceived, AHMHT_HeadersReceived, AHMHT_BodyPartReceived, AHMHT_Completed, AHMHT_TimeOut, AHMHT_Throwable
//		}
//
//		protected static void onLooperStatusReceived(NetworkHandler hd,
//		        final HttpResponseStatus status)
//		{
//			// TODO:可能需要完善其他逻辑
//			int statusCode = status.getStatusCode();
//			if(statusCode != 200)
//			{
//				TCLogger.w(TAG, String.format("header status not 200 received: %d, url:%s", statusCode, hd.mRequest.mUrl));
//			}
//		}
//
//		protected static void onLooperHeadersReceived(NetworkHandler hd,
//		        final HttpResponseHeaders headers)
//		{
//			try
//			{
//				Map<String, List<String>> headMap = headers.getHeaders();
//				List<String> lengthList = headMap==null?null:headMap.get("Content-Length") ;
//				String len = (lengthList!=null && lengthList.size()>0)?lengthList.get(0):null;
//				if (len != null)
//				{
//					hd.mRequest.mReceivedLength = 0;
//					hd.mRequest.mContentLength = Integer.parseInt(len);
//				}
//				//TCLogger.d(TAG, String.format("header received: %s", headMap.toString()));
//			}
//			catch (Exception e)
//			{
//				TCLogger.e(TAG, "onLooperHeadersReceived exception", e);
//				hd.mRequest.mReceivedLength = 0;
//				hd.mRequest.mContentLength = UNKNOWN_LENGTH;
//			}
//		}
//
//		protected static void onLooperBodyPartReceived(NetworkHandler hd,
//		        final HttpResponseBodyPart content)
//		{
//			if (hd.mRequest != null && hd.mRequest.mDelegate != null)
//			{
//				INetworkEngineDelegate delegate = hd.mRequest.mDelegate;
//				if (delegate instanceof INetworkEngineDelegateOptional)
//				{
//					INetworkEngineDelegateOptional delegateOptional = (INetworkEngineDelegateOptional) delegate;
//					hd.mRequest.mReceivedLength += content.length();
//					delegateOptional
//					        .onRequestProgress(
//					                hd.mRequest,
//					                hd.mRequest.mContentLength == UNKNOWN_LENGTH ? 1000000
//					                        : hd.mRequest.mContentLength,
//					                hd.mRequest.mReceivedLength
//					                );
//				}
//			}
//		}
//
//		protected static void onLooperCompleted(NetworkHandler hd,
//		        NetworkResponse response)
//		{
//			if (hd.mRequest != null && hd.mRequest.mDelegate != null)
//			{
//				// String string = new
//				// String(response.getResponseBodyAsBytes());
//				INetworkEngineDelegate delegate = hd.mRequest.mDelegate;
//				try
//				{
//					if (hd.mResponse.mResponse == null)
//					{
//						delegate.onRequestFail(hd.mRequest, 400);
//					}
//					else if (hd.mResponse.mResponse.getStatusCode() >= 400
//					        || hd.mResponse.mResponse.getStatusCode() < 0)
//					{
//						delegate.onRequestFail(hd.mRequest,
//						        hd.mResponse.mResponse.getStatusCode());
//					}
//					else
//					{
//						delegate.onRequestFinish(hd.mRequest, ByteBuffer
//						        .wrap(hd.mResponse.mResponse
//						                .getResponseBodyAsBytes()));
//					}
//				}
//				catch (IOException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//
//		protected static void onLooperTimeOut(NetworkHandler hd,
//		        NetworkRequest request)
//		{
//			// TODO: 可能需要完善其他逻辑
//			if (request != null && request.mDelegate != null)
//			{
//				INetworkEngineDelegate delegate = request.mDelegate;
//				// "408" : Request Time-out
//				delegate.onRequestFail(request, 408);
//			}
//		}
//		
//		protected static void onLooperThrowable(NetworkHandler hd,
//		        int errorCode)
//		{
//			TCLogger.d(TAG, "onLooperThrowable errorCode:"+ errorCode);
//			if (hd.mRequest != null && hd.mRequest.mDelegate != null)
//			{
//				INetworkEngineDelegate delegate = hd.mRequest.mDelegate;
//				if(delegate != null)
//				{
//					delegate.onRequestFail(hd.mRequest, errorCode);
//				}
//			}
//		}
//
//		@SuppressLint("HandlerLeak")
//		protected class AsynHttpMsgHandler extends Handler
//		{
//			public AsynHttpMsgHandler(Looper looper)
//			{
//				super(looper);
//			}
//
//			public AsynHttpMsgHandlerType type = null;
//			public HttpResponseStatus status = null;
//			public HttpResponseHeaders headers = null;
//			public HttpResponseBodyPart content = null;
//			public NetworkResponse mResponse = null;
//			public INetworkEngineDelegate mDelegate = null;
//			public int mErrorCode = -1;
//
//			@Override
//			public void handleMessage(Message msg)
//			{ // 处理消息
//				if (msg.obj instanceof NetworkHandler)
//				{
//					NetworkHandler hd = (NetworkHandler) msg.obj;
//					switch (type)
//					{
//					case AHMHT_StatusReceived:
//						onLooperStatusReceived(hd, status);
//						break;
//					case AHMHT_HeadersReceived:
//						onLooperHeadersReceived(hd, headers);
//						break;
//					case AHMHT_BodyPartReceived:
//						onLooperBodyPartReceived(hd, content);
//						break;
//					case AHMHT_Completed:
//						onLooperCompleted(hd, mResponse);
//						break;
//					case AHMHT_TimeOut:
//						onLooperTimeOut(hd, mRequest);
//						break;
//					case AHMHT_Throwable:
//						onLooperThrowable(hd, mErrorCode);
//						break;
//
//					default:
//						break;
//					}
//				}
//				
//				msg.obj = null;
//				msg = null;
//			}
//		}
//		
//		/**
//		 * {@inheritDoc}
//		 */
//		public void onThrowable(Throwable t)
//		{
//			super.onThrowable(t);
//			
//			// 网络出错啦！
//			TCLogger.e(TAG, "Network Request onThrowable ", t);
//			int errorCode = -1;
//			
//			if (t instanceof TimeoutException || t instanceof SocketTimeoutException ) 
//			{
//				errorCode = NetworkError.NETWORK_CONNECT_TIMEOUT;
//            }
//			//UnknownHostException,ConnectException,SSLHandshakeException
//			else if (t instanceof ConnectionClosedException) 
//			{
//				errorCode = NetworkError.NETWORK_CONNECT_FAILED;
//            }
//			else 
//			{
//				errorCode = NetworkError.NETWORK_UNAVAILABLE;
//			}
//			
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_Throwable;
//				handler.mErrorCode = errorCode;
//	
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperThrowable(mRequest.mHandler, errorCode);
//			}
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		public STATE onStatusReceived(final HttpResponseStatus status)
//		        throws Exception
//		{
//			STATE sts = super.onStatusReceived(status);
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_StatusReceived;
//				handler.status = status;
//
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperStatusReceived(this, status);
//			}
//			return sts;
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		public STATE onHeadersReceived(final HttpResponseHeaders headers)
//		        throws Exception
//		{
//			STATE sts = super.onHeadersReceived(headers);
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_HeadersReceived;
//				handler.headers = headers;
//
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperHeadersReceived(this, headers);
//			}
//			return sts;
//		}
//
//		public STATE onBodyPartReceived(final HttpResponseBodyPart content)
//		        throws Exception
//		{
//			STATE sts = super.onBodyPartReceived(content);
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_BodyPartReceived;
//				handler.content = content;
//
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperBodyPartReceived(this, content);
//			}
//			return sts;
//		}
//
//		@Override
//		public NetworkResponse onCompleted(Response response) throws Exception
//		{
//			Dictionary<String, String> headers = new Hashtable<String, String>();
//			if (response != null)
//			{
//				for (String key : response.getHeaders().keySet())
//				{
//					String valuesString = "";
//					List<String> valueList = response.getHeaders().get(key);
//					for (String value : valueList)
//					{
//						valuesString = String.format("%s %s", valuesString,
//						        value);
//					}
//					headers.put(key, valuesString);
//				}
//				mResponse = new NetworkResponse(response.getStatusCode(),
//				        headers);
//			}
//			else
//			{
//				mResponse = new NetworkResponse(400, headers);
//			}
//
//			mResponse.mRequest = mRequest;
//			mResponse.mResponse = response;
//
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_Completed;
//				handler.mResponse = mResponse;
//
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperCompleted(this, mResponse);
//			}
//
//			return mResponse;
//		}
//
//		/**
//		 * 
//		 * 会在外部调用
//		 * @param request
//		 */
//		public void onTimeOut(NetworkRequest request)
//		{
//			if (mRequest.mCallerLooper != null)
//			{
//				AsynHttpMsgHandler handler = new AsynHttpMsgHandler(
//				        mRequest.mCallerLooper);
//				handler.type = AsynHttpMsgHandlerType.AHMHT_TimeOut;
//
//				handler.removeMessages(0);
//				Message m = handler.obtainMessage(1, 0, 0, this);
//				handler.sendMessage(m);
//			}
//			else
//			{
//				onLooperTimeOut(this, request);
//			}
//		}
//	}

	public NetworkRequest(String url, String method,
	        INetworkEngineDelegate delegate)
	{
		super();
		mUrl = new String(url);
		mMethod = new String(method);
		mDelegate = delegate;
	}

	public NetworkRequest(String url, String method,
	        NETWORK_REQUEST_PRIORITY priority, INetworkEngineDelegate delegate)
	{
		super();
		mUrl = new String(url);
		mMethod = new String(method);
		mDelegate = delegate;
		mPriority = priority;
	}

	public NetworkRequest(String url, String method, Map<String, String> data,
	        INetworkEngineDelegate delegate)
	{
		super();
		mUrl = new String(url);
		mMethod = new String(method);
		mDelegate = delegate;
		mDataDict = data;
	}

	public NetworkRequest(String url, String method, Map<String, String> data,
	        NETWORK_REQUEST_PRIORITY priority, INetworkEngineDelegate delegate)
	{
		super();
		mUrl = new String(url);
		mMethod = new String(method);
		mDelegate = delegate;
		mPriority = priority;
		mDataDict = data;
	}

	static byte[] newBytesByAppending(byte[] src, byte[] appended)
	{
		int len = 0;
		int pos = 0;
		if (src != null)
		{
			len += src.length;
		}
		if (appended != null)
		{
			len += appended.length;
		}
		if (len <= 0)
		{
			return null;
		}
		byte[] ret = new byte[len];
		if (src != null)
		{
			for (int i = 0; i < src.length; i++)
			{
				ret[pos + i] = src[i];
			}
			pos += src.length;
		}
		if (appended != null)
		{
			for (int i = 0; i < appended.length; i++)
			{
				ret[pos + i] = appended[i];
			}
			pos += appended.length;
		}
		return ret;
	}

//	public AsyncHttpClient getConnectionClient()
//	{
//		return mConnectionClient;
//	}
//
//	public AsyncHttpClient makeURLConnectionConnection(
//	        Object urlConnectionDelegate)
//	{
//		if (mConnectionClient != null)
//		{
//			mConnectionClient = null;
//		}
//		if (mRequest != null)
//		{
//			mRequest = null;
//		}
//
//		RequestBuilder builder = new RequestBuilder();
//		// builder.setPerRequestConfig(perRequestConfig)
//		if (mUrl != null)
//		{
//			builder = builder.setUrl(mUrl);
//		}
//		if (mMethod != null)
//		{
//			builder = builder.setMethod(mMethod);
//		}
//		if (mHeaderDict != null && !mHeaderDict.isEmpty())
//		{
//			Enumeration<String> keys = mHeaderDict.keys();
//			while (keys.hasMoreElements())
//			{
//				String key = (String) keys.nextElement();
//				String value = mHeaderDict.get(key);
//				builder.setHeader(key, value);
//			}
//		}
//
//		if (mCookieDict != null && !mCookieDict.isEmpty())
//		{
//			String cookie = null;
//			Enumeration<String> keys = mCookieDict.keys();
//			while (keys.hasMoreElements())
//			{
//				String key = (String) keys.nextElement();
//				String value = mCookieDict.get(key);
//				if (cookie == null)
//				{
//					cookie = String.format("%s=%s", key, value);
//				}
//				else
//				{
//					cookie = String.format("%s; %s=%s", cookie, key, value);
//				}
//			}
//			builder.setHeader("Cookie", cookie);
//		}
//
//		boolean isOnlyStringKeyValue = (mBinaryDataDict == null || mBinaryDataDict
//		        .isEmpty())
//		        && (mFileDataDict == null || mFileDataDict.isEmpty());
//
//		if (isOnlyStringKeyValue)
//		{
//			String dataString = null;
//			if (mDataDict != null && !mDataDict.isEmpty())
//			{
//				Set<String> keySet = mDataDict.keySet();
//
//				if ("GET".equalsIgnoreCase(mMethod))
//				{
//					//GET的参数交由Request生成
//					dataString = "";
//				}
//				else //POST
//				{
//					Iterator<String> keyIterator = keySet.iterator();
//					while (keyIterator.hasNext())
//					{
//						String key = (String) keyIterator.next();
//						String value = mDataDict.get(key);
//						key = UTF8UrlEncoder.encode(key);
//						try
//                        {
//							//这里使用系统的编码库，ning的编码库有些问题，emoji编码会出错
//							value = URLEncoder.encode(value,"utf-8");
//                        }
//                        catch (UnsupportedEncodingException e)
//                        {
//	                        TCLogger.e(TAG, "UnsupportedEncodingException", e);
//	                        //如果系统编码失败，还是用回ning，双保险
//	                        value = UTF8UrlEncoder.encode(value);
//                        }
//						if (dataString == null)
//						{
//							dataString = String.format("%s=%s", key, value);
//						}
//						else
//						{
//							dataString = String.format("%s&%s=%s", dataString,
//							        key, value);
//						}
//					}
//				}
//			}
//
//			if (dataString != null)
//			{
//				if ("POST".equalsIgnoreCase(mMethod))
//				{
//					String charset = "utf-8";
//					String header = String.format(
//					        "application/x-www-form-urlencoded; charset=%s",
//					        charset);
//					builder.setHeader("Content-Type", header);
//					builder.setBody(dataString);
//					TCLogger.i(TAG, "Post Request url = " + mUrl + ", data:" + dataString);
//				}
//				else
//				{
//					String newUrl = mUrl;
//					newUrl = String.format("%s%s", newUrl, dataString);
//					builder = builder.setUrl(newUrl);
//					TCLogger.i(TAG, "Get Request url = " + newUrl);
//				}
//			}
//		}
//		else
//		{
//			// 有二进制数据，即使设置方法为HttpGet，也会强制修改为HttpPost
//			builder.setMethod("POST");
//
//			String charset = "utf-8";
//			String stringBoundary = "0xKhTmLbOuNdArY";
//			builder.setHeader("Content-Type", String.format(
//			        "multipart/form-data; charset=%s; boundary=%s", charset,
//			        stringBoundary));
//
//			byte[] httpBodyData = null;
//			String tmpString = String.format("--%s\r\n", stringBoundary);
//			httpBodyData = newBytesByAppending(httpBodyData,
//			        tmpString.getBytes());
//
//			String endItemBoundary = String.format("\r\n--%s\r\n",
//			        stringBoundary);
//			String allItemsEndBoundary = String.format("\r\n--%s--\r\n",
//			        stringBoundary);
//
//			if (mDataDict != null && !mDataDict.isEmpty())
//			{
//				Set<String> keySet = mDataDict.keySet();
//				Iterator<String> keyIterator = keySet.iterator();
//				while (keyIterator.hasNext())
//				{
//					String key = (String) keyIterator.next();
//					String value = mDataDict.get(key);
//					String contentDipsotion = String
//					        .format("Content-Disposition: form-data; name=\"%s\"\r\n\r\n",
//					                key);
//					httpBodyData = newBytesByAppending(httpBodyData,
//					        contentDipsotion.getBytes());
//					httpBodyData = newBytesByAppending(httpBodyData,
//					        value.getBytes());
//					if (keyIterator.hasNext() || mBinaryDataDict != null
//					        || mFileDataDict != null)
//					{
//						// 还有数据要添加
//						httpBodyData = newBytesByAppending(httpBodyData,
//						        endItemBoundary.getBytes());
//					}
//				}
//			}
//
//			if (mBinaryDataDict != null && !mBinaryDataDict.isEmpty())
//			{
//				Enumeration<String> keys = mBinaryDataDict.keys();
//				while (keys.hasMoreElements())
//				{
//					String key = (String) keys.nextElement();
//					NetworkBinaryData value = mBinaryDataDict.get(key);
//					tmpString = String
//					        .format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n",
//					                key, value.filename);
//					httpBodyData = newBytesByAppending(httpBodyData,
//					        tmpString.getBytes());
//					tmpString = String.format("Content-Type: %s\r\n\r\n",
//					        value.contentType);
//					httpBodyData = newBytesByAppending(httpBodyData,
//					        tmpString.getBytes());
//					httpBodyData = newBytesByAppending(httpBodyData,
//					        value.data.array());
//					if (keys.hasMoreElements() || mFileDataDict != null)
//					{
//						// 还有数据要添加
//						httpBodyData = newBytesByAppending(httpBodyData,
//						        endItemBoundary.getBytes());
//					}
//				}
//			}
//
//			if (mFileDataDict != null && !mFileDataDict.isEmpty())
//			{
//				Enumeration<String> keys = mFileDataDict.keys();
//				while (keys.hasMoreElements())
//				{
//					String key = (String) keys.nextElement();
//					NetworkFileData value = mFileDataDict.get(key);
//					if (value.filepath != null && value.filepath.length() > 0)
//					{
//						ByteBuffer filedataBuffer = ByteBuffer.allocate(4096);
//						byte[] fileBytes = null;
//						{
//							FileChannel fcin = null;
//							try
//							{
//								fcin = new FileInputStream(value.filepath)
//								        .getChannel();
//								while (fcin.read(filedataBuffer) != -1)
//								{
//									// 读文件
//									fileBytes = newBytesByAppending(fileBytes,
//									        filedataBuffer.array());
//								}
//							}
//							catch (FileNotFoundException e)
//							{
//								TCLogger.e(TAG, "FileInputStream", e);
//							}
//							catch (IOException e)
//							{
//								TCLogger.e(TAG, "FileInputStream", e);
//							}
//							finally
//							{
//								if (fcin != null && fcin.isOpen())
//								{
//									try
//                                    {
//	                                    fcin.close();
//                                    }
//                                    catch (IOException e)
//                                    {
//                                    	TCLogger.e(TAG, "FileInputStream", e);
//                                    }
//								}
//								fcin = null;
//							}
//
//						}
//						tmpString = String
//						        .format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\n",
//						                key, value.filename);
//						httpBodyData = newBytesByAppending(httpBodyData,
//						        tmpString.getBytes());
//						tmpString = String.format("Content-Type: %s\r\n\r\n",
//						        value.contentType);
//						httpBodyData = newBytesByAppending(httpBodyData,
//						        tmpString.getBytes());
//						httpBodyData = newBytesByAppending(httpBodyData,
//						        fileBytes);
//						fileBytes = null;
//						if (keys.hasMoreElements())
//						{
//							// 还有数据要添加
//							httpBodyData = newBytesByAppending(httpBodyData,
//							        endItemBoundary.getBytes());
//						}
//					}
//				}
//			}
//
//			httpBodyData = newBytesByAppending(httpBodyData,
//			        allItemsEndBoundary.getBytes());
//			builder.setBody(httpBodyData);
//			//post大图片时，new String(httpBodyData)容易造成oom，暂时注释掉post时的日志打印
////			TCLogger.d("Post Data", new String(httpBodyData));
//		}
//
//		mRequest = builder.build();
//		mHandler = new NetworkHandler();
//		mHandler.mRequest = this;
//
//		mConnectionClient = new AsyncHttpClient();
//		return mConnectionClient;
//	}

	public void setStringData(String data, String key)
	{
		if (mDataDict == null)
		{
			mDataDict = new HashMap<String, String>();
		}
		mDataDict.put(key, data);
	}

	public void setBinaryData(ByteBuffer data, String key)
	{
		this.setBinaryData(data, "file", null, key);
	}

	public void setBinaryData(ByteBuffer data, String fileName,
	        String contentType, String key)
	{
		NetworkBinaryData nd = new NetworkBinaryData();
		nd.data = data;
		nd.filename = fileName;
		nd.contentType = contentType;
		nd.key = key;
		if (mBinaryDataDict == null)
		{
			mBinaryDataDict = new Hashtable<String, NetworkBinaryData>();
		}
		mBinaryDataDict.put(key, nd);
	}

	public void setFileData(String filePath, String key)
	{
		this.setFileData(filePath, "file", null, key);
	}

	public void setFileData(String filePath, String fileName,
	        String contentType, String key)
	{
		NetworkFileData nd = new NetworkFileData();
		nd.filepath = filePath;
		nd.filename = fileName;
		nd.contentType = contentType;
		nd.key = key;
		if (mFileDataDict == null)
		{
			mFileDataDict = new Hashtable<String, NetworkFileData>();
		}
		mFileDataDict.put(key, nd);
	}

	public void setHeader(String value, String key)
	{
		if (mHeaderDict == null)
		{
			mHeaderDict = new Hashtable<String, String>();
		}
		mHeaderDict.put(key, value);
	}

	public void setCookie(String value, String key)
	{
		if (mCookieDict == null)
		{
			mCookieDict = new Hashtable<String, String>();
		}
		mCookieDict.put(key, value);
	}
}
