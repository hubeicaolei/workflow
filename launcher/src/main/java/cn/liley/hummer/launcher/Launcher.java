package cn.liley.hummer.launcher;

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

        //start rest api interface
        tomcatStartup();
        //start command line
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

            tomcat.addWebapp("/rest-api","www/webapps/rest-api.war");
            tomcat.enableNaming();
            tomcat.start();
            tomcat.getServer().await();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
