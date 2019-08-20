package com.hellohuandian.apps.ControllerLibrary.base.strategy;

import com.hellohuandian.apps.ControllerLibrary.base.strategyController.TaskController;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
public abstract class TaskStrategy extends TimeStrategy
{
    private TaskController taskController;
    public final byte[] taskBytes;
    private boolean isLast;
    private boolean isLoopRead;

    public TaskStrategy(int id, byte[] taskBytes)
    {
        super(id);
        this.taskBytes = taskBytes;
    }

    public void setTaskController(TaskController taskController)
    {
        this.taskController = taskController;
    }

    public int getGroupindex()
    {
        return taskController != null ? taskController.getTaskGroupId() : 0;
    }

    public final void execute()
    {
        if (taskController != null)
        {
            taskController.execute(this);
        }
    }

    public boolean isLast()
    {
        return isLast;
    }

    public void setLast(boolean last)
    {
        isLast = last;
    }

    public boolean isLoopRead()
    {
        return isLoopRead;
    }

    public void setLoopRead(boolean loopRead)
    {
        isLoopRead = loopRead;
    }
}
