package com.hellohuandian.apps.serialPortControllerLibrary;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.configstrategylibrary.table.ControlAddressTable;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.EventStrategyController;
import com.hellohuandian.apps.serialPortControllerLibrary.funcController.commonController.CommonController;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 负责提供匹配控制板的Read控制器
 */
public final class SerialPortControllerProvider
{
    public static List<EventStrategyController> loadController(@NonNull SerialPortDevice serialPortDevice)
    {
        List<EventStrategyController> readControllerList = new ArrayList<>();
        // TODO: 2019-07-27 根据控制板地址表初始化读取控制器

        for (ControlAddressTable.Address address : ControlAddressTable.ADDRESSES)
        {
            readControllerList.add(new CommonController(serialPortDevice, address));
        }

        return readControllerList;
    }
}
