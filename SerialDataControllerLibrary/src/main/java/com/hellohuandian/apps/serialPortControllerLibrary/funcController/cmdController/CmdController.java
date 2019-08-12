package com.hellohuandian.apps.serialPortControllerLibrary.funcController.cmdController;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.BaseController;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-08
 * Description: 命令控制器主要用来主动给控制板发送命令而用
 */
public class CmdController extends BaseController
{
    public CmdController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }
}
