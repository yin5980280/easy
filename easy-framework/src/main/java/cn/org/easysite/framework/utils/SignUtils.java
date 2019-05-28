package cn.org.easysite.framework.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import cn.org.easysite.commons.constants.BaseConstants;
import cn.org.easysite.commons.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;

import static cn.org.easysite.framework.utils.ExceptionTool.createBaseException;

/**
 * @author liangb
 * @version 1.0
 * @date 16/7/7 下午2:15
 */
@Slf4j
public class SignUtils {
    private static final String SIGN_SHA1 = "SHA1";
    private static final String SIGN_MD5 = "MD5";
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String KEY_STR = "&key=";//SHA1签名拼接的字符串

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param charset    编码格式
     * @return 签名值
     */
    public static String rsa(String content, String privateKey, String charset) throws Exception {
        return RSA.sign(content.getBytes(charset), privateKey);
    }

    /**
     * RSA2签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param charset    编码格式
     * @return 签名值
     */
    public static String rsa2(String content, String privateKey, String charset) throws Exception {
        return RSA.sign2(content.getBytes(charset), privateKey);
    }

    /**
     * 生成RSA签名
     *
     * @param text
     * @param privateKey
     * @return
     */
    public static String rsaByText(String text, String privateKey) {
        String result = null;
        try {
            result = rsa(text, privateKey, BaseConstants.DEFAULT_CHARSET);
        } catch (Exception e) {
            String errInfo = "计算RSA签名出错，请检查RSA秘钥配置是否正确";
            log.error(errInfo + text, e);
            createBaseException(1023, errInfo);
        }
        log.info("String: {} , RSA: {}", text, result);
        return result;
    }

    /**
     * 生成RSA2签名
     *
     * @param text
     * @param privateKey
     * @return
     */
    public static String rsa2ByText(String text, String privateKey) {
        String result = null;
        try {
            result = rsa2(text, privateKey, BaseConstants.DEFAULT_CHARSET);
        } catch (Exception e) {
            String errInfo = "计算RSA2签名出错，请检查RSA2秘钥配置是否正确";
            log.error(errInfo + text, e);
            createBaseException(1023, errInfo);
        }
        log.info("String: {} , RSA2: {}", text, result);
        return result;
    }

    /**
     * 生成RSA签名
     *
     * @param paramesMap
     * @param privateKey
     * @return
     */
    public static String rsaByMap(TreeMap<String, String> paramesMap, String privateKey) {
        StringBuilder sb = MapUtils.map2StringBuilder(paramesMap, null);
        return SignUtils.rsaByText(sb.toString(), privateKey);
    }

    /**
     * 生成RSA2签名
     *
     * @param paramesMap
     * @param privateKey
     * @return
     */
    public static String rsa2ByMap(TreeMap<String, String> paramesMap, String privateKey) {
        StringBuilder sb = MapUtils.map2StringBuilder(paramesMap, null);
        return SignUtils.rsa2ByText(sb.toString(), privateKey);
    }

    /**
     * 校验RSA签名是否正确
     *
     * @param paramesMap
     * @param publicKey
     * @param sign
     * @return
     */
    public static boolean rsaVerify(TreeMap<String, String> paramesMap, String publicKey, String sign) {
        StringBuilder sb = MapUtils.map2StringBuilder(paramesMap, null);
        String text = sb.toString();
        try {
            return RSA.verify(text.getBytes(BaseConstants.DEFAULT_CHARSET_OBJ), publicKey, sign);
        } catch (Exception e) {
            log.error("rsa verify error" + e.getMessage());
        }
        return false;
    }

    /**
     * 校验RSA2签名是否正确
     *
     * @param paramesMap
     * @param publicKey
     * @param sign
     * @return
     */
    public static boolean rsa2Verify(TreeMap<String, String> paramesMap, String publicKey, String sign) {
        StringBuilder sb = MapUtils.map2StringBuilder(paramesMap, null);
        String text = sb.toString();
        try {
            return RSA.verify2(text.getBytes(BaseConstants.DEFAULT_CHARSET_OBJ), publicKey, sign);
        } catch (Exception e) {
            log.error("rsa verify error" + e.getMessage());
        }
        return false;
    }

    /**
     * 生成sha1签名(大写)
     *
     * @param map
     * @param key
     * @return
     */
    public static String sha1ByMap(TreeMap<String, Object> map, String key) {
        String text = MapUtils.treeMap2ascString(map);//生成待签名字符串
        text = text + KEY_STR + key;//拼接key
        return sha1ByText(text);
    }

    /**
     * 生成sha1签名(大写)
     *
     * @param text
     * @return
     */
    public static String sha1ByText(String text) {
        String sign = null;
        try {
            sign = SignUtils.security(text, SIGN_SHA1, null).toUpperCase();
        } catch (Exception e) {
            String errInfo = "计算SHA1签名出错";
            log.error(errInfo + text, e);
            createBaseException(1023, errInfo);
        }
        log.info("String: {} , SHA1: {}", text, sign);
        return sign;
    }

    private static String security(String s, String algorithm, Charset charset) throws NoSuchAlgorithmException {
        if (null == charset) {
            charset = BaseConstants.DEFAULT_CHARSET_OBJ;
        }
        byte[] btInput = s.getBytes(charset);
        // 获得摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance(algorithm);
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 取字符串的md5值(大写)，使用默认的UTF-8编码
     *
     * @param text
     * @return
     */
    public static String md5ByText(String text) {
        return SignUtils.md5ByText(text, null);
    }

    /**
     * 取字符串的md5值(大写)，使用默认的UTF-8编码
     *
     * @param text
     * @return
     */
    public static String md5ByText(String text, Charset charset) {
        String rs = null;
        try {
            rs = security(text, SIGN_MD5, charset).toUpperCase();
        } catch (Exception e) {
            String errInfo = "计算MD5签名出错";
            log.error(errInfo + rs, e);
            createBaseException(1023, errInfo);
        }
        log.info("String: {} , MD5: {}", text, rs);
        return rs;
    }

    /**
     * 将treeMap中的键值对串联起来，并加上后缀suffix，最后取md5
     * 串联方式为：key1=value1&key2=value2&...&keyn=valuen，如果suffix不为空则加在后面
     *
     * @param map
     * @param suffix
     * @return
     */
    public static String md5ByMap(TreeMap<String, String> map, String suffix) {
        StringBuilder sb = MapUtils.map2StringBuilder(map, null);
        if (null != suffix) {
            sb.append(suffix);
        }
        return SignUtils.md5ByText(sb.toString());
    }
}
