package com.hellohuandian.apps.ControllerLibrary.base.strategyController.action;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-20
 * Description: 任务动作，分组策略打包为一个单元(task)执行之前和之后
 */
public interface TaskAction
{
    /**
     * 任务执行之前
     *
     * @param taskAddress
     */
    void onTaskExecuteBefore(int taskAddress);

    /**
     * 任务执行之后
     *
     * @param taskAddress
     */
    void onTaskExecuteAfter(int taskAddress);
}
