package cn.org.easysite.framework.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import cn.org.easysite.commons.utils.Encodes;

/**
 * @author panda
 * @version 1.0
 * @date 2018/6/8 11:27
 */
public class RSA {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_RSA1 = "SHA1WithRSA";
    public static final String SIGNATURE_RSA2 = "SHA256WithRSA";
    /**
     * RSA加密长度（值越大，强度越高，速度越慢）
     */
    public static final int RSA_KEY_SIZE = 1024;
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名(SHA1WithRSA)
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        return sign(SIGNATURE_RSA1, data, privateKey);
    }

    /**
     * 用私钥对信息生成数字签名(SHA256WithRSA)
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign2(byte[] data, String privateKey) throws Exception {
        return sign(SIGNATURE_RSA2, data, privateKey);
    }

    /**
     * 用指定签名算法和私钥对信息生成数字签名
     *
     * @param signAlgorithm 签名算法
     * @param data          加密数据
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    public static String sign(String signAlgorithm, byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = Encodes.decodeBase64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(priKey);
        signature.update(data);
        return Encodes.encodeBase64(signature.sign());
    }

    /**
     * 校验数字签名(SHA1WithRSA)
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        return verify(SIGNATURE_RSA1, data, publicKey, sign);
    }

    /**
     * 校验数字签名(SHA256WithRSA)
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify2(byte[] data, String publicKey, String sign) throws Exception {
        return verify(SIGNATURE_RSA2, data, publicKey, sign);
    }

    /**
     * 校验数字签名
     *
     * @param signAlgorithm 签名算法
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(String signAlgorithm, byte[] data, String publicKey, String sign)
            throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = Encodes.decodeBase64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Encodes.decodeBase64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Encodes.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * 用私钥解密
     *
     * @param encryptData
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String encryptData, String key)
            throws Exception {
        return decryptByPrivateKey(Encodes.decodeBase64(encryptData), key);
    }

    /**
     * 解密
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Encodes.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * 用公钥解密
     *
     * @param encryptData
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(String encryptData, String key) throws Exception {
        return decryptByPublicKey(Encodes.decodeBase64(encryptData), key);
    }

    /**
     * 加密
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = Encodes.decodeBase64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 加密
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = Encodes.decodeBase64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Encodes.encodeBase64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Key> keyMap)
            throws Exception {
        Key key = keyMap.get(PUBLIC_KEY);
        return Encodes.encodeBase64(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(RSA_KEY_SIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }

//    public static void main(String[] args) throws Exception {
//        Map<String,Key> keyMap = initKey();
//        System.out.println(getPrivateKey(keyMap));
//        System.out.println(getPublicKey(keyMap));
//        Charset charset = Charset.forName("utf-8");
//        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK7/hcZq60HMsqaeCFIZAZz4B7a7PdDxoyiLBfYHCR0RvaRYfSeA5LBCusW02k5RXLSvNhApHX7xCahXJiI5Bfq75uu2xKBLfSGbN3IsQBfXXosJarrJDWwwcWD09+bd61W/C5z550xzxGNN8lUlrHS6vZ4ik4knztYJE35gTFjXAgMBAAECgYEAi6pxyP8yTayidKox99vvVqj59xQPbQA9eLEkC8xnsKSlF1JsaKfNXQOU7w+9H75939LqshEuck7Lp2gOjke2hadyhBZDtHoUaucFeJK7/vFtoFOZUyd+mApILsWmBwvTRDjkU0n8J8O/TlMLRfBt3/yyFVzKuhb433sDYF0hrLkCQQDtgDh7BeZFCERQfGlpjgVplZ/QzQTDwbHtBbP5V7eQlr49NnrMH/eNM77vLvhNOTay3Z2i4sRtthfiACmLNnWdAkEAvKD+jczWoQQtMfubjSxBsRMiI8hNk2KvOObJ1gc1NdX/rFom3HigYxFQj+oTxOXn0szUlk1TQyJgqTPrz7dYAwJAIWMeDYVVyqyP1fS3aORTNHWZQuQfYFmRZq2fkFTZdFUMp1RKTPnNdicpFzy2IT0CyIBJEcXpMTchAl9dd7mPLQJAKk3JCTBJam8vW6hcKHsmeHocC/5eN5HITnCkD5YqPtqxrovBb2y2O8GkWcJbkKsnJMW/X74T0FijaQ818a3i1QJAPWmBZdNZib28P22Jy+nf4z8QVmBFJRtZ0npu7m9jQNuIhKeBF3HPstYxcFfdrx2+WPkl1+62IV8/Z20tL5q5aw==";
//        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu/4XGautBzLKmnghSGQGc+Ae2uz3Q8aMoiwX2BwkdEb2kWH0ngOSwQrrFtNpOUVy0rzYQKR1+8QmoVyYiOQX6u+brtsSgS30hmzdyLEAX116LCWq6yQ1sMHFg9Pfm3etVvwuc+edMc8RjTfJVJax0ur2eIpOJJ87WCRN+YExY1wIDAQAB";
//        String signStr = "appType=mobile-android&bankAccName=大佬&bankAccPhone=18888888888&bankAccount=666666666666&bankBranchNo=132345&bankName=中国银行&brandId=4891&deviceId=d89c3278de74629d84499cd6b9d9a974&shopId=810002803&systemName=Android&timestamp=1511862189478&userId=88889066316&versionCode=2110032000&versionName=3.21.0-SNAPSHOT";
//        String encStr = "asfgs2d85sf635点睡ads35sdfa是1的a发生235";
//        byte[] data = encStr.getBytes(charset);
//        byte[] encByPriv = encryptByPrivateKey(data,privateKey);
//        byte[] decByPub = decryptByPublicKey(encByPriv,publicKey);
//        byte[] encByPub = encryptByPublicKey(data,publicKey);
//        byte[] decByPriv = decryptByPrivateKey(encByPub,privateKey);
//
//        byte[] signData = signStr.getBytes(charset);
//        String sign = sign(signData, privateKey);
//        boolean isOk = verify(signData,publicKey,sign);
//
//        System.out.println("原文：" + encStr);
//        System.out.println("私钥加密：" + Base64.encode(encByPriv));
//        System.out.println("公钥解密：" + new String(decByPub,charset));
//        System.out.println("公钥加密：" + Base64.encode(encByPub));
//        System.out.println("私钥解密：" + new String(decByPriv,charset));
//        System.out.println();
//        System.out.println("签名原文：" + signStr);
//        System.out.println("签名结果：" + sign);
//        System.out.println("验签结果：" + isOk);

//        Charset charset = Charset.forName("utf-8");
//        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK7/hcZq60HMsqaeCFIZAZz4B7a7PdDxoyiLBfYHCR0RvaRYfSeA5LBCusW02k5RXLSvNhApHX7xCahXJiI5Bfq75uu2xKBLfSGbN3IsQBfXXosJarrJDWwwcWD09+bd61W/C5z550xzxGNN8lUlrHS6vZ4ik4knztYJE35gTFjXAgMBAAECgYEAi6pxyP8yTayidKox99vvVqj59xQPbQA9eLEkC8xnsKSlF1JsaKfNXQOU7w+9H75939LqshEuck7Lp2gOjke2hadyhBZDtHoUaucFeJK7/vFtoFOZUyd+mApILsWmBwvTRDjkU0n8J8O/TlMLRfBt3/yyFVzKuhb433sDYF0hrLkCQQDtgDh7BeZFCERQfGlpjgVplZ/QzQTDwbHtBbP5V7eQlr49NnrMH/eNM77vLvhNOTay3Z2i4sRtthfiACmLNnWdAkEAvKD+jczWoQQtMfubjSxBsRMiI8hNk2KvOObJ1gc1NdX/rFom3HigYxFQj+oTxOXn0szUlk1TQyJgqTPrz7dYAwJAIWMeDYVVyqyP1fS3aORTNHWZQuQfYFmRZq2fkFTZdFUMp1RKTPnNdicpFzy2IT0CyIBJEcXpMTchAl9dd7mPLQJAKk3JCTBJam8vW6hcKHsmeHocC/5eN5HITnCkD5YqPtqxrovBb2y2O8GkWcJbkKsnJMW/X74T0FijaQ818a3i1QJAPWmBZdNZib28P22Jy+nf4z8QVmBFJRtZ0npu7m9jQNuIhKeBF3HPstYxcFfdrx2+WPkl1+62IV8/Z20tL5q5aw==";
//        String signStr = "appType=mobile-android&bankAccName=大佬&bankAccPhone=18888888888&bankAccount=666666666666&bankBranchNo=132345&bankName=中国银行&brandId=4891&deviceId=d89c3278de74629d84499cd6b9d9a974&shopId=810002803&systemName=Android&timestamp=1511862189478&userId=88889066316&versionCode=2110032000&versionName=3.21.0-SNAPSHOT";
//        byte[] signData = signStr.getBytes(charset);
//        System.out.println(sign(signData, privateKey));
//        System.out.println(sign2(signData, privateKey));
//    }
}
