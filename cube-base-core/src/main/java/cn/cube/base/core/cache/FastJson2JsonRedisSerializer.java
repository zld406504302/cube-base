package cn.cube.base.core.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Description:FastJson2JsonRedisSerializer
 * Author:zhanglida
 * Date:2018/3/24
 * Email:406504302@qq.com
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return toJson(t).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return (T) toObject(str,clazz);
    }

    public static String toJson(Object object){
        return JSON.toJSONString(object, SerializerFeature.WriteClassName);
    }

    public static <V> V toObject(String json,Class<V> vClass){
        return (V) JSON.parseObject(json, vClass);
    }


}
