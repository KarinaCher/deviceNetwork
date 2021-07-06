package converter;

import entity.Device;
import entry.TopologyEntry;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

public class NetworkToTopologyConverter {

    public static TopologyEntry convert(List<Device> all) {
        TopologyEntry topology = new TopologyEntry("ROOT");
        List<Device> deviceList = all.stream()
                .filter(device -> isEmpty(device.getLinkedTo()))
                .collect(toList());

        for (Device device : deviceList) {

            String macAddress = device.getMacAddress();
            TopologyEntry te = new TopologyEntry(device.getMacAddress());
            topology.getNodes().add(te);
            buildEntry(te, all, macAddress);
        }

        return topology;
    }

    private static void buildEntry(TopologyEntry parent, List<Device> all, String macAddress) {
        List<Device> linked = getLinked(macAddress, all);

        if (!linked.isEmpty()) {
            for (Device device : linked) {
                TopologyEntry te = new TopologyEntry(device.getMacAddress());
                parent.getNodes().add(te);

                buildEntry(te, all, device.getMacAddress());
            }
        }
    }

    private static List<Device> getLinked(String macAddress, List<Device> all) {
        return all.stream()
                .filter(device -> macAddress.equals(device.getLinkedTo()))
                .collect(toList());
    }

    public static TopologyEntry convert(List<Device> all, String macAddress) {
        TopologyEntry topologyEntry = convert(all);
        return find(topologyEntry, macAddress);
    }

    private static TopologyEntry find(TopologyEntry topologyEntry, String macAddress) {
        Optional<TopologyEntry> entryOptional = topologyEntry.getNodes().stream()
                .filter(te -> macAddress.equals(te.getMacAddress()))
                .findFirst();
        if (entryOptional.isPresent()) {
            return entryOptional.get();
        } else {
            for (TopologyEntry te : topologyEntry.getNodes()) {
                return find(te, macAddress);
            }
        }
        return null;
    }
}
