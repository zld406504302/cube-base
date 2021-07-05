package cn.cube.base.core.util;

import org.slf4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description:HMAC
 * Author:zhanglida
 * Date:2020/9/10
 * Email:406504302@qq.com
 */
public class HMAC {
    private static final Logger logger = LoggerUtils.getLogger(HMAC.class);

    public static String sha256(String data, String key) {
        StringBuilder sb = new StringBuilder();
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
        } catch (Exception e) {
            logger.error("hmac fail cause:{}", e);
        }

        return sb.toString().toUpperCase();

    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String key = "561b8b607e32f3805430f5ab56a4632d";
        String content = "appid=wx1664ad5a906cf4b4&mch_id=1402174102&nonce_str=ds6rep9jr4x9tubv71niafln57zjf3up&receiver={\"account\":\"owSsd0U-aptJjDvU7jz_n9ywPxIY\",\"relation_type\":\"PARTNER\",\"type\":\"PERSONAL_OPENID\"}&key="+key;
        System.out.println(HMACSHA256(content,key));
    }

}
