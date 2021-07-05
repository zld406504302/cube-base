package cn.cube.base.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

/**
 * 工具类(Json)
 * <p>
 * Example：自定义属性名称、排序
 * <blockquote>
 * <pre>
 * //输出排序(如有自定义名称需用自定义的名称)
 * <b>@JSONType(orders = {"Element", "ArrayStr", "ListStr", "MapStr"})</b>
 * public class XmlUtilTestObj {
 *     private String el;
 *     private String hide;
 *     private String[] arraystr;
 *     private String[] liststr;
 *     private Map&lt;String, String&gt; mapstr;
 * 
 *     //自定义属性名称
 *     <b>@JSONField(name = "Element")</b>
 *     public String getEl() {
 *         return el;
 *     }
 *     //自定义属性名称(反序列化要用，必须写)
 *     <b>@JSONField(name = "Element")</b>
 *     public void setEl(String el) {
 *         this.el = el;
 *     }
 * 
 *     ......
 * 
 *     //屏蔽序列化
 *     <b>@JSONField(serialize = false)</b>
 *     public String getHide() {
 *         return hide;
 *     }
 *     //屏蔽反序列化
 *     <b>@JSONField(deserialize = false)</b>
 *     public void setHide(String hide) {
 *         this.hide = hide;
 *     }
 * 
 *     ......
 * 
 * }
 * </pre>
 * </blockquote>
 * <p>
 * Target：Json
 * <blockquote>
 * <pre>
 * {
 *     "Element":"el",
 *     "ArrayStr":["arraystr_1","arraystr_2"],
 *     "ListStr":["liststr_1","liststr_2"],
 *     "MapStr":{
 *         "mapstr_key1":"mapstr_value1",
 *         "mapstr_key2":"mapstr_value2"
 *     }
 * }
 * </pre>
 * </blockquote>
 * <p>
 * @author Sunny
 *
 */
public class JsonUtil {
    /**
     * 将对象序列化为Json字符串
     * 注意：obj String类型的值Null将会被转化成""
     * @param obj 对象
     * @return String Json字符串
     */
    public static final String toJson(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.WriteNullStringAsEmpty);
    }


    /**
     * 将对象序列化为Json字符串
     * @param obj 对象
     * @return String Json字符串
     */
    public static final String toJson(Object obj,SerializerFeature... features){
        return JSON.toJSONString(obj, features);
    }


    /**
     * 将Json字符串反序列化为对象
     * @param jsonString Json字符串
     * @param clazz 需要转换到的对象类
     * @return <T>对象
     */
    public static final <T> T toObj(String jsonString, Class<T> clazz) {
        //jsonString = CodingUtil.paramDecoding(jsonString);
        return JSON.parseObject(jsonString, clazz);
    }

    public static final <T> T toObj(String jsonString,TypeReference<T> typeReference) {
        return JSON.parseObject(jsonString, typeReference);
    }
    /**
     * 将Json字符串反序列化为数组对象
     * @param jsonString Json字符串
     * @param clazz 需要转换到的对象类（数组内的对象实体）
     * @return List<T>数组对象
     */
    public static final <T> List<T> toList(String jsonString, Class<T> clazz) {
        //jsonString = CodingUtil.paramDecoding(jsonString);
        return JSON.parseArray(jsonString, clazz);
    }
    /**
     * 将Map转化为对象
     * @param map
     * @param clazz 需要转换到的对象类
     * @return <T>对象
     */
    public static final <T> T toObj(Object map, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(map, SerializerFeature.WriteMapNullValue), clazz);
    }

    /**
     * 将bean 属性key value解析到map
     * @param obj
     * @return
     */
    public static final Map<String, String> toMap(Object obj) {
        JSONObject jsonObject = JSON.parseObject(JsonUtil.toJson(obj));
        Map<String, String> paramMap = Maps.newHashMap();
        jsonObject.keySet().forEach(key -> {
            paramMap.put(key, jsonObject.getString(key));
        });
        return paramMap;
    }

}
