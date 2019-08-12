package com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-09
 * Description:
 */
public abstract class TaskStrategy extends CmdStrategy
{
    public TaskStrategy(int strategyId, byte[] cmdBytes)
    {
        super(strategyId, cmdBytes);
    }
}
