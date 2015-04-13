package cn.liley.hummer.ws.system;

import cn.liley.hummer.engine.Engine;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caolei on 2015/4/10.
 */
@At({"/system"})
public class SystemModule {

    @At({"/showstatus"})
    @Ok("json")
    public Map showStatus(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("status", Engine.getInstance().getStatus());
        return map;
    }
}
