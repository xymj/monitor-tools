package com.monitor.zkmonitor.service;

import com.monitor.common.ResponseResult;
import com.monitor.zkmonitor.module.ZKSource;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 16:49
 * @Description:
 */

public interface ZookeeperSourceService {
    public ResponseResult addZksource(ZKSource zkSource);

    public ResponseResult updateZksource(ZKSource zkSource);

    public ResponseResult deleteZksource(String id);

    public ResponseResult getAllZksource();

    public ResponseResult getZksourceById(String id);

    public ResponseResult getZksourceByCondition(int page,int rows,String whereSql);




}
