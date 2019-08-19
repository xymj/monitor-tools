package com.monitor.zkmonitor.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: xymj
 * @Date: 2019/6/28 0028 15:20
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZkServe {

    private String ip;
    private int port;
    private String monitorData;

    public ZkServe(String ip, int port) {
        this.ip = ip;
        this.port = port;
        monitorData = StringUtils.EMPTY;
    }
}
