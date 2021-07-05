package cn.cube.base.core.util;


import cn.cube.base.core.security.Digests;

import java.io.IOException;

/**
 */
public class Base64Utils {

    /**
     *
     * @param src
     * @return
     */
    public static String encodeToString(byte[] src) {
        return org.springframework.util.Base64Utils.encodeToString(src);
    }

    public static String encodeToUrlSafeString(byte[] src) {
        return org.springframework.util.Base64Utils.encodeToUrlSafeString(src);
    }

    /**
     *
     * @param src
     * @return
     */
    public static byte[] decodeFromString(String src) {
        return org.springframework.util.Base64Utils.decodeFromString(src);
    }

    public static byte[] decodeFromUrlSafeString(String src) {
        return org.springframework.util.Base64Utils.decodeFromUrlSafeString(src);
    }

    public static void main(String[] args) throws IOException {
        String src = "app_id=1&biz_data=Ri1zikqAbZ3f7aEcsi-6iNQjondwOJcO3sm2pm_AlHDToh0-Av_Q_jFrf88R8bEn&imei=0123456789012345&method=com.lejane.handler.user.register&timestamp=1492744371440&token=&uid=&version=1.0";
        src += "&key=6sZUoGeHzIdmp5u8";

        for (int i=0; i< 10; i++) {
            byte[] result = Digests.md5(src.getBytes());
            System.out.println(encodeToUrlSafeString(result));
        }

    }

}
