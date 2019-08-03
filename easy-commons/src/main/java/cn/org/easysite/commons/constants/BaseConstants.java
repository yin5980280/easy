package cn.org.easysite.commons.constants;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.TimeZone;

/**
 * @version 1.0
 * @date 2017/6/5 11:31
 */
public class BaseConstants {
    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final Charset DEFAULT_CHARSET_OBJ = Charset.forName(DEFAULT_CHARSET);
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    public static final Integer INTEGER_ZERO = Integer.valueOf(0);

    public static final Integer INTEGER_ONE = Integer.valueOf(1);

    public static final Long LONG_ZERO = Long.valueOf(0);

    public static final Long LONG_ONE = Long.valueOf(1);

    public static final BigDecimal BIG_DECIMAL_HUNDRED = BigDecimal.valueOf(100);

    //签名字段
    public static final String KEY_SIGN = "sign";
    //时间戳字段
    public static final String KEY_TIMESTAMP = "timestamp";

    public static final String PACKAGE_JAVA_LANG = "java.lang";
    public static final String PACKAGE_COM_EASY_SITE = "cn.org.easysite";

    public static final String WEB_DEFAULT_DEVICEID = "00-00-00-00-00-00";

    public static final String WEB_DEFAULT_CHANNEL = "00";



    public static final String URL_SEPARATOR = "/";

    public static final String HTTP_CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_CONTENT_TYPE_TEXT = "text/";
    public static final String HTTP_CONTENT_TYPE_XML = "xml";
    public static final String HTTP_CONTENT_TYPE_JS = "javascript";

    public static final String HTTP_HEADER_CONTENT_DISPOSITON = "Content-disposition";
    public static final String HEADER_SESSION_ID = "sessionId";
    public static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    public static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
}
