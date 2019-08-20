package com.hellohuandian.apps.ControllerLibrary.strategyController.controlPanel;

import com.hellohuandian.apps.ControllerLibrary.base.strategyController.TaskController;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description: 转485控制器
 */
public class _485Controller extends TaskController<_485Strategy>
{
    public _485Controller(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    @Override
    public void execute(_485Strategy taskStrategy)
    {
        //每组任务都需要先转485处理
        taskExecuteBefore();

        if (taskStrategy != null)
        {
            if (taskStrategy.delayTime > 0)
            {
                //激活485需要延迟的时间
                sleep(taskStrategy.delayTime);
            }

            strategyWrite(taskStrategy);

            if (taskStrategy.intervalTime > 0)
            {
                sleep(taskStrategy.intervalTime);
            }

            strategyRead(taskStrategy);
        }
    }
}
