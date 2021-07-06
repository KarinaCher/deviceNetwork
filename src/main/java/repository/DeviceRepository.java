package repository;

import entity.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {

    Device findByLinkedTo(String macAddress);

    Device findByMacAddress(String macAddress);

    Device save(Device device);

    Iterable<Device> findAll();
}
