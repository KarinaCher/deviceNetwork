package topology;

import device.NetworkingDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static device.DeviceType.GATEWAY;
import static device.DeviceType.SWITCH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkTest {

    private Network network;

    @BeforeEach
    public void setUp() {
        network = new Network();
    }

    @Test
    public void testInitRoot() throws RuntimeException {
        network.addRoot(GATEWAY, "ROOT_MAC");

        assertEquals(GATEWAY, network.get("ROOT_MAC").getType());
    }

    @Test
    public void testGetChild() throws RuntimeException {
        network.addRoot(GATEWAY, "ROOT_MAC");
        network.get("ROOT_MAC").addLinked(SWITCH, "CHILD_1");

        NetworkingDevice actual = network.get("CHILD_1");

        assertEquals(SWITCH, actual.getType());
    }

}