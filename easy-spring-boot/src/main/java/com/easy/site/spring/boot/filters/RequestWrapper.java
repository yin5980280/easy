package com.easy.site.spring.boot.filters;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import static com.easy.site.commons.constants.BaseConstants.DEFAULT_CHARSET_OBJ;
import static com.easy.site.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_JS;
import static com.easy.site.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_JSON;
import static com.easy.site.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_TEXT;
import static com.easy.site.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_XML;


/**
 * @version 1.0
 * @date 2016/10/20 10:48
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private BufferedServletInputStream bsis = null;

    private byte[] buffer = null;

    private String requestText;

    private RequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        // Read InputStream and store its content in a buffer.
        InputStream is = req.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int letti;
        while ((letti = is.read(buf)) > 0) {
            baos.write(buf, 0, letti);
        }
        this.buffer = baos.toByteArray();
    }

    public static RequestWrapper getRequestWrapper(HttpServletRequest request) throws IOException {
        RequestWrapper requestWrapper;
        if (request instanceof RequestWrapper) {
            requestWrapper = (RequestWrapper) request;
        } else {
            requestWrapper = new RequestWrapper(request);
        }
        return requestWrapper;
    }

    /**
     * 是否必须包装为RequestWrapper，GET、表单或者没有请求体的请求都不需要包装
     * @param request
     * @return
     */
    public static boolean isMustWrapper(HttpServletRequest request) {
        if (request.getContentLength() <= 0) {
            return false;
        }
        String contentType = request.getContentType();
        if (null == contentType) {
            return true;
        }
        return !contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                && !contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE);
    }

    @Override
    public ServletInputStream getInputStream() {
        if (null != this.bsis) {
            return this.bsis;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(this.buffer);
        this.bsis = new BufferedServletInputStream(bais);
        return this.bsis;
    }

    public String getRequestText() {
        if (null != requestText) {
            return requestText;
        }
        if (null == buffer || buffer.length == 0) {
            return null;
        }
        String contentType = super.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            return null;
        }
        if (contentType.startsWith(HTTP_CONTENT_TYPE_JSON)
                || contentType.startsWith(HTTP_CONTENT_TYPE_TEXT)
                || contentType.contains(HTTP_CONTENT_TYPE_XML)
                || contentType.contains(HTTP_CONTENT_TYPE_JS)) {
            //request body是文本
            Charset charset = this.getRequestCharset();
            requestText = new String(buffer, charset);
        }
        return requestText;
    }
    private Charset getRequestCharset() {
        String charset = super.getCharacterEncoding();
        if (null == charset) {
            //没有取到contentType，使用默认编码
            return DEFAULT_CHARSET_OBJ;
        }
        try {
            return Charset.forName(charset);
        } catch (Exception e) {
            //contentType有问题，使用默认编码
            return DEFAULT_CHARSET_OBJ;
        }
    }

    private static final class BufferedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }
    }
}
