package controller;

import entity.Device;
import entity.DeviceType;
import entry.TopologyEntry;
import exception.DuplicateDeviceException;
import exception.InitializationException;
import exception.ParentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import repository.DeviceRepository;

import java.util.List;
import java.util.stream.StreamSupport;

import static converter.NetworkToTopologyConverter.convert;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@RestController
public class NetworkController {

    @Autowired
    private DeviceRepository repository;

    DeviceRepository getRepository() {
        return repository;
    }

    @GetMapping(value = {
            "/register/type/{deviceType}/mac/{macAddress}/uplink/{uplinkMacAddress}",
            "/register/type/{deviceType}/mac/{macAddress}"})
    public ResponseEntity register(@PathVariable(name = "deviceType") DeviceType deviceType,
                                   @PathVariable(name = "macAddress") String macAddress,
                                   @PathVariable(name = "uplinkMacAddress", required = false) String uplinkMacAddress) throws RuntimeException {
        if (uplinkMacAddress != null && macAddress.equals(uplinkMacAddress)) {
            throw new InitializationException("Device can't be uplinked to itself.");
        }

        if (getRepository().findByMacAddress(macAddress) != null) {
            throw new DuplicateDeviceException("Device with this MAC address already registered");
        }

        if (uplinkMacAddress == null) {
            getRepository().save(new Device(macAddress, deviceType, null));
            return ResponseEntity.ok(HttpStatus.OK);
        }

        Device parent = getRepository().findByMacAddress(uplinkMacAddress);
        if (parent == null) {
            throw new ParentNotFoundException(String.format("No devices found to uplink %s", uplinkMacAddress));
        } else {
            getRepository().save(new Device(macAddress, deviceType, uplinkMacAddress));
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getList")
    public List<Device> getList() {
        List<Device> result = StreamSupport.stream(getRepository().findAll().spliterator(), false)
                .sorted(comparing(device -> device.getType().getPriority()))
                .collect(toList());
        return result;
    }

    @GetMapping("/get/{macAddress}")
    public Device get(@PathVariable(name = "macAddress") String macAddress) {
        if (isEmpty(macAddress)) {
            return null;
        }

        return getRepository().findByMacAddress(macAddress);
    }

    @GetMapping("/topology")
    public TopologyEntry getTopology() {
        return convert(getList());
    }

    @GetMapping("/topology/{macAddress}")
    public TopologyEntry getTopology(@PathVariable(name = "macAddress") String macAddress) {
        return convert(getList(), macAddress);
    }
}
