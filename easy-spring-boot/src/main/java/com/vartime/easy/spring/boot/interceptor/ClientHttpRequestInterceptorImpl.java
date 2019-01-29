/**
 *
 */
package com.vartime.easy.spring.boot.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * resttemplate 请求响应信息拦截器
 */
public class ClientHttpRequestInterceptorImpl implements
        ClientHttpRequestInterceptor {

	/** 日志 */
	private Log LOG = LogFactory.getLog("integration");

	/** aplication/json;charset=UTF-8 */
	private final MediaType application_json_utf8 = MediaType
			.parseMediaType("application/json;charset=UTF-8");

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.http.client.ClientHttpRequestInterceptor#intercept
	 * (org.springframework.http.HttpRequest, byte[],
	 * org.springframework.http.client.ClientHttpRequestExecution)
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {

		HttpHeaders headers = request.getHeaders();

		long start = System.currentTimeMillis();
		EnhancerClientHttpResponse resp = null;
		try {
			resp = new EnhancerClientHttpResponse(execution.execute(request, body));
		} finally {

			String respText = resp == null ? "" : resp.responseText();

			LOG.info(MessageFormat.format(
					"请求地址: {0}, 头信息：{4}, 请求参数: {1} => 返回结果: {2}。 [{3}]ms。 ", request
							.getURI().toString(), new String(body), respText,
					System.currentTimeMillis() - start, new ObjectMapper().writeValueAsString(headers.entrySet())));
		}

		return resp;
	}

}
