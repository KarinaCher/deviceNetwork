package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "network_devices")
public class Device {

    @Id
    @Column(name = "mac_address")
    private String macAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DeviceType type;

    @Column(name = "liked_to")
    @JsonIgnore
    private String linkedTo;

    public Device() {
    }

    public Device(String macAddress, DeviceType type, String linkedTo) {
        this.macAddress = macAddress;
        this.type = type;
        this.linkedTo = linkedTo;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public DeviceType getType() {
        return type;
    }

    public String getLinkedTo() {
        return linkedTo;
    }
}
