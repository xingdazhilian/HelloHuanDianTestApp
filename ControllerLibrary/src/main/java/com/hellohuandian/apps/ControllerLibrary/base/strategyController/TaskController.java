package com.hellohuandian.apps.ControllerLibrary.base.strategyController;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.TaskAction;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description: 任务控制，将一组策略打包成任务单元，对任务事件做支持
 */
public abstract class TaskController<T extends TaskStrategy> extends EventController<T>
{
    private int taskGroupId;
    private int taskAddress;

    private TaskAction taskAction;

    public TaskController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    public int getTaskGroupId()
    {
        return taskGroupId;
    }

    public void setTaskGroupId(int taskGroupId)
    {
        this.taskGroupId = taskGroupId;
    }

    public int getTaskAddress()
    {
        return taskAddress;
    }

    public void setTaskAddress(int taskAddress)
    {
        this.taskAddress = taskAddress;
    }

    public void setTaskAction(TaskAction taskAction)
    {
        this.taskAction = taskAction;
    }

    protected final void taskExecuteBefore()
    {
        if (taskAction != null)
        {
            taskAction.onTaskExecuteBefore(taskAddress);
        }
    }

    protected final void taskExecuteAfter()
    {
        if (taskAction != null)
        {
            taskAction.onTaskExecuteAfter(taskAddress);
        }
    }
}
