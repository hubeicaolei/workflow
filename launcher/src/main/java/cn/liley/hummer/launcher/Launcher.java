package cn.liley.hummer.launcher;

import cn.liley.hummer.engine.Engine;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * Created by caolei on 2015/3/24.
 */
public class Launcher {

    public static void main(String[] args){
        Launcher launcher = new Launcher();
        launcher.startup();
    }

    public void startup(){
        //start workflow engine
        workflowStartup();

        //start rest api interface
        tomcatStartup();

        //start message queue
    }

    private void workflowStartup() {
        Engine.getInstance().setStatus("Active");
    }


    private void tomcatStartup() {

        try{
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir("www");
            tomcat.setPort(3411);

            // Add AprLifecycleListener
            StandardServer server = (StandardServer)tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);

            tomcat.getHost().setAutoDeploy(true);

            tomcat.addWebapp("/rest-api","/rest-api.war");
            tomcat.enableNaming();
            tomcat.start();
            tomcat.getServer().await();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
