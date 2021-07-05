package cn.cube.base.core.util;

import cn.cube.base.core.security.Cryptos;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 密码辅助类
 * Created by liuyang on 15/11/8.
 */
public class CodecUtils {

    private CodecUtils() {
    }

    public static String aesEncode(String data, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Base64.encodeBase64String(result);
    }

    public static byte[] aesEncodeByte(String data, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(byteContent);
    }

    public static String aesEncodeHex(String data, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] byteContent = data.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return Cryptos.byteToHexString(result);
    }

    public static String aesDecode(String data, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        String result = StringUtils.newStringUtf8(cipher.doFinal(Base64.decodeBase64(data)));
        return result;
    }

    public static String aesDecode(byte[] bytes, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        String result = StringUtils.newStringUtf8(cipher.doFinal(bytes));
        return result;
    }

    public static byte[] aesCbcEncodeByte(String data, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(aesKey);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] byteContent = data.getBytes("utf-8");
        return cipher.doFinal(byteContent);
    }

    public static String aesCbcDecode(byte[] bytes, String encodeKey) throws Exception {
        byte[] aesKey = StringUtils.getBytesUtf8(encodeKey);
        SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(encodeKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        String result = StringUtils.newStringUtf8(cipher.doFinal(bytes));
        return result;
    }


    public static void main(String[] args) throws Exception {
        String data = "fJB7hM5czE-0ZcasozhFuQfvfJ8w0FnRTec8iO-gyO_bOtgJwRnxnZvaTuX9SRhN";

        byte[] bytes = Base64Utils.decodeFromUrlSafeString(data);
        String s = CodecUtils.aesDecode(bytes, "TCoOkPBljy9rgdj4");
        System.out.println(s);
    }

}
