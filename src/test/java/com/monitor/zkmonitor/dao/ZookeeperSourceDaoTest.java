package com.monitor.zkmonitor.dao;

import com.monitor.zkmonitor.dao.impl.ZookeeperSourceDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 17:44
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperSourceDaoTest {

    @Autowired
    private ZookeeperSourceDaoImpl zookeeperSourceDao;

    @Test
    public void test() throws SQLException {
        zookeeperSourceDao.test();
    }

    @Test
    public void test2() throws SQLException {
        zookeeperSourceDao.initTable();
    }

}