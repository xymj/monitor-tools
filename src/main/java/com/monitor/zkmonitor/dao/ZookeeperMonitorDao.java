package com.monitor.zkmonitor.dao;

/**
 * @Author: xymj
 * @Date: 2019/6/28 0028 14:39
 * @Description:
 */
public interface ZookeeperMonitorDao {
    public String getZkJmxInfo(String cacheId, String cmd) throws Exception;
}
