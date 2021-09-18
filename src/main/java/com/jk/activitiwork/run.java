package com.jk.activitiwork;

import com.jk.activitiwork.utils.activitiUtils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

public class run {
    /**
     * 部署流程
     * 执行完，就会像act_re_procdef、act_re_deployment和act_ge_bytearray三张表中，插入数据
     */
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public void delployFlow(){
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                .createDeployment()//创建一个部署对象
                .name("报销流程")    //添加部署对象名称
                .addClasspathResource("useraudit.bpmn")//从classpath的资源加载，一次只能加载一个文件
                .addClasspathResource("useraudit.png")//从classpath的资源加载，一次只能加载一个文件
                .deploy();//完成部署

        // 存在在数据库 act_re_procdef的DEPLOYMENT_ID_
        //act_re_procdef(流程定义数据表)  act_re_deployment  (部署信息表)   act_ge_bytearray（资源文件表）
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
    }
    /**
     * 启动流程  runtimeService对象
     * 执行完，就会在 act.ru.task表中，插入数据（每次插入一条数据）
     */
    public void flowStart() {
        RuntimeService runtimeService = processEngine.getRuntimeService();// 获取runtimeService对象
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("audit");//act_re_procdef(流程定义数据表)中的key_字段
    }
    /**
     * 查找代办任务信息
     */
    public void findEmployeeTask(){
        //数据库关系》》》》ID【act_re_deployment】 == ID【act_ru_execution】  == ID【act_ru_task】 ==》【ASSIGNEE_(cwh)】
        String assignee = "组长";  //节点的assignee_
        List<Task> taskList= processEngine.getTaskService()//获取任务启动流程的service
                .createTaskQuery()//创建查询对象
                .taskAssignee(assignee)//指定查询人
                .list();

        if(taskList.size()>0){
            for (Task task : taskList){
                System.out.println("代办任务ID:"+task.getId());
                System.out.println("代办任务name:"+task.getName());
                System.out.println("代办任务创建时间:"+task.getCreateTime());
                System.out.println("代办任务办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
            }
        }
    }

    /**
     * 处理流程
     *
     * 根据act_ru_task 任务表的id，获取流程的service对象，调用complete方法来处理流程。
     *
     * @Description:
     */
    public void completeTask(){
        // ID【act_ru_task】
        String taskId = "";
        processEngine.getTaskService().complete(taskId);//完成任务
        System.out.println("完成任务，任务ID:"+taskId);

    }

    public static void main(String[] args) {
        run r = new run();
        r.delployFlow();
        r.flowStart();
        r.findEmployeeTask();
        r.completeTask();

    }






}