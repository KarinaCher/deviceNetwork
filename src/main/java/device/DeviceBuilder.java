package device;

import exception.DeviceTypeException;

public class DeviceBuilder {

    public static NetworkingDevice build(DeviceType type, String macAddress) throws DeviceTypeException {
        switch (type) {
            case GATEWAY:
                return new Gateway(macAddress);

            case SWITCH:
                return new Switch(macAddress);

            case ACCESS_POINT:
                return new AccessPoint(macAddress);

            case ROOT:
            default:
                throw new DeviceTypeException("Device type not supported");
        }
    }
}
