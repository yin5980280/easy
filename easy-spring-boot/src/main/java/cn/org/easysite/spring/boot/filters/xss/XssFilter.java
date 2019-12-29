package cn.org.easysite.spring.boot.filters.xss;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-12-25 08:53
 * @Description : Xss过滤器
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : com.keruyun.wallet.framework.web.filter.xss.XssFilter
 */
public class XssFilter implements Filter {

    public static final String XSS_FILTER_URL_EXCLUDES = "xss.filter.url.excludes";

    public static final String XSS_FILTER_ENABLED = "xss.filter.enabled";

    /**
     * 排除链接
     */
    public List<String> excludes = new ArrayList<>();

    /**
     * xss过滤开关
     */
    public boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tempExcludes = filterConfig.getInitParameter(XSS_FILTER_URL_EXCLUDES);
        String tempEnabled = filterConfig.getInitParameter(XSS_FILTER_ENABLED);
        if (StringUtils.isNotEmpty(tempExcludes)) {
            excludes.addAll(Arrays.asList(tempExcludes.split(",")));
        }
        if (StringUtils.isNotEmpty(tempEnabled)) {
            enabled = Boolean.valueOf(tempEnabled);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //处理非过滤请求
        if (handleExcludeURL(req, resp)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    /**
     * 处理过滤url
     * @param request
     * @param response
     * @return
     */
    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (!enabled) {
            return true;
        }
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void destroy() {

    }
}
