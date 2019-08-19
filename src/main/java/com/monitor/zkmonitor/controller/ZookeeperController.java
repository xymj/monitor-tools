package com.monitor.zkmonitor.controller;

import com.monitor.common.module.ResponseResult;
import com.monitor.common.utils.CommonUtils;
import com.monitor.zkmonitor.module.ZKSource;
import com.monitor.zkmonitor.service.ZookeeperService;
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
 * @Date: 2019/6/19 0019 18:42
 * @Description:
 */

@Api(tags = "ZK操作Controller")
@RequestMapping("/zk")
@RestController
public class ZookeeperController {

    @Autowired
    ZookeeperService zookeeperService;

    @ApiOperation(value = "获取ZK连接状态", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
    })
    @RequestMapping(value = "/getZkConnectStatus", method = RequestMethod.GET)
    public String getZkConnectStatus(String cacheId) {
        return CommonUtils.toJson(zookeeperService.getZkConnectStatus(cacheId));
    }

    @ApiOperation(value = "获取ZK节点数据", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "path", value = "获取zk数据节点路径", required = true),
    })
    @RequestMapping(value = "/getZkNodeInfo", method = RequestMethod.POST)
    public String getZkNodeInfo(String cacheId, String path) {
        ResponseResult result = zookeeperService.getZkNodeInfo(cacheId, path);
        System.out.println("ZookeeperController ResponseResult:" + result.toString());
        String res = CommonUtils.toJson(result);
        System.out.println("ZookeeperController res:" + res);
        return String.format("getData: cacheId->%s,path->%s,,result->%s",
                cacheId,path,res);
    }

    @ApiOperation(value = "获取ZK JMX信息", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "cmd", value = "获取查询信息四字命令(Four-character command)", required = false),
    })
    @RequestMapping(value = "/getZkJmxInfo", method = RequestMethod.GET)
    public String getZkJmxInfo(String cacheId, String cmd){
        return CommonUtils.toJson(zookeeperService.getZkJmxInfo(cacheId, cmd));
    }

    @ApiOperation(value = "获取ZK指定路径节点树", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "path", value = "获取zk数据节点路径", required = true),
    })
    @RequestMapping(value = "/getZkNodeTree", method = RequestMethod.POST)
    public String getZkNodeTree(String cacheId, String path){
        return CommonUtils.toJson(zookeeperService.getZkNodeTree(cacheId, path));
    }

    @ApiOperation(value = "更新ZK节点数据", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "path", value = "获取zk数据节点路径", required = true),
            @ApiImplicitParam(name = "data", value = "更新的数据", required = true),
    })
    @RequestMapping(value = "/updateZkNodeData", method = RequestMethod.POST)
    public String updateZkNodeData(String cacheId, String path, String data){
        return CommonUtils.toJson(zookeeperService.updateZkNodeData(cacheId,path,data));
    }

    @ApiOperation(value = "添加ZK节点", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "path", value = "添加节点的路径", required = true),
            @ApiImplicitParam(name = "createModeFlag", value = "添加节点类型：" +
                    "0-永久节点，1-永久有序节点，2-临时节点，3-临时有序", required = true),
    })
    @RequestMapping(value = "/addZkNode", method = RequestMethod.POST)
    public String addZkNode(String cacheId, String path, String data, int createModeFlag){
        return CommonUtils.toJson(zookeeperService.addZkNode(cacheId,path,data, createModeFlag));
    }

    @ApiOperation(value = "删除ZK节点", notes = "需正确传参")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cacheId", value = "zk服务主键id", required = true),
            @ApiImplicitParam(name = "path", value = "要删除节点路径", required = true),
    })
    @RequestMapping(value = "/deleteZkNode", method = RequestMethod.POST)
    public String deleteZkNode(String cacheId, String path){
        return CommonUtils.toJson(zookeeperService.deleteZkNode(cacheId,path));
    }
}
