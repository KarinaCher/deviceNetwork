package device;

import exception.DeviceTypeException;
import org.junit.jupiter.api.Test;

import static device.DeviceType.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceBuilderTest {

    @Test
    void testBuildGateway() throws DeviceTypeException {
        NetworkingDevice device = DeviceBuilder.build(GATEWAY, "MAC");

        assertTrue(device instanceof Gateway);
    }

    @Test
    void testBuildSwitch() throws DeviceTypeException {
        NetworkingDevice device = DeviceBuilder.build(SWITCH, "MAC");

        assertTrue(device instanceof Switch);
    }

    @Test
    void testBuildAccessPoint() throws DeviceTypeException {
        NetworkingDevice device = DeviceBuilder.build(ACCESS_POINT, "MAC");

        assertTrue(device instanceof AccessPoint);
    }

    @Test
    void testUnsupportedDevice() {
        assertThrows(DeviceTypeException.class,
                () -> DeviceBuilder.build(ROOT, "MAC"));
    }
}