package com.monitor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 14:25
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InitSequenceBeanConfig.class)
@Slf4j
public class InitSequenceBeanTest {

    @Autowired
    private InitSequenceBean initSequenceBean;

    @Test
    public void test(){
        log.info(initSequenceBean.toString());
    }
}
