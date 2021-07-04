package device;

import exception.DeviceTypeException;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkingDevice
{
    protected final String macAddress;
    private List<NetworkingDevice> items = new ArrayList();

    public NetworkingDevice(String macAddress)
    {
        this.macAddress = macAddress;
    }
    
    public abstract DeviceType getType();

    public String getMacAddress()
    {
        return macAddress;
    }

    public List<NetworkingDevice> getItems()
    {
        return items;
    }
    
    public void addLinked(DeviceType type, String macAddress) throws DeviceTypeException {
        NetworkingDevice device = DeviceBuilder.build(type, macAddress);
        items.add(device);
    }
    
    public NetworkingDevice find(String macAddress)
    {
        return items.stream()
                .filter(device -> macAddress.equals(device.getMacAddress()))
                .findFirst()
                .orElse(null);
    }
}
