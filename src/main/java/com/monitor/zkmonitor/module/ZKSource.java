package com.monitor.zkmonitor.module;

import lombok.Data;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 18:47
 * @Description:
 */
@Data
public class ZKSource {
    private String id;
    private String desc;
    private String connectStr;
    private long sessiontimeout;
}
