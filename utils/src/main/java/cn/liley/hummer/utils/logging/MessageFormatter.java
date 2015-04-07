package cn.liley.hummer.utils.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by caolei on 2015/4/7.
 */
public class MessageFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer();
        //time
        Date date = new Date(record.getMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
        sb.append(sdf.format(date));
        sb.append(" ");

        //level
        sb.append(record.getLevel().getName());
        sb.append(" ");

        //message
        sb.append(record.getMessage());
        sb.append(" ");

        //class name and method name
        sb.append("method=" + record.getSourceClassName() + "." + record.getSourceMethodName());
        sb.append(" ");

        //thread
        ThreadInfo threadInfo = ManagementFactory.getThreadMXBean().getThreadInfo(record.getThreadID());
        if(null != threadInfo)
            sb.append("thread=" + threadInfo.getThreadName() + "(" + record.getThreadID() + ")");
        else
            sb.append("thread=null");
        sb.append(" ");

        //logger name
        sb.append("logger=" + record.getLoggerName());

        //exception
        Throwable e = record.getThrown();
        if(null != e)
        {
            StringWriter sw = null;
            try {
                sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                sb.append(sw.toString());
            } catch (Exception e2) {
            }finally {
                if(null != sw)
                {
                    try{
                        sw.close();
                    }catch (Exception e3){}
                }
            }
        }
        return sb.toString();
    }
}
