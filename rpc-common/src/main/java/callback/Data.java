package callback;

/**
 * -------------------------------------
 * TODO
 * -------------------------------------
 * Created by liutao on 2017/4/3 下午6:03.
 */
public class Data {
    private int m;
    private int n;

    public Data(int m, int n) {
        this.m = m;
        this.n = n;
    }

    @Override
    public String toString() {
        return "Data{" +
                "m=" + m +
                ", n=" + n +
                '}';
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
