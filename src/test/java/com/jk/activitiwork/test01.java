package com.jk.activitiwork;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

public class test01 {


    @Test
    public void test1(){
        //使用classpath下的 activiti.cfg.xml中的配置来创建processEngine对象
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        //mysql来自动创建24张表结构
        System.out.println(engine);

    }

    /***
     * 自定义的方式来加载配置文件
     */
    public void test2(){
        //首先创建procesEngineConfiguration对象
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //通过procesEngineConfiguration对象来创建 ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
    }








}