package entry;

import device.DeviceType;
import device.NetworkingDevice;

public class DeviceEntry {

    private String macAddress;
    private DeviceType type;

    public DeviceEntry() {
    }

    public DeviceEntry(NetworkingDevice device) {
        if (device == null) {
            return;
        }
        this.macAddress = device.getMacAddress();
        this.type = device.getType();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }
}
