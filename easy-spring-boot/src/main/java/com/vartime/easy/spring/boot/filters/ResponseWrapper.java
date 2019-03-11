package com.vartime.easy.spring.boot.filters;



import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static com.vartime.easy.commons.constants.BaseConstants.DEFAULT_CHARSET_OBJ;
import static com.vartime.easy.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_JS;
import static com.vartime.easy.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_JSON;
import static com.vartime.easy.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_TEXT;
import static com.vartime.easy.commons.constants.BaseConstants.HTTP_CONTENT_TYPE_XML;


/**
 * @version 1.0
 * @date 2016/10/20 10:48
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private PrintWriter writer = new PrintWriter(new OutputStreamWriter(bos, DEFAULT_CHARSET_OBJ));
    private ServletOutputStream teeServletOutputStream;
    private String responseText;

    private ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public static ResponseWrapper getResponseWrapper(HttpServletResponse response) {
        ResponseWrapper responseWrapper;
        if (response instanceof ResponseWrapper) {
            responseWrapper = (ResponseWrapper) response;
        } else {
            responseWrapper = new ResponseWrapper(response);
        }
        return responseWrapper;
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (null != teeServletOutputStream) {
            return teeServletOutputStream;
        }
        teeServletOutputStream = new ServletOutputStream() {
            private TeeOutputStream tos = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }

            @Override
            public void write(int b) throws IOException {
                tos.write(b);
            }
        };
        return teeServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new TeePrintWriter(super.getWriter(), writer);
    }

    public String getResponseText() {
        if (null != responseText) {
            return responseText;
        }
        if (bos.size() == 0) {
            return null;
        }
        String contentType = super.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            return null;
        }
        if (contentType.startsWith(HTTP_CONTENT_TYPE_JSON) || contentType.startsWith(HTTP_CONTENT_TYPE_TEXT)
                || contentType.contains(HTTP_CONTENT_TYPE_XML) || contentType.contains(HTTP_CONTENT_TYPE_JS)) {
            Charset charset = this.getResponseCharset();
            responseText = new String(bos.toByteArray(), charset);
        }
        return responseText;
    }

    private Charset getResponseCharset() {
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
}
