package callback;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午6:02.
 */
public class MyFetcher implements Fetcher {
    final Data data;

    public MyFetcher(Data data) {
        System.out.println("My Fetcher 构造方法被调用");
        this.data = data;
    }

    @Override
    public void fetchData(FetcherCallBack callBack) {
        try {
            System.out.println("调用Fetcher的正常方法");
            callBack.doDate(data);
        } catch (Exception e) {
            System.out.println("调用Fetcher的错误方法");
            callBack.onError(e);
        }
    }
}
