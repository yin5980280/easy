package cn.org.easysite.spring.boot.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-09-27 10:47
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.utils.MDCUtils
 */
@Slf4j
public class MDCUtils {

    public static final String KEY_MSG_ID = "_EASY_GLOBAL_MSG_ID";

    private static final String KEY_IP = "IP";

    private static final String KEY_URL = "URL";

    private static String MSG_ID_PREFIX = "easy_";

    /**
     * 服务器ip地址
     */
    private static String ip;

    /**
     * 服务器短IP
     */
    private static String shortIp;

    static {
        ip = getLocalIp();
        String[] sip = ip.split("\\.");
        if (sip.length >= 4) {
            shortIp = sip[2] + "." + sip[3];
        } else {
            shortIp = ip;
        }
    }

    public static void setMsgIdPrefix(String msgIdPrefix) {
        if (StringUtils.isNotBlank(msgIdPrefix)) {
            MSG_ID_PREFIX = msgIdPrefix;
        }
    }

    public static void init() {
        MDC.clear();
        MDC.put(KEY_IP, ip);
        setMsgId(generateId());
    }

    public static void init(HttpServletRequest request) {
        MDC.clear();
        MDC.put(KEY_IP, ip);
        getOrGenMsgId(request);
        setUrL(request.getServletPath());
    }

    public static String getIp() {
        return ip;
    }

    public static void removeIp() {
        MDC.remove(KEY_IP);
    }

    public static String getShortIp() {
        return shortIp;
    }

    public static void setUrL(String url) {
        MDC.put(KEY_URL, url);
    }

    public static String getUrL() {
        return MDC.get(KEY_URL);
    }

    public static void removeUrL() {
        MDC.remove(KEY_URL);
    }

    public static void setMsgId(String id) {
        MDC.put(KEY_MSG_ID, id);
    }

    public static String getMsgId() {
        return MDC.get(KEY_MSG_ID);
    }

    public static void removeMsgId() {
        MDC.remove(KEY_MSG_ID);
    }

    /**
     * 生成一个唯一的消息id，用于日志埋点
     *
     * @return
     */
    public static String generateId() {
        StringBuilder sb = new StringBuilder(22);
        sb.append(MSG_ID_PREFIX).append(shortIp);
        sb.append("_").append(System.currentTimeMillis());
        return sb.toString();
    }

    /**
     * 从MDC中获取msgId，没有则生成一个
     *
     * @return
     */
    public static String getOrGenMsgId() {
        String msgId = getMsgId();
        if (StringUtils.isBlank(msgId)) {
            msgId = generateId();
            setMsgId(msgId);
        }
        return msgId;
    }

    /**
     * 获取或者创建messgaeId
     * @param request
     * @return
     */
    public static String getOrGenMsgId(HttpServletRequest request) {
        String msgId = getMsgId();
        if (StringUtils.isNotBlank(msgId)) {
            return msgId;
        }
        msgId = request.getHeader(KEY_MSG_ID);
        if (StringUtils.isEmpty(msgId)) {
            msgId = generateId();
        }
        setMsgId(msgId);
        return msgId;
    }
    /**
     * 设置MDC中msgId的值，为空则生成一个
     * @return
     */
    public static String setOrGenMsgId(String msgId) {
        if (StringUtils.isBlank(msgId)) {
            msgId = generateId();
        }
        setMsgId(msgId);
        return msgId;
    }

    /**
     * 获取本地IP地址
     * @return
     */
    public static String getLocalIp() {
        InetAddress ip, localIp = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        if (null == localIp && ip.isSiteLocalAddress()) {// 内网IP
                            localIp = ip;
                        } else {//外网IP
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            log.error(e.getMessage());
        }
        if (null == localIp) {
            return "127.0.0.1";
        }

        return localIp.getHostAddress();
    }
}
