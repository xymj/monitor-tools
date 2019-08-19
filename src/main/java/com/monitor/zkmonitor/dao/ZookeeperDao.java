package com.monitor.zkmonitor.dao;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 11:57
 * @Description:
 */
public interface ZookeeperDao {

    public String getZkConnectStatus(String cacheId) throws Exception;
    public String getZkNodeInfo(String cacheId, String path) throws Exception;

    public String getZkNodeTree(String cacheId, String path) throws Exception;
    public boolean updateZkNodeData(String cacheId, String path, String data) throws Exception;
    public boolean addZkNode(String cacheId, String path, String data, int modeFlag) throws Exception;
    public boolean deleteZkNode(String cacheId, String path) throws Exception;

}
