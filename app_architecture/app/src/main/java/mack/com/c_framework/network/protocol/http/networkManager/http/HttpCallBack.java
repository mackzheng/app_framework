package mack.com.c_framework.network.protocol.http.networkManager.http;


public abstract class HttpCallBack<Result> {

    public abstract void onSuccess(Result result);

    public abstract void onFailed(String error);

}
