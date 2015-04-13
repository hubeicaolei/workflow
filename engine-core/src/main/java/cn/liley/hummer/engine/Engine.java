package cn.liley.hummer.engine;

import cn.liley.hummer.utils.logging.LogManager;

import java.util.logging.Logger;

/**
 * Created by caolei on 2015/4/12.
 */
public class Engine {

    String CLASS_NAME = this.getClass().getName();
    Logger logger = LogManager.getLogger(CLASS_NAME);

    private static Engine instance = new Engine();
    private String status = "Inactive";

    private Engine() {
        String METHOD = "engine";
        logger.entering(CLASS_NAME,METHOD,"new engine is created");
    }

    public static Engine getInstance(){
        return instance;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
