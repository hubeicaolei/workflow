package cn.liley.hummer.wfcli.command;

import cn.liley.hummer.wfcli.Command;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLineParser;

import java.io.Console;

/**
 * Created by caolei on 2015/4/5.
 */
public class ShowStatus extends AbstractCommand {

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

       if (-1 == super.runCommand(console))
           return 0;

        //check work flow status;
        return 0;
    }

}
