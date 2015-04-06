package cn.liley.hummer.wfcli;

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
            System.out.println(inputStream);
            logManager.readConfiguration(inputStream);
        } catch (SecurityException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
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
