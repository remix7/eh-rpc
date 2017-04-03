package nio;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午1:47.
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8888;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MULTIPLEXER TIME SERVER 001").start();
    }
}
