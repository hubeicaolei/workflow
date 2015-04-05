package cn.liley.hummer.wfcli;

import java.io.Console;

/**
 * Created by caolei on 2015/4/4.
 */
public interface Command {

    void addArgs(String[] args);
    boolean checkArg();
    String getError();
    String help();
    int runCommand(Console console);
    String getDescription();
    String getName();

    void setName(String commandName);
}
