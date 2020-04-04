package cn.org.easysite.commons.utils;

import java.util.Map;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-10 00:28
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.platform.payment.core.utils.URLUtils
 */
public class HttpRequestParamUtils {

    /**
     * 构建GET请求url
     * @param url
     * @param params
     * @return
     */
    public static String buildGetUrl(String url, Map<String, String> params) {
        int i = 0;
        String paramStr = "";
        for (String key : params.keySet()) {
            paramStr += "&" + key + "=" + params.get(key);
        }
        return url + paramStr.replaceFirst("&", "?");
    }

    /**
     * 构建FORM表单
     * @param url
     * @param methodType
     * @param parameters
     * @return
     */
    public static String buildFormHtml(String url, String methodType, Map<String, String> parameters) {
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<form target=\"_self\" id=\"pay-submit-form\" name=\"pay-submit-form\" action=\""
                + url
                + "\" method=\"" + methodType + "\">");
        if (parameters != null) {
            for (String name : parameters.keySet()) {
                String value = parameters.get(name);
                sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
            }
        }
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"Encodes\" style=\"display:none;\">");
        sbHtml.append("</form>");
        sbHtml.append("<script>document.forms['pay-submit-form'].submit();</script>");
        return sbHtml.toString();
    }

}
