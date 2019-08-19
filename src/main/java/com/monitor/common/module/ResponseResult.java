package com.monitor.common.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 19:07
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult {
    // 响应业务状态
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;

    public ResponseResult(Object data) {
        this.status = Status.SUCESS.getCode();
        this.msg = "SUCESS";
        this.data = data;
    }


    public static ResponseResult build(Status status, String msg, Object data) {
        return new ResponseResult(status.getCode(),msg,data);
    }

    public static ResponseResult build(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult SUCESS() {
        return new ResponseResult(null);
    }

    public static ResponseResult SUCESS(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult ERROR() {
        return new ResponseResult(Status.ERROR.getCode(), "ERROR",null);
    }

    public static ResponseResult ERROR(Object data) {
        return new ResponseResult(Status.ERROR.getCode(), "ERROR",data);
    }

    public static ResponseResult ERROR(String msg) {
        return new ResponseResult(Status.ERROR.getCode(), msg,null);
    }

}
