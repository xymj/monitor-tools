package com.monitor.zkmonitor.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xymj
 * @Date: 2019/6/15 0015 23:09
 * @Description:
 */

//@Controller
@RestController
@RequestMapping("/demo")
public class HelloController2 {

    @RequestMapping(name = "/hello2")
    //@ResponseBody
    public String hello() {
        return "hello2  ResponseBody";
    }
}
