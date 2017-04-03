package callback;


/**
 * -------------------------------------
 * Fetcher 回调方法
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午5:58.
 */
public interface FetcherCallBack {
    void doDate(Data data) throws Exception;

    void onError(Throwable cause);
}
