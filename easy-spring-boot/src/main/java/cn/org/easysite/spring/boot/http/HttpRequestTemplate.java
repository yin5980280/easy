package cn.org.easysite.spring.boot.http;

import com.fasterxml.jackson.databind.JavaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.org.easysite.commons.utils.JsonMapper;
import cn.org.easysite.commons.utils.MapUtils;
import cn.org.easysite.commons.utils.XmlUtils;
import cn.org.easysite.spring.boot.utils.MDCUtils;
import lombok.extern.slf4j.Slf4j;

import static cn.org.easysite.commons.constants.BaseConstants.DEFAULT_CHARSET_OBJ;

/**
 * @author : yinlin
 * @version : 1.0
 * @date : 2019-09-27 10:47
 * @Description :
 * @Copyright : Copyright (c) 2019
 * @Company : EasySite Technology Chengdu Co. Ltd.
 * @link : cn.org.easysite.spring.boot.http.HttpRequestTemplate
 */
@Slf4j
public class HttpRequestTemplate {
    /**
     * 定义JSON
     */
    private static final MediaType MEDIA_TYPE_JSON = new MediaType("application", "json", DEFAULT_CHARSET_OBJ);
    /**
     * 定义FORM请求
     */
    private static final MediaType MEDIA_TYPE_FORM = new MediaType("application", "x-www-form-urlencoded", DEFAULT_CHARSET_OBJ);

    /**
     * 定义XML请求
     */
    private static final MediaType MEDIA_TYPE_XML = new MediaType("application", "xml", DEFAULT_CHARSET_OBJ);

    private RestTemplate restTemplate;

    public HttpRequestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T postJson(String url, Object request, Class<T> responseType) throws RestClientException {
        if (responseType == String.class) {
            return (T) this.postJson(url, request);
        }
        return this.postJson(url, request, JsonMapper.contructType(responseType));
    }

    public <T> T postJson(String url, Object request, JavaType responseType) throws RestClientException {
        String res = this.postJson(url, request);
        return JsonMapper.fromJson(res, responseType);
    }

    /**
     *  post 支持带http header信息
     * @param url
     * @param request
     * @param headersParam
     * @return
     * @throws RestClientException
     */
    public String postJson(String url, Object request, Map<String, List<String>> headersParam) throws RestClientException {
        return this.exchange(url, HttpMethod.POST, JsonMapper.toJson(request), headersParam);
    }


    public String postJson(String url, Object request) throws RestClientException {
        return this.exchange(url, HttpMethod.POST, JsonMapper.toJson(request));
    }

    public String postJson(String url, String json) throws RestClientException {
        return this.exchange(url, HttpMethod.POST, json);
    }

    public <T> T postJsonForm(String url, Object request, Class<T> responseType) throws RestClientException {
        if (responseType == String.class) {
            return (T) this.postJsonForm(url, request);
        }
        return this.postJsonForm(url, request, JsonMapper.contructType(responseType));
    }

    public <T> T postJsonForm(String url, Object request, JavaType responseType) throws RestClientException {
        String res = this.postJsonForm(url, request);
        return JsonMapper.fromJson(res, responseType);
    }

    public String postJsonForm(String url, Object request) throws RestClientException {
        Map<String, Object> map = MapUtils.toJsonMap(request);
        return this.postForm(url, map);
    }

    public <T> T postForm(String url, Object request, Class<T> responseType) throws RestClientException {
        Map<String, Object> map = MapUtils.toTreeMap(request, true);
        return this.postForm(url, map, responseType);
    }

    /**
     * form 提交
     * @param url
     * @param request
     * @param responseType
     * @param <T>
     * @return
     * @throws RestClientException
     */
    public <T> T postForm(String url, Map<String, ? extends Object> request, Class<T> responseType) throws RestClientException {
        String res = this.postForm(url, request);
        if (responseType == String.class) {
            return (T) res;
        }
        return JsonMapper.fromJson(res, responseType);
    }

