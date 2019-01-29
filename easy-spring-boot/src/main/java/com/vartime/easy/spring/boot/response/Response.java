package com.vartime.easy.spring.boot.response;

import com.vartime.easy.commons.base.BaseObject;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Panda on 2016/9/27.
 *
 * @version 1.0
 * @description com.vpay.exchange.base.Response
 */
@Data
@NoArgsConstructor
public class Response<T> extends BaseObject {

    private static final long serialVersionUID = 8105076289642490706L;

    /**
     * 错误码
     */
    private Integer code = Status.OK;

    /**
     * 是否操作成功
     */
    private boolean success = Status.SUCCESS;

    /**
     * 数据返回数据对象
     */
    private T data;

    /**
     * 错误消息
     */
    private String msg;

    public Response(T data) {
        this.data = data;
        this.code = Status.OK;
    }

    public Response(String message) {
        this.msg = message;
    }

    public Response(Integer code, boolean success, T data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public static Response build(Integer code, String message, Object content, boolean success) {
        Response response = new Response(code, success, content);
        response.setMsg(message);
        return response;
    }

    public static Response build(Object content) {
        return build(200, "请求成功", content, true);
    }

    public static Response build(Integer code, String message, boolean success) {
        return build(code, message, null, success);
    }

    public static Response buildSuccess() {
        return build(200, null, true);
    }

    public static Response buildSuccess(String message) {
        return build(200, message, true);
    }

    public static Response buildError() {
        return build(500, null, false);
    }

    public static Response buildError(Integer code, String message) {
        return build(code, message, false);
    }

    public interface Status {
        int OK = 200;
        boolean SUCCESS = true;
        boolean FAILURE = false;
    }

    public static Response buildError(String message) {
        return build(500, message, false);
    }
}
