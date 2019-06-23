package com.monitor.zkmonitor.dao.impl;

import com.google.common.collect.Lists;
import com.monitor.common.CommonUtils;
import com.monitor.zkmonitor.aop.H2Aop;
import com.monitor.zkmonitor.constant.H2SqlConstant;
import com.monitor.zkmonitor.dao.ZookeeperSourceDao;
import com.monitor.zkmonitor.module.ZKSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 17:24
 * @Description:
 */

@Slf4j
@Repository
public class ZookeeperSourceDaoImpl implements ZookeeperSourceDao {

//    @Value("${h2.init-sql}")
//    private String initSql;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void initTable() throws SQLException {
        jdbcTemplate.execute(H2SqlConstant.INIT_SQL);
        //jdbcTemplate.update(initSql);
    }

    @H2Aop("insert")
    @Override
    public Integer insert(ZKSource zkSource) throws SQLException {
        if (StringUtils.isBlank(zkSource.getId())) {
            zkSource.setId(CommonUtils.uuidKey());
        }

        return jdbcTemplate.update(H2SqlConstant.ADD_SQL, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1,zkSource.getId());
                ps.setString(2,zkSource.getDesc());
                ps.setString(3,zkSource.getConnectStr());
                ps.setLong(4,zkSource.getSessiontimeout());
            }
        });
    }

    @H2Aop("update")
    @Override
    public Integer update(ZKSource zkSource) throws SQLException {
        return jdbcTemplate.update(H2SqlConstant.UPDATE_SQL, preparedStatement -> {
            preparedStatement.setString(1,zkSource.getDesc());
            preparedStatement.setString(2,zkSource.getConnectStr());
            preparedStatement.setLong(3,zkSource.getSessiontimeout());
            preparedStatement.setString(4,zkSource.getId());
        });
    }

    @H2Aop("delete")
    @Override
    public Integer delete(String id) throws SQLException {
        return jdbcTemplate.update(H2SqlConstant.DELETE_SQl, ps -> {
            ps.setString(1, id);
        });
    }

    @H2Aop("selectAll")
    @Override
    public List<ZKSource> selectAll() throws SQLException {
        List<ZKSource> zkSources = jdbcTemplate
                .query(H2SqlConstant.SELECT_ALL_SQL, BeanPropertyRowMapper.newInstance(ZKSource.class));
        return zkSources;
    }

    @H2Aop("selectById")
    @Override
    public ZKSource selectById(String id) throws SQLException {
        return jdbcTemplate.queryForObject(
                H2SqlConstant.SELECT_ID_SQL,new Object[]{id}, BeanPropertyRowMapper.newInstance(ZKSource.class));
    }

    @H2Aop("selectByPage")
    @Override
    public List<ZKSource> selectByPage(int page, int rows, String whereSql) throws SQLException {
        if (StringUtils.isBlank(whereSql)) {
            whereSql = "where 0=0";
        }
        String sql = String.format(H2SqlConstant.SELECT_PAGE_SQL,
                whereSql, (page - 1) * rows, rows);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ZKSource.class));
    }

    @H2Aop("selectByCondition")
    @Override
    public List<ZKSource> selectByCondition(String whereSql) throws SQLException {
        if (StringUtils.isBlank(whereSql)) {
            return Lists.newArrayList();
        }
        String sql = new StringBuilder(H2SqlConstant.SELECT_ALL_SQL)
                .append(" ").append(whereSql).toString();
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ZKSource.class));
    }



    public void test() throws SQLException {
        Connection connection =
                dataSource.getConnection();
        log.error("-----------------------");
        log.error(connection.toString());
        log.error("-----------------------");
        connection.close();

        int init = jdbcTemplate.update(H2SqlConstant.INIT_SQL);
        System.out.println("initSql:" + init);

        int update1 = jdbcTemplate.update("insert into zksource values ('123456','test','192.168.200.155:2181','1000' )");
        int update2 = jdbcTemplate.update("insert into zksource values ('123457','test','192.168.200.155:2181','1000' )");
        System.out.println("update1: " + update1);
        System.out.println("update2: " + update2);
        Long aLong = jdbcTemplate.queryForObject("select count(*) from  zksource", Long.class);
        System.out.println(aLong);

        //jdbcTemplate.update("drop table zksource");

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from zksource");
        System.out.println(maps.size());

        int init2 = jdbcTemplate.update(H2SqlConstant.INIT_SQL);
        System.out.println("initSql2:" + init2);
    }
}
