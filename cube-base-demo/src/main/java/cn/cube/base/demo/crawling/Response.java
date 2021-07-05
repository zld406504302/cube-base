package cn.cube.base.demo.crawling;

/**
 * Description:Response
 * Author:zhanglida
 * Date:2020/2/21
 * Email:406504302@qq.com
 */
public class Response<T> {
    private T  Data;
    private String status;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
