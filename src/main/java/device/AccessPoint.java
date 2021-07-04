package device;

import static device.DeviceType.ACCESS_POINT;

public class AccessPoint extends NetworkingDevice
{

    public AccessPoint(String macAddress)
    {
        super(macAddress);
    }

    @Override
    public DeviceType getType()
    {
        return ACCESS_POINT;
    }
}
