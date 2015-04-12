package cn.liley.hummer.ws.system;

import cn.liley.hummer.engine.Engine;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * Created by caolei on 2015/4/10.
 */
@At({"/system"})
public class SystemModule {

    @At({"/showstatus"})
    @Ok("json")
    public String showStatus(){
        return Engine.getInstance().getStatus();
    }
}
