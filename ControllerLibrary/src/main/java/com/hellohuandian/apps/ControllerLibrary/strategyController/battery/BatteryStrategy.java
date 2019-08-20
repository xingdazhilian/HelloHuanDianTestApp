package com.hellohuandian.apps.ControllerLibrary.strategyController.battery;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description:
 */
public class BatteryStrategy extends TaskStrategy
{
    public BatteryStrategy(int id, byte[] taskBytes)
    {
        super(id, taskBytes);
    }
}
