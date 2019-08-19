package com.monitor.zkmonitor.dao.impl;

import com.google.common.collect.Lists;
import com.monitor.zkmonitor.module.ZkServe;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: xymj
 * @Date: 2019/6/28 0028 16:15
 * @Description:
 */
public class ZookeeperMonitorDaoImplTest {

    @Test
    public void getZkJmxInfo() throws IOException {
        ZookeeperMonitorDaoImpl zookeeperMonitorDao = new ZookeeperMonitorDaoImpl();
        ZkServe zkServe5 = new ZkServe("192.168.200.155", 2181);
        ZkServe zkServe6 = new ZkServe("192.168.200.156", 2181);
        ZkServe zkServe7 = new ZkServe("192.168.200.157", 2181);
        ZkServe zkServe = new ZkServe("192.168.200.155", 2181);
        List<ZkServe> zkServes = Lists.newArrayList(zkServe, zkServe5, zkServe6, zkServe7);
        zkServes.stream().forEach(zkServe1 -> {
            ZkServe stat = null;
            try {
                zookeeperMonitorDao.executeCmd(zkServe1, "stat");
                System.out.println(zkServe1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}