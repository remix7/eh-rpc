package nio;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午2:24.
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8888;
        new Thread(new TimeClientHandle("127.0.0.1", port), "TIME CLOENT 001").start();
    }
}
