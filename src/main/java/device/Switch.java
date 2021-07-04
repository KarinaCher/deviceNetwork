package device;

import static device.DeviceType.SWITCH;

public class Switch extends NetworkingDevice
{

    public Switch(String macAddress)
    {
        super(macAddress);
    }



    @Override
    public DeviceType getType()
    {
        return SWITCH;
    }


}
