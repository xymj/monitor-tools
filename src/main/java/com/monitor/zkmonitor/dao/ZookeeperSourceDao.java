package com.monitor.zkmonitor.dao;

import com.monitor.common.ResponseResult;
import com.monitor.zkmonitor.module.ZKSource;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 19:32
 * @Description:
 */
public interface ZookeeperSourceDao {

    public void initTable() throws SQLException;

    public Integer insert(ZKSource zkSource) throws SQLException;

    public Integer update(ZKSource zkSource) throws SQLException;

    public Integer delete(String id) throws SQLException;

    public List<ZKSource> selectAll() throws SQLException;

    public ZKSource selectById(String id) throws SQLException;

    public List<ZKSource> selectByPage(int page, int rows, String whereSql) throws SQLException;

    public List<ZKSource> selectByCondition(String whereSql) throws SQLException;
}
