package converter;

import device.NetworkingDevice;
import entry.TopologyEntry;

public class NetworkToTopologyConverter {

    public static TopologyEntry convert(NetworkingDevice device) {

        TopologyEntry topologyEntry = copy(new TopologyEntry(device.getMacAddress()), device);

        return topologyEntry;
    }

    private static TopologyEntry copy(TopologyEntry entry, NetworkingDevice device) {

        for (NetworkingDevice item : device.getItems()) {
            TopologyEntry topologyEntry = new TopologyEntry(item.getMacAddress());
            entry.getNodes().add(topologyEntry);
            copy(topologyEntry, item);
        }

        return entry;
    }
}
