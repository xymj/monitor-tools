package com.monitor.zkmonitor.module;

import lombok.Data;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 21:04
 * @Description:
 */
@Data
public class ZKACLMetaData {

    //权限模式
    private String scheme;
    //授权对象ID
    private String id;
    //权限
    private String permission;
}
