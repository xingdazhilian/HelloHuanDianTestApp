package com.hellohuandian.apps.ControllerLibrary.strategyController.cmd;

import com.hellohuandian.apps.ControllerLibrary.base.strategyController.TaskController;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
public class CmdController extends TaskController<CmdStrategy>
{
    public CmdController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    @Override
    public void execute(CmdStrategy taskStrategy)
    {
        if (taskStrategy != null)
        {
            //推杆需要等待485转发超时，否则命令就传给了电池
            if (taskStrategy.delayTime > 0)
            {
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
