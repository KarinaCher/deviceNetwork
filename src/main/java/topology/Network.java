package topology;

import device.DeviceBuilder;
import device.DeviceType;
import device.NetworkingDevice;
import exception.DeviceTypeException;

import java.util.List;

public class Network {

    private NetworkingDevice root = new NetworkingDevice("ROOT") {
        @Override
        public DeviceType getType() {
            return DeviceType.ROOT;
        }
    };

    public void addRoot(DeviceType deviceType, String macAddress) throws DeviceTypeException {
        this.root.getItems().add(DeviceBuilder.build(deviceType, macAddress));
    }

    public NetworkingDevice getRoot() {
        return root;
    }

    public NetworkingDevice get(String macAddress) {
        NetworkingDevice device = root.find(macAddress);
        if (device == null) {
            return get(macAddress, root.getItems());
        } else {
            return device;
        }
    }

    private NetworkingDevice get(String macAddress, List<NetworkingDevice> items) {
        for (NetworkingDevice node : items) {
            NetworkingDevice device = node.find(macAddress);
            if (device != null) {
                return device;
            } else {
                return get(macAddress, node.getItems());
            }
        }

        return null;
    }

}
