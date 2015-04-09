package cn.liley.hummer.wfcli;

import cn.liley.hummer.utils.logging.LogManager;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by caolei on 2015/4/2.
 */
public class CommandShell implements Runnable{

    private Logger logger = LogManager.getLogger(this.getClass().getName());
    private final String CLASS_NAME = "CommandShell";

    private final String COMMAND_SIGN ="wfcli>";

    private final String HELP = "help";
    
    //the file stored commands
    private final String COMMAND_FILE_NAME = "conf/cli.properties";

    private final HashMap<String, Command> commands = new HashMap<String, Command>();

    public static void main(String[] args){

        CommandShell cs = new CommandShell();
        try {
            //init shell console
            cs.initShell();

            cs.run();
        }catch (Exception e)
        {
            System.out.print("wfcli exited for an error, pls check the error log for the detail");
            System.exit(-1);
        }
        System.exit(-2);

    }

    private void showCommandSign(){
        System.out.print(COMMAND_SIGN);
    }

    /**
     * create a shell ui
     */
    private void initShell() throws IOException {
        String METHOD = "initShell";
        logger.entering(CLASS_NAME, METHOD);
        String starting = "Starting wfcli...........\n"
                        + "Initialize command.......";
        String weclome = "Welcome to Work Flow Command Line Console!";

        System.out.println(starting);
        //init the command list
        initCommands();

        System.out.println(weclome);
        showCommandSign();
        logger.exiting(CLASS_NAME, METHOD);

    }

    /**
     * load/initialize command list
     */
    private void initCommands() throws IOException {
        String METHOD = "initCommands";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(COMMAND_FILE_NAME));
            Properties props = new Properties();
            props.load(fis);
            Enumeration en = props.propertyNames();
            while(en.hasMoreElements()){
                String commandName = (String)en.nextElement();
                String commandClass = props.getProperty(commandName);

                try{
                    Command command = (Command) Class.forName(commandClass).newInstance();
                    command.setName(commandName);
                    commands.put(commandName, command);
                } catch (Exception e) {
                    logger.logp(Level.SEVERE, CLASS_NAME, METHOD,"command " + commandName + ":" + commandClass + " can not be initialized", e);
                    continue;
                }
            }

        }catch(IOException e){
            logger.logp(Level.SEVERE, CLASS_NAME, METHOD, "command file can not be loaded, the wfcli will exist", e);
            throw e;
        }finally {
            if(null != fis)
            {
                try{
                    fis.close();
                }catch (Exception e){
                }
            }
        }
    }

    @Override
    public void run() {

        String METHOD ="run";
        logger.entering(CLASS_NAME,METHOD);
        Console console = System.console();
        if(null == console)
        {
            logger.logp(Level.FINEST,CLASS_NAME,METHOD,"can not get console, pls run wfcli in the command line console");
            return;
        }


        while(true){
            String commandStr = console.readLine();
            console.flush();
            logger.logp(Level.FINEST,CLASS_NAME, METHOD,"command string is :" + commandStr);
            if(null != commandStr)
            {
                commandStr = commandStr.trim();
                if(0 == commandStr.length())
                {
                    showCommandSign();
                    continue;
                }
                if(commandStr.equalsIgnoreCase(HELP))
                {
                    showCommandList(console);
                    console.flush();
                    showCommandSign();
                    continue;
                }
                Scanner scanner = new Scanner(commandStr);

                List<String> argList = new ArrayList<String>();
                while(scanner.hasNext())
                {
                    argList.add(scanner.next());
                }
                if(0 >= argList.size())
                {
                    logger.logp(Level.FINEST,CLASS_NAME, METHOD,"Not found the parameters");
                    showCommandSign();
                    continue;
                }
                String[] args = argList.toArray(new String[0]);
                Command command = getCommand(args[0]);
                if(null == command)
                {
                    logger.logp(Level.FINER, CLASS_NAME, METHOD,"can not found the command:" + args[0]);
                    showHelp(console);
                    console.flush();
                    showCommandSign();
                    continue;
                }


                command.addArgs(args);
                if(!command.checkArg()) {
                    console.printf("%s\n", command.getError());
                    showCommandSign();
                    continue;
                }
                command.runCommand(console);
                console.printf("\n");
                showCommandSign();
            }

        }
    }

    private void showCommandList(Console console) {
        String METHOD = "showCommandList";
        logger.entering(CLASS_NAME,METHOD);
        if(null == commands)
        {
            logger.logp(Level.FINEST,CLASS_NAME,METHOD,"Not found all of commands,pls check the command configuration file");
            console.printf("%s\n", "Not found any command, pls re-enter wfcli or contact on administrator");
        }

        Iterator<String> keys = commands.keySet().iterator();
        while(keys.hasNext())
        {
            Command command = commands.get(keys.next());
            console.printf("%s - %s\n", command.getName(), command.getDescription());
        }
        logger.exiting(CLASS_NAME,METHOD);

    }

    private void showHelp(Console console) {
        String help = "Not found the command, pls enter 'help' to get all commands";
        console.printf("%s\n", help);
    }

    private Command getCommand(String commandName) {
//        String METHOD = "getCommand";
        if(null == commands || !commands.containsKey(commandName))
            return null;
        return commands.get(commandName);

    }
}
