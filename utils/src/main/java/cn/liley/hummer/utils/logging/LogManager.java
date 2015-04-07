package cn.liley.hummer.utils.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created by caolei on 2015/4/6.
 */
public class LogManager {

    // 初始化LogManager
    static {
        InputStream inputStream = null;
        java.util.logging.LogManager logManager = java.util.logging.LogManager.getLogManager();
        try {
            inputStream = new FileInputStream(new File(System.getProperty("log.configurationFile","conf/log.properties")));
            logManager.readConfiguration(inputStream);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != inputStream)
            {
                try{
                    inputStream.close();
                }catch(Exception e){}
            }
        }
    }

    /**
     * 获取日志对象
     *
     * @param clazz
     * @return
     */
    public static Logger getLogger(String clazz) {
        Logger logger = Logger.getLogger(clazz);
        return logger;
    }
}
