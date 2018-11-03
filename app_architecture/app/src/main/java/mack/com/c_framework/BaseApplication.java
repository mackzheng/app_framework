package mack.com.c_framework;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application implements IBaseApplication {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }


    @Override
    public void initEnviroment() {

    }

    @Override
    public void initLog() {

    }

    @Override
    public void initNetWork() {

    }

    @Override
    public void initDataBase() {

    }

    @Override
    public void initImage() {

    }

    @Override
    public void initBugly() {

    }

    @Override
    public void initFileDownLoad() {

    }

    @Override
    public void initLeakCanary() {

    }

    @Override
    public void initThirdLib() {

    }
}
