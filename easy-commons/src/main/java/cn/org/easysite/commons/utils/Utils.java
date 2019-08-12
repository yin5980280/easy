package cn.org.easysite.commons.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.org.easysite.commons.constants.BaseConstants;


/**
 * @version 1.0
 * @date 2016/10/19 16:29
 */
public class Utils {

    //获取msgId的正则表达式
    private static final Pattern msgIdPattern = Pattern.compile("[{,]\\s?\"msgId\"\\s?:\\s?\"\\S+\"\\s?[,}]");

    private static final Pattern messageIdPattern = Pattern.compile("[{,]\\s?\"messageId\"\\s?:\\s?\"\\S+\"\\s?[,}]");

    public static final String PACKAGE_PREFIX_EASY_SITE = "cn.org.easysite";

    public static final String PACKAGE_EXCLUDE_COMMON = "common";

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 从json字符串中提取msgId字段的值
     *
     * @param json
     * @return
     */
    public static String getMsgId(String json) {
        return getJsonValue(json, msgIdPattern);
    }

    /**
     * 从json字符串中提取msgId字段的值
     *
     * @param json
     * @return
     */
    public static String getMessageId(String json) {
        return getJsonValue(json, messageIdPattern);
    }

    /**
     * 从json字符串中提取字段的值
     * @param json
     * @param pattern
     * @return
     */
    public static String getJsonValue(String json, Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                String msgId = matcher.group(0);
                int start = msgId.indexOf(':');
                start = msgId.indexOf('"', start + 1);
                int end = msgId.lastIndexOf('"');
                return msgId.substring(start + 1, end);
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取异常所在的业务代码相关的信息
     *
     * @param e
     * @return
     */
    public static String getExceptionInfo(Throwable e) {
        return getExceptionInfo(e, PACKAGE_PREFIX_EASY_SITE, PACKAGE_EXCLUDE_COMMON);
    }

    /**
     * 获取异常所在的业务代码相关的信息
     * @param e
     * @param packagePrefix
     * @param index
     * @return
     */
    public static String getExceptionInfo(Throwable e, String packagePrefix, int index) {
        if (e == null) {
            return "";
        }
        String errInfo = null;
        StackTraceElement[] ss = e.getStackTrace();
        int n = 0;
        if (ss != null && ss.length > 0) {
            for (StackTraceElement s : ss) {
                if (s == null) {
                    continue;
                }
                String className = s.getClassName();
                if (className.startsWith(packagePrefix)) {
                    if (++n > index) {
                        break;
                    }
                }
            }
        }
       return getExceptionInfo(e, errInfo);
    }

    private static String getExceptionInfo(Throwable e, String errInfo) {
        StringBuilder sb = new StringBuilder(e.getClass().getSimpleName());
        String message = e.getMessage();
        if (null != message) {
            sb.append(':').append(message);
        }
        if (null != errInfo) {
            sb.append(':').append(errInfo);
        }
        return sb.toString();
    }

    /**
     * 获取异常所在的业务代码相关的信息
     * @param e
     * @param packagePrefix
     * @param packageExclude
     * @return
     */
    public static String getExceptionInfo(Throwable e, String packagePrefix, String packageExclude) {
        if (e == null) {
            return null;
        }
        String errInfo = null;
        StackTraceElement[] ss = e.getStackTrace();
        if (ss != null && ss.length > 0) {
            for (StackTraceElement s : ss) {
                if (s == null) {
                    continue;
                }
                String className = s.getClassName();
                if (className.startsWith(packagePrefix) && !className.contains(packageExclude)) {
                    errInfo = s.toString();
                    break;
                }
            }
        }
        return getExceptionInfo(e, errInfo);
    }

    /**
     * 按字节截取字符串
     *
     * @param s
     * @param byteCount
     * @return
     */
    public static String subByteString(String s, int byteCount) {
        //处理错误信息的长度，防止超长，影响正常业务
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        byte[] data = s.getBytes(BaseConstants.DEFAULT_CHARSET_OBJ);
        if (data.length > byteCount) {
            return new String(Arrays.copyOf(data, byteCount), BaseConstants.DEFAULT_CHARSET_OBJ);
        }
        return s;
    }

    /**
     * 判断源字符串是否在集合中存在
     *
     * @param patterns 集合，支持1个通配符*，示例["a","ab","*c","d*","e*f"]
     * @param source
     * @return
     */
    public static boolean isMatcher(Set<String> patterns, String source) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        if (StringUtils.isEmpty(source)) {
            return true;
        }
        for (String pattern : patterns) {
            if (matches(pattern, source)) {
                return true;
            }
        }
        return false;
    }

    public static boolean matches(String pattern, String source) {
        int start;
        if (pattern.endsWith("*")) {
            start = pattern.length() - 1;
            if (source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                return true;
            }
        } else if (pattern.startsWith("*")) {
            start = pattern.length() - 1;
            if (source.length() >= start && source.endsWith(pattern.substring(1))) {
                return true;
            }
        } else if (pattern.contains("*")) {
            start = pattern.indexOf("*");
            int end = pattern.lastIndexOf("*");
            if (source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
                return true;
            }
        } else if (pattern.equals(source)) {
            return true;
        }

        return false;
    }

}
