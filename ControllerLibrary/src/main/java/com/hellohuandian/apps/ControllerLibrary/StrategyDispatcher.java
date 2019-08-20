package com.hellohuandian.apps.ControllerLibrary;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;

import java.util.ArrayList;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description: 只做策略分发和执行操作
 */
public final class StrategyDispatcher<T extends TaskStrategy> extends ArrayList<T>
{
    private int currentStrategyIndex;
    private long strategyOutTime;

    public final void next()
    {
        final int size = size();

        if (currentStrategyIndex < size)
        {
            TaskStrategy taskStrategy = get(currentStrategyIndex);
            if (taskStrategy != null)
            {
                long currentTime = System.currentTimeMillis() - strategyOutTime;
                if (currentTime > taskStrategy.delayTime)
                {
                    // TODO: 2019-08-16 超时之后需要获取groupindex作为当前策略index执行
                    currentStrategyIndex = taskStrategy.getGroupindex();
                    taskStrategy = get(currentStrategyIndex);
                }

                if (taskStrategy != null)
                {
                    taskStrategy.execute();
                }

                currentStrategyIndex++;

                if (currentStrategyIndex == size)
                {
                    currentStrategyIndex = 0;
                }
            }
        }

        strategyOutTime = System.currentTimeMillis();
    }

    public final void next(T taskStrategy)
    {
        if (taskStrategy != null)
        {
            taskStrategy.execute();
        }
    }
}
