package cn.liley.hummer.wfcli.command;

import cn.liley.hummer.utils.webservice.WebServiceManager;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by caolei on 2015/4/5.
 */
public class ShowStatus extends AbstractCommand {

    String CLASS_NAME = "cn.liley.hummer.wfcli.command.ShowStatus";
    public ShowStatus() {
        description = "Display the status of the workflow.";
    }
    @Override
    public void addArgs(String[] args) {
        super.addArgs(args);

    }

    @Override
    public String help() {
        return description;
    }

    @Override
    public int runCommand(Console console) {

        String METHOD = "runCommand";
       if (-1 == super.runCommand(console))
           return 0;

        //check work flow status;
        try {
            String json = WebServiceManager.getInstance().httpGet("/system/showstatus");
            Map map = Json.fromJson(HashMap.class, Lang.inr(json));
            String status = (String)map.get("status");
            console.printf("%s",status);
        }catch(Exception e){
            logger.logp(Level.SEVERE,CLASS_NAME,METHOD,"",e);
        }
        return 0;
    }

}
