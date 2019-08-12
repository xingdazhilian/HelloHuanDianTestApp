package com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
abstract class CmdStrategy extends TimeStrategy
{
    private static final long DEAULT_WAIT_TIME = 500;

    public final int strategyId;
    public final byte[] cmdBytes;

    public CmdStrategy(int strategyId, byte[] cmdBytes)
    {
        this(strategyId, cmdBytes, 0);
    }

    public CmdStrategy(int strategyId, byte[] cmdBytes, long outTime)
    {
        this(strategyId, cmdBytes, outTime, DEAULT_WAIT_TIME);
    }

    public CmdStrategy(int strategyId, byte[] cmdBytes, long outTime, long waitTime)
    {
        super(outTime, waitTime);
        this.strategyId = strategyId;
        this.cmdBytes = cmdBytes.clone();
    }
}
