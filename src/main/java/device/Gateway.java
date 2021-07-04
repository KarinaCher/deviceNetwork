package device;

import static device.DeviceType.GATEWAY;

public class Gateway extends NetworkingDevice
{

    public Gateway(String macAddress)
    {
        super(macAddress);
    }


    @Override
    public DeviceType getType()
    {
        return GATEWAY;
    }

    
}
