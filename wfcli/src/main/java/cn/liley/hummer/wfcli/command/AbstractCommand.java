package cn.liley.hummer.wfcli.command;

import cn.liley.hummer.utils.logging.LogManager;
import cn.liley.hummer.wfcli.Command;

import org.apache.commons.cli.*;

import java.io.Console;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by caolei on 2015/4/5.
 */
 public  abstract class AbstractCommand implements Command {

    protected static String CLASS_NAME = "cn.liley.hummer.wfcli.command.AbstractCommand";
    protected Logger logger = LogManager.getLogger(CLASS_NAME);

    protected String name = "AbstractCommand";
    protected String description = "it's a abstract command,don't use it directly.";
    protected Options opts = new Options();
    protected String[] args = null;
    protected String error = "";

    protected CommandLine commandLine;

    protected AbstractCommand() {
        opts.addOption("h","help",false,description);
    }

    @Override
    public void addArgs(String[] args){
        String METHOD = "addArgs";

        logger.entering(CLASS_NAME,METHOD,args);
        this.args = args;
        CommandLineParser parser = new BasicParser();
        try {
            commandLine = parser.parse(this.opts, this.args);
        } catch (ParseException e) {
            logger.logp(Level.SEVERE, CLASS_NAME, METHOD, "parsing the parameters of the command " + name + " failed", e);
        }
        logger.exiting(CLASS_NAME,METHOD);
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }

    @Override
    public int runCommand(Console console){
        if(commandLine.hasOption("help")) {
            console.printf("%s", help());
            return -1;
        }
        return 0;
    }

    @Override
    public String getError(){
        return error;
    }

    @Override
    public boolean checkArg() {
        return true;
    }

}
