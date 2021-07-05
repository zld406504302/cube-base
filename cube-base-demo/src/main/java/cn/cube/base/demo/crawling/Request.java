package cn.cube.base.demo.crawling;

import cn.cube.base.core.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

/**
 * Description:BaseRequest
 * Author:zhanglida
 * Date:2020/2/21
 * Email:406504302@qq.com
 */
public class Request<T> {
    private String query_hash = "bd33792e9f52a56ae8fa0985521d141d";
    private String variables;

    public String getQuery_hash() {
        return query_hash;
    }

    public void setQuery_hash(String query_hash) {
        this.query_hash = query_hash;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(T variables) {
        this.variables = JsonUtil.toJson(variables);
    }
    public Map<String, String> toMap() {
        Map<String, String> map = Maps.newHashMap();
        JSONObject jsonObject = JSON.parseObject(JsonUtil.toJson(this));
        Set<String> keySet = jsonObject.keySet();
        keySet.forEach(key -> map.put(key, jsonObject.getString(key)));
        return map;
    }
}

