package cn.cube.base.core.util;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:UrlBuilder
 * Author:zhanglida
 * Date:2017/6/23
 * Email:406504302@qq.com
 */
public class UrlBuilder {

    private String url ;
    private Map<String, String> map = new HashMap<>();

    public UrlBuilder(JSONObject jsonObject) {
        if (null == jsonObject) {
            return;
        }

        for (String key : jsonObject.keySet()) {
            map.put(key, String.valueOf(jsonObject.get(key)));
        }

    }

    public String build() throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();

        List<String> keys = new ArrayList<>(map.keySet());
        keys.sort(String.CASE_INSENSITIVE_ORDER);

        for (String key : keys) {
            String value = map.get(key);
            if (StringUtils.isEmpty(value)){
                continue;
            }
            value = URLEncoder.encode(value,"utf-8");
            builder.append(key).append("=").append(value).append("&");
        }
        String content = builder.substring(0, builder.length() - 1).toString();
        return content;
    }
}
