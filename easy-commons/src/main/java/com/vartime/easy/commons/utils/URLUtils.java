package com.vartime.easy.commons.utils;

import java.util.Map;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-10 00:28
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : KeRuYun Technology(Beijing) Chengdu Co. Ltd.
 * @link : com.platform.payment.core.utils.URLUtils
 */
public class URLUtils {

    public static String buildGetUrl(String url, Map<String, String> params) {
        int i = 0;
        String paramStr = "";
        for (String key : params.keySet()) {
            paramStr += "&" + key + "=" + params.get(key);
        }
        return url + paramStr.replaceFirst("&", "?");
    }

}
