package cn.cube.base.third.wx;

import cn.cube.base.core.util.HMAC;
import cn.cube.base.core.util.LoggerUtils;
import org.slf4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 15:23
 */
public class Signature {
    private static Logger log = LoggerUtils.getLogger(Signature.class);

    public static final String SIGN_TYPE_HMAC256 = "HMAC256";
    public static final String SIGN_TYPE_MD5 = "MD5";
    public static String getSign(String key, Map<String, String> map) {
        return getSign(key, map, SIGN_TYPE_MD5);
    }

    public static String getSign(String key, Map<String, String> map, String signType) {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (null != entry.getValue() && entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        log.info("Sign Before:" + result);
        if (SIGN_TYPE_MD5.equals(signType)) {
            result = MD5.MD5Encode(result).toUpperCase();
        } else {
            result = HMAC.sha256(result, key);
        }
        log.info("Sign Result:" + result);
        return result;
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     *
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static String getSignFromResponseString(String key, String responseString) throws Exception {
        Map<String, String> map = XMLParser.getMapFromXML(responseString);
        map.put("sign", "");
        return Signature.getSign(key, map);
    }

    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param responseString API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String key, String responseString) {

        Map<String, String> map;
        try {
            map = XMLParser.getMapFromXML(responseString);
        } catch (Exception e) {
            log.error("xml to map fail", e);
            return false;
        }

        return checkIsSignValidFromResponseString(key, map);
    }


    /**
     * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
     *
     * @param map API返回的XML数据字符串
     * @return API签名是否合法
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static boolean checkIsSignValidFromResponseString(String key, Map<String, String> map) {
        if (null == map || map.size() == 0) {
            return false;
        }
        String signFromAPIResponse = map.get("sign");
        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            return false;
        }
        map.put("sign", "");
        String signForAPIResponse = Signature.getSign(key, map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {
            return false;
        }
        return true;
    }


}
