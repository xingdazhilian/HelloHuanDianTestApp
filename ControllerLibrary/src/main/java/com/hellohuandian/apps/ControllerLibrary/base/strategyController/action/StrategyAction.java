package com.hellohuandian.apps.ControllerLibrary.base.strategyController.action;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description:
 */
public interface StrategyAction<T extends TaskStrategy>
{
    void onStrategyWrite(T taskStrategy);

    void onStrategyRead(T taskStrategy, byte[] data);
}
