package mack.com.c_framework;

public interface IBaseApplication {

    /**
     * 初始化APP环境：dev、pro、 test 环境
     * init app Enviroment
     */
    public void initEnviroment();

    /**
     * 初始化日志模块
     * init log
     */
    public void initLog();

    /**
     * init network
     */
    public void initNetWork();

    /**
     * init database
     */
    public void initDataBase();

    /**
     * init Image
     */
    public void initImage();

    /**
     * bugly
     */
    public void initBugly();

    /**
     * file download
     */
    public void initFileDownLoad();

    /**
     * leakCanary
     */
    public void initLeakCanary();

    /**
     * third library
     * wx
     * lbs
     * zxing
     * fasebook
     * report
     */
    public void initThirdLib();



}
