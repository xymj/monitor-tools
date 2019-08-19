package com.monitor.common.module;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 20:17
 * @Description:
 */
public enum Status {
    SUCESS(200),
    ERROR(500);

    private Integer code;

    Status(Integer code) {
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
