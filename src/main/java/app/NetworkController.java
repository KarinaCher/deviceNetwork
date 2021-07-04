package app;

import device.DeviceType;
import device.NetworkingDevice;
import entry.DeviceEntry;
import entry.TopologyEntry;
import exception.DuplicateDeviceException;
import exception.InitializationException;
import exception.ParentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import topology.Network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static converter.NetworkToTopologyConverter.convert;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RestController
public class NetworkController {

    private Network network = new Network();

    @GetMapping(value = {"/register/type/{deviceType}/mac/{macAddress}/uplink/{uplinkMacAddress}",
            "/register/type/{deviceType}/mac/{macAddress}"})
    public ResponseEntity register(@PathVariable(name = "deviceType") DeviceType deviceType,
                                                  @PathVariable(name = "macAddress") String macAddress,
                                                  @PathVariable(name = "uplinkMacAddress", required = false) String uplinkMacAddress) throws RuntimeException {
        if (uplinkMacAddress != null && macAddress.equals(uplinkMacAddress)) {
            throw new InitializationException("Device can't be uplinked to itself.");
        }

        if (network.get(macAddress) != null) {
            throw new DuplicateDeviceException("Device with this MAC address already registered");
        }

        if (uplinkMacAddress == null) {
            network.addRoot(deviceType, macAddress);
            return ResponseEntity.ok(HttpStatus.OK);
        }

        NetworkingDevice parent = network.get(uplinkMacAddress);
        if (parent == null) {
            throw new ParentNotFoundException(String.format("No devices found to uplink %s", uplinkMacAddress));
        } else {
            parent.addLinked(deviceType, macAddress);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getList")
    public List<DeviceEntry> getList() {
        NetworkingDevice root = network.getRoot();
        if (root.getItems().isEmpty()) {
            return Collections.emptyList();
        }

        List<NetworkingDevice> list = new ArrayList<>();
        add(list, root.getItems());

        List<DeviceEntry> result = list.stream()
                .sorted(comparing(NetworkingDevice::getType))
                .map(device -> new DeviceEntry(device))
                .collect(toList());
        return result;
    }

    private void add(List<NetworkingDevice> list, List<NetworkingDevice> items) {
        list.addAll(items);
        for (NetworkingDevice device : items) {
            add(list, device.getItems());
        }
    }

    @GetMapping("/get/{macAddress}")
    public DeviceEntry get(@PathVariable(name = "macAddress") String macAddress) {
        if (isEmpty(macAddress)) {
            return null;
        }

        return new DeviceEntry(network.get(macAddress));
    }

    @GetMapping("/topology")
    public TopologyEntry getTopology() {
        return convert(network.getRoot());
    }

    @GetMapping("/topology/{macAddress}")
    public TopologyEntry getTopology(@PathVariable(name = "macAddress") String macAddress) {
        return convert(network.get(macAddress));
    }
}
