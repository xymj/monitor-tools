package com.monitor.zkmonitor.service.impl;

import com.monitor.common.module.ResponseResult;
import com.monitor.zkmonitor.constant.CommonConstant;
import com.monitor.zkmonitor.dao.ZookeeperDao;
import com.monitor.zkmonitor.dao.ZookeeperMonitorDao;
import com.monitor.zkmonitor.module.FourCharacterCmd;
import com.monitor.zkmonitor.service.ZookeeperService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xymj
 * @Date: 2019/6/26 0026 16:08
 * @Description:
 */
@Service
public class ZookeeperServiceImpl implements ZookeeperService {



    @Autowired
    private ZookeeperDao zookeeperDao;

    @Autowired
    private ZookeeperMonitorDao zookeeperMonitorDao;

    @Override
    public ResponseResult getZkConnectStatus(String cacheId) {
        ResponseResult resp = ResponseResult.SUCESS();
        if (StringUtils.isBlank(cacheId)) {
            resp.setData(CommonConstant.DISCONNECTED_STATUS);
            return resp;
        }
        try {
            resp.setData(zookeeperDao.getZkConnectStatus(cacheId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    public ResponseResult getZkNodeInfo(String cacheId, String path) {
        try {
            String data = zookeeperDao.getZkNodeInfo(cacheId, path);
            System.out.println("ZookeeperServiceImpl res:"+ data);
            return ResponseResult.SUCESS(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.ERROR();
    }

    @Override
    public ResponseResult getZkJmxInfo(String cacheId ,String cmd) {
        if (StringUtils.isBlank(cmd)) {
            cmd = FourCharacterCmd.STAT.getCmd();
        }
        try {
            String zkJmxInfo = zookeeperMonitorDao.getZkJmxInfo(cacheId, cmd);
            return ResponseResult.SUCESS(zkJmxInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.ERROR();
    }

    @Override
    public ResponseResult getZkNodeTree(String cacheId, String path) {
        if (StringUtils.isBlank(cacheId)) {
            return ResponseResult.ERROR();
        }
        if (StringUtils.isBlank(path)) {
            path = CommonConstant.DEFAULT_ROOT_PATH;
        }

        if (!path.startsWith(CommonConstant.DEFAULT_ROOT_PATH)) {
            path = new StringBuilder()
                    .append(CommonConstant.DEFAULT_ROOT_PATH)
                    .append(path).toString();
        }
        try {
            String zkNodeTree = zookeeperDao.getZkNodeTree(cacheId, path);
            System.out.println(zkNodeTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.SUCESS();
    }

    @Override
    public ResponseResult updateZkNodeData(String cacheId, String path, String data) {
        ResponseResult result = ResponseResult.SUCESS();
        if (StringUtils.isBlank(cacheId) || StringUtils.isBlank(path)) {
            result.setData(CommonConstant.OPERATE_UNSUCESS_CONDITION);
            return result;
        }
        try {
            boolean flag = zookeeperDao.updateZkNodeData(cacheId, path, data);
            result.setData(flag ? CommonConstant.OPERATE_SUCESS_CONDITION
                    : CommonConstant.OPERATE_UNSUCESS_CONDITION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult addZkNode(String cacheId, String path, String data, int modeFlag) {
        ResponseResult result = ResponseResult.SUCESS();
        if (StringUtils.isBlank(cacheId) || StringUtils.isBlank(path)) {
            result.setData(CommonConstant.OPERATE_UNSUCESS_CONDITION);
            return result;
        }
        try {
            boolean flag = zookeeperDao.addZkNode(cacheId, path, data, modeFlag);
            result.setData(flag ? CommonConstant.OPERATE_SUCESS_CONDITION
                    : CommonConstant.OPERATE_UNSUCESS_CONDITION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult deleteZkNode(String cacheId, String path) {
        ResponseResult result = ResponseResult.SUCESS();
        if (StringUtils.isBlank(cacheId) || StringUtils.isBlank(path)) {
            result.setData(CommonConstant.OPERATE_UNSUCESS_CONDITION);
            return result;
        }
        try {
            boolean flag = zookeeperDao.deleteZkNode(cacheId, path);
            result.setData(flag ? CommonConstant.OPERATE_SUCESS_CONDITION
                    : CommonConstant.OPERATE_UNSUCESS_CONDITION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
