package cn.org.easysite.commons.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import static cn.org.easysite.commons.base.Response.Status.FAILURE;
import static cn.org.easysite.commons.base.Response.Status.SUCCESS;

/**
 * Created by Panda on 2016/9/27.
 * @author yinlin
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
    private boolean success = SUCCESS;

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

    public static Response build(Integer code, String message, Object data, boolean success) {
        Response response = new Response(code, success, data);
        response.setMsg(message);
        return response;
    }

    public static Response build(Object data) {
        return build(200, "请求成功", data, SUCCESS);
    }

    public static Response build(Integer code, String message, boolean success) {
        return build(code, message, null, success);
    }

    public static Response buildSuccess() {
        return build(200, null, SUCCESS);
    }

    public static Response buildSuccess(String message) {
        return build(200, message, SUCCESS);
    }

    public static Response buildError() {
        return build(500, null, FAILURE);
    }

    public static Response buildError(Integer code, String message) {
        return build(code, message, FAILURE);
    }

    public interface Status {
        int OK = 200;
        boolean SUCCESS = true;
        boolean FAILURE = false;
    }

    public static Response buildError(String message) {
        return build(500, message, FAILURE);
    }
}
