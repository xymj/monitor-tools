package com.monitor.zkmonitor.service.impl;

import com.google.common.collect.Lists;
import com.monitor.common.ResponseResult;
import com.monitor.common.Status;
import com.monitor.zkmonitor.dao.ZookeeperSourceDao;
import com.monitor.zkmonitor.dao.impl.ZookeeperSourceDaoImpl;
import com.monitor.zkmonitor.module.ZKSource;
import com.monitor.zkmonitor.service.ZookeeperSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 19:28
 * @Description:
 */

@Slf4j
@Service
public class ZookeeperSourceServiceImpl implements ZookeeperSourceService, InitializingBean {

    @Autowired
    private ZookeeperSourceDao zookeeperSourceDao;

    @Override
    public ResponseResult addZksource(ZKSource zkSource) {
        ResponseResult result = ResponseResult.SUCESS();
        try {
            Integer row = zookeeperSourceDao.insert(zkSource);
            if (null == row) {
                result = ResponseResult.ERROR();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult updateZksource(ZKSource zkSource) {
        if (StringUtils.isBlank(zkSource.getId())) {
            return ResponseResult.ERROR("zkSource id is null");
        }
        ResponseResult result = ResponseResult.SUCESS();
        try {
            Integer row = zookeeperSourceDao.update(zkSource);
            if (null == row) {
                result = ResponseResult.ERROR();
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult deleteZksource(String id) {
        ResponseResult result = ResponseResult.SUCESS();
        try {
            Integer row = zookeeperSourceDao.delete(id);
            if (null == row) {
                result = ResponseResult.ERROR();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult getAllZksource() {
        ResponseResult result = ResponseResult.SUCESS();
        try {
            List<ZKSource> zkSources = zookeeperSourceDao.selectAll();
            if (!CollectionUtils.isEmpty(zkSources)) {
                result.setData(zkSources);
            } else {
                result = ResponseResult.ERROR(Lists.newArrayList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult getZksourceById(String id) {
        if (StringUtils.isBlank(id)) {
            log.error("getZksourceById id is null");
            return ResponseResult.ERROR("id null");
        }
        ResponseResult result = ResponseResult.SUCESS();
        try {
            ZKSource zkSource = zookeeperSourceDao.selectById(id);
            if (zkSource != null) {
                result.setData(zkSource);
            } else {
                result = ResponseResult.ERROR(zkSource);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResponseResult getZksourceByCondition(int page, int rows, String whereSql) {
        ResponseResult result = ResponseResult.SUCESS();
        try {
            List<ZKSource> zkSources = zookeeperSourceDao.selectByPage(page, rows, whereSql);
            if (!CollectionUtils.isEmpty(zkSources)) {
                result.setData(zkSources);
            } else {
                result = ResponseResult.ERROR(Lists.newArrayList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            zookeeperSourceDao.initTable();
            log.info("init data table sucess");
        } catch (Exception e) {
            log.error("init data table error : ",e);
        }
    }
}
