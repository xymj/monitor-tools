package com.monitor.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 14:18
 * @Description:
 *
 *  从接口的名字上不难发现，InitializingBean 的作用就是在 bean 初始化后执行定制化的操作。
 *
 *  Spring 容器中的 Bean 是有生命周期的，Spring 允许在 Bean 在初始化完成后以及 Bean 销毁前执行特定的操作，常用的设定方式有以下三种：
 *     通过实现 InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
 *     通过 <bean> 元素的 init-method/destroy-method 属性指定初始化之后 /销毁之前调用的操作方法；
 *     在指定方法上加上@PostConstruct 或@PreDestroy注解来制定该方法是在初始化之后还是销毁之前调用。
 *
 *
 *  spring bean的初始化执行顺序：构造方法 --> @PostConstruct注解的方法 --> afterPropertiesSet方法 --> init-method指定的方法。
 *  afterPropertiesSet通过接口实现方式调用（效率上高一点），@PostConstruct和init-method都是通过反射机制调用

 */

@Slf4j
public class InitSequenceBean implements InitializingBean {

    public InitSequenceBean(){
        log.info("initSequenceBean: construct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("initSequenceBean: afterPropertiesSet");
    }

    @PostConstruct
    public void postConstruct(){
        log.info("initSequenceBean: postConstruct");
    }

    public void initMethod(){
        log.info("initSequenceBean: initMethod");
    }

}
