package cn.cube.base.core.util;

import org.apache.commons.io.FilenameUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Haima on 2016/06/14.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    public static String format(String str, String... args) {
        if (isEmpty(str)) {
            return str;
        }
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                str = str.replaceFirst("\\{\\}", args[i]);
            }
        }
        return str;
    }
    /**
     * 是否是包含"{}"的格式化字符串，例如：”错误原因{}“
     *
     * @param src
     * @return
     */
    public static boolean isFormatStr(String src) {
        if (isEmpty(src)) {
            return false;
        }
        if (src.matches(".*\\{\\}.*")) {
            return true;
        }
        return false;
    }
    public static String strip(String str) {
        return strip(str, null);
    }

    public static String strip(String str, String pattern) {
        return org.apache.commons.lang3.StringUtils.strip(str, pattern);
    }

    public static String newStringUtf8(byte[] bytes) {
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
    }

    /**
     * 校验给定字符是否匹配给定的正则表达
     * @param src
     * @param regExp
     * @return
     */
    public static boolean isMatch(String src, String regExp) {
        if (isEmpty(regExp)) {
            return true;
        }
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean isPhoneMatch(String cellphone) {
        return isMatch(cellphone, "^(1)\\d{10}$");
    }

    /**
     * 根据用户名的不同长度，来进行替换 ，达到保密效果
     *
     * @param userName 用户名
     * @return 替换后的用户名
     */
    public static String userNameReplaceWithStar(String userName) {
        String userNameAfterReplaced = "";

        if (userName == null){
            userName = "";
        }

        int nameLength = userName.length();

        if (nameLength == 1) {
            userNameAfterReplaced = "*";
        } else if (nameLength == 2) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.{1}).(?=.{0})");
        } else if (nameLength >= 3 && nameLength <= 6) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.{1}).(?=.{0})");
        } else if (nameLength == 7) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.{1}).(?=.{0})");
        } else if (nameLength == 8) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.{1}).(?=.{0})");
        } else if (nameLength == 9) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.{1}).(?=.{0})");
        } else if (nameLength == 10) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.d{1}).(?=.{0})");
        } else if (nameLength >= 11) {
            userNameAfterReplaced = replaceAction(userName, "(?<=.d{1}).(?=.{0})");
        }

        return userNameAfterReplaced;

    }

    /**
     * 实际替换动作
     *
     * @param username username
     * @param regular  正则
     * @return
     */
    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }

    /**
     * 身份证号替换，保留前四位和后四位
     *
     * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param idCard 身份证号
     * @return
     */
    public static String idCardReplaceWithStar(String idCard) {

        if (idCard == null || idCard.isEmpty()) {
            return null;
        } else {
            return replaceAction(idCard, "(?<=\\d{1})\\d(?=\\d{1})");
        }
    }

    /**
     * 手机号码替换，保留前三位和后四位
     *
     * 如果身份证号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param phone 身份证号
     * @return
     */
    public static String phoneReplaceWithStar(String phone) {

        if (phone == null || phone.isEmpty()) {
            return null;
        } else {
            return replaceAction(phone, "(?<=\\d{3})\\d(?=\\d{4})");
        }
    }

    /**
     * 过滤特殊字符
     *
     * @param str 要清空特殊字符的字符串
     * @return 清空特殊字符后的字符串
     */
    public static String StringFilter(String str) {
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 根据文件后缀名称判断是否符合
     *
     * @param fileName 文件名称
     * @param judgeSuffix 判断后缀
     *
     */
    public static boolean judgeFileSuffix(String fileName, String judgeSuffix) {
        String suffix = FilenameUtils.getExtension(fileName).toLowerCase();
        if (judgeSuffix.equals(suffix)) {
            return true;
        }

        return false;
    }

    /**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String randomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
