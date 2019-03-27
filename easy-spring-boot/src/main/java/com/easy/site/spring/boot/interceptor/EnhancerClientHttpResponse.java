package com.easy.site.spring.boot.interceptor;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * client response 代理
 * created by crush 2018-08-17
 *
 */
public class EnhancerClientHttpResponse implements ClientHttpResponse {

	private ClientHttpResponse clientHttpResponse;

	/** 原始响应体 */
	private String responseText;

	private InputStream inputStream;

	public EnhancerClientHttpResponse(ClientHttpResponse clientHttpResponse) {
		this.clientHttpResponse = clientHttpResponse;
	}

	public String responseText() {
		try {
			responseText = IOUtils.toString(clientHttpResponse.getBody(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}

	@Override
	public InputStream getBody() throws IOException {
		inputStream = IOUtils.toInputStream(responseText, "UTF-8");
		return inputStream;
	}

	@Override
	public HttpHeaders getHeaders() {
		return clientHttpResponse.getHeaders();
	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		return clientHttpResponse.getStatusCode();
	}

	@Override
	public int getRawStatusCode() throws IOException {
		return clientHttpResponse.getRawStatusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return clientHttpResponse.getStatusText();
	}

	@Override
	public void close() {
		clientHttpResponse.close();
		IOUtils.closeQuietly(inputStream);
	}

}
