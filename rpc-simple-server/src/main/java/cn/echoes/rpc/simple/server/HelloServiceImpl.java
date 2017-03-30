package cn.echoes.rpc.simple.server;

import cn.echoes.rpc.base.HelloService;
import cn.echoes.rpc.base.Person;
import cn.echoes.rpc.server.RpcService;

/**
 * -------------------------------------
 * 接口实现类
 * -------------------------------------
 * Created by liutao on 2017/3/30 上午10:33.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String hello(String string) {
        return string;
    }

    public String hello(Person person) {
        return person.toString();
    }
}
