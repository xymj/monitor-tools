package com.monitor.zkmonitor.service;

import com.monitor.common.module.ResponseResult;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 10:25
 * @Description:
 */
public interface ZookeeperService {

    public ResponseResult getZkConnectStatus(String cacheId);
    public ResponseResult getZkNodeInfo(String cacheId, String path);
    public ResponseResult getZkJmxInfo(String cacheId, String cmd);
    public ResponseResult getZkNodeTree(String cacheId, String path);
    public ResponseResult updateZkNodeData(String cacheId, String path, String data);
    public ResponseResult addZkNode(String cacheId, String path, String data, int modeFlag);
    public ResponseResult deleteZkNode(String cacheId, String path);
}
