package cn.liley.hummer.launcher;

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
            tomcat.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
