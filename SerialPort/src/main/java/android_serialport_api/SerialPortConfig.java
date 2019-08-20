package android_serialport_api;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public class SerialPortConfig
{
    private String serialPortPath = "/dev/ttyS4";
    private int serialPortRate = 9600;
    private long serialPortReadInterval = 100;

    public SerialPortConfig()
    {
    }

    public SerialPortConfig(String serialPortPath, int serialPortRate, long serialPortReadInterval)
    {
        this.serialPortPath = serialPortPath;
        this.serialPortRate = serialPortRate;
        this.serialPortReadInterval = serialPortReadInterval;
    }

    public String getSerialPortPath()
    {
        return serialPortPath;
    }

    public int getSerialPortRate()
    {
        return serialPortRate;
    }

    public long getSerialPortReadInterval()
    {
        return serialPortReadInterval;
    }
}
