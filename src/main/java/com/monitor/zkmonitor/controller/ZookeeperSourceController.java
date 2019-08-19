package com.monitor.zkmonitor.controller;

import com.monitor.common.utils.CommonUtils;
import com.monitor.common.module.ResponseResult;
import com.monitor.zkmonitor.module.ZKSource;
import com.monitor.zkmonitor.service.ZookeeperSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xymj
 * @Date: 2019/6/19 0019 18:39
 * @Description:
 */

@Api(tags = "ZK配置Controller")
@RequestMapping("/zk")
@RestController
public class ZookeeperSourceController {

    @Autowired
    ZookeeperSourceService zookeeperSourceService;


    @ApiOperation(value = "新增ZK配置", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "desc", value = "ZK配置描述", required = true),
            @ApiImplicitParam(name = "connectStr", value = "ZK服务器链接地址", required = true),
            @ApiImplicitParam(name = "sessionTimeoutMs", value = "ZK回话超时时间，单位ms", dataType = "long", required = true)
    })
    @RequestMapping(value = "/addZksource", method = RequestMethod.POST)
    public String addZksource(String desc, String connectStr,
                              long sessionTimeoutMs) {
        ZKSource zkSource = new ZKSource();
        zkSource.setId(CommonUtils.uuidKey());
        zkSource.setDesc(desc);
        zkSource.setConnectStr(connectStr);
        zkSource.setSessiontimeout(sessionTimeoutMs);
        ResponseResult result = zookeeperSourceService.addZksource(zkSource);
        String res = CommonUtils.toJson(result);
        return String.format("addZksource: desc->%s,connectStr->%s,sessionTimeout->%d,result->%s",
                desc,connectStr,sessionTimeoutMs,res);
    }



    @ApiOperation(value = "更新ZK配置", notes = "需正确传参")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "更新ZK配置的唯一ID", required = true),
            @ApiImplicitParam(name = "desc", value = "ZK配置描述", required = true),
            @ApiImplicitParam(name = "connectStr", value = "ZK服务器链接地址", required = true),
            @ApiImplicitParam(name = "sessionTimeoutMs", value = "ZK回话超时时间，单位ms", required = true)
    })
    @RequestMapping(value = "/updateZksource", method = RequestMethod.POST)
    public String updateZksource(String id, String desc,
                                 String connectStr, long sessionTimeoutMs) {
        ZKSource zkSource = new ZKSource();
        zkSource.setId(id.trim());
        zkSource.setDesc(desc);
        zkSource.setConnectStr(connectStr);
        zkSource.setSessiontimeout(sessionTimeoutMs);
        ResponseResult result = zookeeperSourceService.updateZksource(zkSource);
        return CommonUtils.toJson(result);
    }



    @ApiOperation(value = "删除ZK配置", notes = "需正确传参")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "删除ZK配置的唯一ID", required = true),
    })
    @RequestMapping(value = "/deleteZksource", method = RequestMethod.GET)
    public String deleteZksource(String id) {
        if (StringUtils.isBlank(id)) {
            return CommonUtils.toJson(ResponseResult.ERROR());
        }
        return CommonUtils.toJson(zookeeperSourceService.deleteZksource(id.trim()));
    }



    @ApiOperation(value = "获取一个ZK配置", notes = "需正确传参")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要获取的ZK配置唯一ID", required = true),
    })
    @RequestMapping(value = "/getZksourceById", method = RequestMethod.POST)
    public String getZksourceById(String id) {
        if (StringUtils.isBlank(id)) {
            return CommonUtils.toJson(ResponseResult.ERROR());
        }
        return CommonUtils.toJson(zookeeperSourceService.getZksourceById(id));
    }



    @ApiOperation(value = "指定条件获取ZK配置", notes = "获取配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "rows", value = "每页行数", required = true),
            @ApiImplicitParam(name = "whereSql", value = "获取配置where条件", required = false),
    })
    @RequestMapping(value = "/getZksourceByCondition", method = RequestMethod.GET)
    public String getZksourceByCondition(int page,int rows,String whereSql) {
        return CommonUtils.toJson(
                zookeeperSourceService.getZksourceByCondition(page,rows,whereSql));
    }


    @ApiOperation(value = "获取所有ZK配置", notes = "获取所有配置")
    @RequestMapping(value = "/getAllZksource", method = RequestMethod.GET)
    public String getAllZksource() {
        return CommonUtils.toJson(zookeeperSourceService.getAllZksource());
    }


}
