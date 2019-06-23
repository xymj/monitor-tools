package com.monitor.zkmonitor.config;

import com.monitor.zkmonitor.constant.H2SqlConstant;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 23:14
 * @Description:
 */
@Configuration
@PropertySource("classpath:sql/sql.properties")
@Data
public class H2SqlInitConfig {

    @Value("${h2.sql.init}")
    private String initSql;

    @Value("${h2.sql.add}")
    private String addSql;

    @Value("${h2.sql.update}")
    private String updateSql;

    @Value("${h2.sql.delete}")
    private String deleteSql;

    @Value("${h2.sql.count}")
    private String countSql;

    @Value("${h2.sql.select.all}")
    private String selectAllSql;

    @Value("${h2.sql.select.by.id}")
    private String selectIdSql;

    @Value("${h2.sql.select.pagination}")
    private String selectPageSql;

    @PostConstruct
    public void initConstant() {
        H2SqlConstant.INIT_SQL = initSql;
        H2SqlConstant.ADD_SQL = addSql;
        H2SqlConstant.UPDATE_SQL = updateSql;
        H2SqlConstant.DELETE_SQl = deleteSql;
        H2SqlConstant.COUNT_SQL = countSql;
        H2SqlConstant.SELECT_ALL_SQL = selectAllSql;
        H2SqlConstant.SELECT_ID_SQL = selectIdSql;
        H2SqlConstant.SELECT_PAGE_SQL = selectPageSql;

    }
}
