package cn.liley.hummer.ws;

import cn.liley.hummer.ws.system.SystemModule;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Modules;

/**
 * Created by caolei on 2015/4/10.
 */
@Modules({ SystemModule.class})
@At({"/v1"})
public class MainModule {

}
