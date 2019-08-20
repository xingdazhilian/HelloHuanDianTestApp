package com.hellohuandian.apps.ControllerLibrary.strategyController.cmd;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
public class CmdStrategy extends TaskStrategy
{
    public CmdStrategy(int id, byte[] taskBytes)
    {
        super(id, taskBytes);
    }
}
