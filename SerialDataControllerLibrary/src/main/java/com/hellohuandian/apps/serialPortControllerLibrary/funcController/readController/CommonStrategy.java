package com.hellohuandian.apps.serialPortControllerLibrary.funcController.readController;

import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-09
 * Description:
 */
public class CommonStrategy extends TaskStrategy
{
    public CommonStrategy(int strategyId, byte[] cmdBytes)
    {
        super(strategyId, cmdBytes);
    }
}
