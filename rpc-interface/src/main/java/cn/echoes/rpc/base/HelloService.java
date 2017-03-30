package cn.echoes.rpc.base;

/**
 * -------------------------------------
 * 接口
 * -------------------------------------
 * Created by liutao on 2017/3/30 上午9:44.
 */
public interface HelloService {
    String hello(String string);

    String hello(Person person);
}
