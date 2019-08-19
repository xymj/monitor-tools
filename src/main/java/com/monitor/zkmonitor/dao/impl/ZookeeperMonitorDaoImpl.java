package com.monitor.zkmonitor.dao.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.monitor.common.service.LocalCacheService;
import com.monitor.common.utils.CommonUtils;
import com.monitor.zkmonitor.constant.CommonConstant;
import com.monitor.zkmonitor.dao.ZookeeperMonitorDao;
import com.monitor.zkmonitor.dao.ZookeeperSourceDao;
import com.monitor.zkmonitor.module.ZKSource;
import com.monitor.zkmonitor.module.ZkServe;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xymj
 * @Date: 2019/6/28 0028 14:40
 * @Description:
 */
@Slf4j
@Repository
public class ZookeeperMonitorDaoImpl implements ZookeeperMonitorDao {

    @Autowired
    private LocalCacheService cacheService;

    @Autowired
    private ZookeeperSourceDao zookeeperSourceDao;


    @Override
    public String getZkJmxInfo(String cacheId, String cmd) throws Exception {
        String data = getMonitorData(
                getZkServers(getZkServerIp(cacheId)), cmd);
        System.out.println(data);
        return data;
    }


    private List<ZkServe> getZkServers(String zkServerIp) {
        if (StringUtils.isBlank(zkServerIp)) {
            return Lists.newArrayList();
        }
        String[] addrs = zkServerIp.split(",");
        return Arrays.asList(addrs).stream().map(addr -> {
            String[] host = addr.split(":");
            if (host.length == 2) {
                return new ZkServe(host[0], Integer.parseInt(host[1]));
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private String getMonitorData(List<ZkServe> zkServers, String cmd) {
        if (CollectionUtils.isEmpty(zkServers)) {
            return StringUtils.EMPTY;
        }
        Map<String, String> data = Maps.newHashMap();
        zkServers.stream().forEach(zkServe -> {
            try {
                String monitorData = data.get(zkServe.getIp());
                if (StringUtils.isBlank(monitorData)) {
                    data.put(zkServe.getIp(), StringUtils.EMPTY);
                }
                ZkServe zkServerRes = executeCmd(zkServe, cmd);
                data.put(zkServerRes.getIp(), zkServerRes.getMonitorData());
                System.out.println(zkServe.getMonitorData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return CommonUtils.toJson(data);
    }

    public ZkServe executeCmd(ZkServe zkServe, String cmd) throws IOException {

        Socket socket = null;
        OutputStream socketOut = null;
        InputStream socketIn = null;
        BufferedReader reader = null;

        try {
            socket = new Socket(zkServe.getIp(), zkServe.getPort());
            socket.setSoTimeout(100);
            System.out.println(socket.toString());
            socketOut = socket.getOutputStream();
            socketOut.write(cmd.getBytes());
            socketOut.flush();

            socketIn = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(socketIn));
            List<String> data = reader.lines().collect(Collectors.toList());
            zkServe.setMonitorData(CommonUtils.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (socketIn != null) {
                socketIn.close();
            }
            if (socketOut != null) {
                socketOut.close();
            }
            if (socket != null) {
                socket.close();
            }
        }

        return zkServe;
    }


    private String getZkServerIp(String cacheId) throws SQLException {
        if (StringUtils.isBlank(cacheId)) {
            return StringUtils.EMPTY;
        }
        String cacheKey = new StringBuilder()
                .append(cacheId)
                .append(CommonConstant.DEFAULT_SPLIT_CHARACTER)
                .append(CommonConstant.ZK_SERVER_IP_CACHE_SUFFIX).toString();

        String serverIps = (String) cacheService.mget(cacheKey);

        if (StringUtils.isBlank(serverIps)) {
            System.out.println("miss hint server ip");
            ZKSource zkSource = zookeeperSourceDao.selectById(cacheId);
            if (null == zkSource) {
                return StringUtils.EMPTY;
            }
            serverIps = zkSource.getConnectStr().trim();
            cacheService.set(cacheKey, serverIps);
        } else {
            System.out.println("hint server ip");
        }

        return serverIps;
    }


}
