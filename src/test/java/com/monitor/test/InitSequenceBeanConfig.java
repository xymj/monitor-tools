package com.monitor.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 14:24
 * @Description:
 */

@Configuration
public class InitSequenceBeanConfig {

    @Bean(initMethod = "initMethod", name = "initSequenceBean")
    public InitSequenceBean initSequenceBean() {
        return new InitSequenceBean();
    }
}
