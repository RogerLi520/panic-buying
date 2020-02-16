package com.wenyanwen123.buy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * log4j2测试类
 */
@RestController
public class Log4jTest {

    private Logger logger = LoggerFactory.getLogger(Log4jTest.class);

    @RequestMapping("/logtest")
    public void test() {
        logger.info("log4j2输出测试");
    }

}