    /**
     * form提交
     * @param url
     * @param request
     * @param responseType
     * @param <T>
     * @return
     * @throws RestClientException
     */
    public <T> T postForm(String url, Map<String, ? extends Object> request, JavaType responseType) throws RestClientException {
        String res = this.postForm(url, request);
        return JsonMapper.fromJson(res, responseType);
    }

    public String postForm(String url, Map<String, ? extends Object> request) throws RestClientException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        for (Map.Entry<String, ?> entry : request.entrySet()) {
            Object value = entry.getValue();
            if (null == value) {
                continue;
            }
            String s;
            if (value instanceof String) {
                s = (String) value;
            } else {
                s = value.toString();
            }
            map.add(entry.getKey(), s);
        }
        HttpHeaders headers = this.getHeaders(MEDIA_TYPE_FORM);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        return this.httpExchange(url, HttpMethod.POST, httpEntity);
    }

    /**
     * 提交xml
     * @param url
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postXml(String url, Object request, Class<T> clazz) {
        String res = this.postXml(url, request);
        return XmlUtils.xml2Object(res, clazz);
    }

    public String postXml(String url, Object request) {
        String xml;
        if (request instanceof String) {
            xml = (String) request;
        } else {
            xml = XmlUtils.object2Xml(request);
        }
        HttpHeaders headers = this.getHeaders(MEDIA_TYPE_XML);
        HttpEntity<String> httpEntity = new HttpEntity<>(xml, headers);
        return this.httpExchange(url, HttpMethod.POST, httpEntity);
    }

    public <T> T get(String url, JavaType responseType) throws RestClientException {
        String res = this.get(url);
        return JsonMapper.fromJson(res, responseType);
    }

    public String get(String url) throws RestClientException {
        return this.exchange(url, HttpMethod.GET, null);
    }

    private String exchange(String url, HttpMethod method, String body) throws RestClientException {
        return this.exchange(url, method, body, new HttpHeaders());
    }

    private HttpHeaders getHeaders(MediaType mediaType) {
        HttpHeaders headers = this.getHeaders();
        if (null != mediaType) {
            headers.setContentType(mediaType);
        }
        return headers;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(MDCUtils.KEY_MSG_ID, MDCUtils.getOrGenMsgId());
        return headers;
    }

    /**
     * 支持传header
     *
     * @param url
     * @param method
     * @param body
     * @return
     * @throws RestClientException
     */
    public String exchange(String url, HttpMethod method, String body, Map<String, List<String>> headersParam) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        if (headersParam != null) {
            Iterator<Map.Entry<String, List<String>>> it = headersParam.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<String>> row = it.next();
                headers.put(row.getKey(), row.getValue());
            }
        }

        return this.exchange(url, method, body, headers);
    }

    public String exchange(String url, HttpMethod method, String body, HttpHeaders headers) {
        HttpEntity<String> httpEntity;
        if (HttpMethod.POST == method) {
            headers.setContentType(MEDIA_TYPE_JSON);
            httpEntity = new HttpEntity<>(body, headers);
        } else {
            httpEntity = new HttpEntity<>(headers);
        }
        return this.httpExchange(url, method, httpEntity);
    }

    public String httpExchange(String url, HttpMethod method, HttpEntity httpEntity) throws RestClientException {
        return this.httpExchange(url, method, httpEntity, String.class);
    }

    public <T> T httpExchange(String url, HttpMethod method, HttpEntity httpEntity, Class<T> responseType) throws RestClientException {
        if (StringUtils.isBlank(url)) {
            throw new RestClientException("request url can not blank");
        }
        if (url.indexOf(' ') >= 0) {
            log.warn("url中含有空格，现将空格转换为20%，以便能正常执行请求");
            url = url.replaceAll(" ", "20%");
        }
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType);
            HttpStatus status = responseEntity.getStatusCode();
            T res = responseEntity.getBody();
            if (status == HttpStatus.OK) {
                return res;
            }
        } catch (RestClientException e) {
            log.error("request RestClientException：" + e.getLocalizedMessage());
            throw e;
        }
        return null;
    }
}
