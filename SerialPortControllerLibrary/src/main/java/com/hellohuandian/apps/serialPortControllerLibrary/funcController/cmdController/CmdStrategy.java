package com.hellohuandian.apps.serialPortControllerLibrary.funcController.cmdController;

import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-13
 * Description:
 */
public class CmdStrategy extends TaskStrategy
{
    public CmdStrategy(int strategyId, byte[] cmdBytes)
    {
        super(strategyId, cmdBytes);
    }
}
