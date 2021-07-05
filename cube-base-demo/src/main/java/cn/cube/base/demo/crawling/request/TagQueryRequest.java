package cn.cube.base.demo.crawling.request;

/**
 * Description:TagQueryRequest
 * Author:zhanglida
 * Date:2020/2/21
 * Email:406504302@qq.com
 */
public class TagQueryRequest{
    private String tag_name;
    private Integer first;
    private String after;
    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }
}
