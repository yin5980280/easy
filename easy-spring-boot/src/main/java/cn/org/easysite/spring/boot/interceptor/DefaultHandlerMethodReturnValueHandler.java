package cn.org.easysite.spring.boot.interceptor;

import com.alibaba.fastjson.JSON;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import cn.org.easysite.framework.utils.SpringApplicationUtils;
import cn.org.easysite.spring.boot.configuration.GlobalReturnValuePathProperties;
import cn.org.easysite.spring.boot.interceptor.annoation.Ignored;
import cn.org.easysite.spring.boot.model.Response;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-01-30 16:29
 * @Description :
 * @Copyright : Copyright (c) 2018
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.interceptor.DefaultHandlerMethodReturnValueHandler
 */
@Slf4j
public class DefaultHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    private static Map<String, Pattern> PATTERN_MAP = new HashMap<>();

    public DefaultHandlerMethodReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        if (this.globalEnhancer(returnValue, returnType, mavContainer, webRequest)) {
            Response res = Response.build(returnValue);
            if (log.isDebugEnabled()) {
                log.debug("接口返回数据结果[{}]", JSON.toJSONString(res));
            }
            delegate.handleReturnValue(res, returnType, mavContainer, webRequest);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("接口返回数据结果[{}]", JSON.toJSONString(returnValue));
            }
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
        }
    }

    /**
     * 判断是否标注ignored
     * @param returnType
     * @return
     */
    private boolean ignored(MethodParameter returnType) {
        boolean typeIgnored = returnType.getMethod() != null && returnType.getMethod().getDeclaringClass().isAnnotationPresent(Ignored.class);
        return typeIgnored || returnType.getMethodAnnotation(Ignored.class) != null;
    }

    /**
     * 全局增强
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param webRequest
     * @return
     */
    private boolean globalEnhancer(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        boolean need = !ignored(returnType) && !excludeUrl(webRequest.getNativeRequest(HttpServletRequest.class));
        if (need) {
            return !(returnValue instanceof Json || returnValue instanceof Response);
        }
        return false;
    }

    /**
     * 处理过滤url
     * @param request
     * @return
     */
    private boolean excludeUrl(HttpServletRequest request) {
        GlobalReturnValuePathProperties pathProperties = SpringApplicationUtils.getBean(GlobalReturnValuePathProperties.class);
        if (pathProperties.getExcludes() == null || pathProperties.getExcludes().length == 0) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : pathProperties.getExcludes()) {
            Pattern p = getPattern(pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 缓存效率更高
     * @param pattern
     * @return
     */
    private Pattern getPattern(String pattern) {
        Pattern p = PATTERN_MAP.get(pattern);
        if (p == null) {
            p = Pattern.compile("^" + pattern);
            PATTERN_MAP.put(pattern, p);
        }
        return p;
    }

}
