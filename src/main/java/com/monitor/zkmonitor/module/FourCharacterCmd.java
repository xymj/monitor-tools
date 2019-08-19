package com.monitor.zkmonitor.module;

import lombok.Data;

/**
 * @Author: xymj
 * @Date: 2019/6/28 0028 14:14
 * @Description:
 */


public enum FourCharacterCmd {
    STAT("stat"), //用于获取ZkServer运行时的状态信息，包括zk版本、打包信息、运行时角色、集群数据节点个数等
    CONF("conf"), //用于输出ZkServer运行时使用的基本配置信息，包括clientPort、dataDir、trickTime等
    CONS("cons"), //用于输出当前这台服务器上所有客户端的连接信息，包括每个客户端的IP、回话ID、最后一次与服务器交互的操作类型等
    CRST("crst"), //功能性命令，用于重置所有客户端连接统计信息
    DUMP("dump"), //用于输出当前集群的所有会话信息，包括会话ID、每个会话创建的临时节点信息等
    ENVI("envi"), //用于输出ZkServer所有服务器运行时的环境信息，包括os.version、java.version、user.home等
    ROUK("rouk"), //用于输出当前ZkServer是否正在运行，（Are you ok）,imok表示正常，只是代表2181端口服务正常
    SRVR("srvr"), //同stat，但不输出客户端的连接情况
    SRST("srst"), //功能性命令，用于重置所有服务器的统计信息
    WCHS("wchs"), //用于输出当前ZkServer上管理的Watcher概要信息
    WCHC("wchc"), //用于输出当前ZkServer上管理的Watcher详细信息
    WCHP("wchp"), //类似wchc，不同点在于以节点路径为单位进行分组输出Watcher详细信息
    MNTR("mntr"); //类似stat，比stat更详细，输出包括请求处理的延迟情况、服务器内存数据库大小、集群数据同步情况等


    private String cmd;

    FourCharacterCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
