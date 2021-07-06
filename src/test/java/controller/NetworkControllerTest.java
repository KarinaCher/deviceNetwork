package controller;

import exception.DuplicateDeviceException;
import exception.InitializationException;
import exception.ParentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import static entity.DeviceType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootApplication
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "repository")
public class NetworkControllerTest {

    @Autowired
    private NetworkController networkController;

    @BeforeEach
    public void setUp() {
        networkController.getRepository().deleteAll();
    }

    @Test
    public void testRegisterRoot() throws RuntimeException {
        String macAddress = "MAC1";
        networkController.register(SWITCH, macAddress, null);

        assertEquals(macAddress, networkController.get(macAddress).getMacAddress());
    }

    @Test
    public void testRegisterMoreDevices() throws RuntimeException {
        String parentMacAddress = "PARENT_MAC";
        String gatewayMacAddress = "GATEWAY_MAC";
        String swithMacAddress = "SWITCH_MAC";
        String accessPointMacAddress = "ACCESS_POINT_MAC";
        networkController.register(SWITCH, parentMacAddress, null);
        networkController.register(GATEWAY, gatewayMacAddress, parentMacAddress);
        networkController.register(SWITCH, swithMacAddress, parentMacAddress);
        networkController.register(ACCESS_POINT, accessPointMacAddress, parentMacAddress);

        assertEquals(parentMacAddress, networkController.get(parentMacAddress).getMacAddress());
    }

    @Test
    public void testPrentNotFound() throws RuntimeException {
        networkController.register(SWITCH, "PARENT_MAC", null);

        assertThrows(ParentNotFoundException.class,
                () -> networkController.register(SWITCH, "CHILD_MAC", "UNREGISTERED_MAC"));
    }

    @Test
    public void testInitializationExc() {
        assertThrows(InitializationException.class,
                () -> networkController.register(SWITCH, "MAC", "MAC"));
    }

    @Test
    public void testDuplicateDeviceExc() {
        String macAddress = "MAC";
        networkController.register(SWITCH, macAddress, null);
        assertThrows(DuplicateDeviceException.class,
                () -> networkController.register(SWITCH, macAddress, null));
    }

    @Test
    public void testTopology_empty() {
        assertEquals(0, networkController.getTopology().getNodes().size());
    }

    @Test
    public void testTopology() throws RuntimeException {
        networkController.register(GATEWAY, "MAC", null);
        networkController.register(GATEWAY, "MAC11", "MAC");
        networkController.register(GATEWAY, "MAC12", "MAC");
        networkController.register(GATEWAY, "MAC111", "MAC11");

        assertEquals("MAC", networkController.getTopology("MAC").getMacAddress());
        assertEquals("MAC", networkController.getTopology("MAC").getMacAddress());
        assertEquals(2, networkController.getTopology("MAC").getNodes().size());
        assertEquals("MAC11", networkController.getTopology("MAC11").getMacAddress());
        assertEquals(1, networkController.getTopology("MAC11").getNodes().size());

    }

    @Test
    public void testGetAll_empty() {
        assertEquals(0, networkController.getList().size());
    }

    @Test
    public void testGetAll_rootOnly() throws RuntimeException {
        networkController.register(GATEWAY, "MAC", null);

        assertEquals(1, networkController.getList().size());
    }

    @Test
    public void testGet() throws RuntimeException {
        networkController.register(GATEWAY, "MAC", null);
        networkController.register(SWITCH, "MAC11", "MAC");
        networkController.register(ACCESS_POINT, "MAC12", "MAC");
        networkController.register(GATEWAY, "MAC2", "MAC11");

        assertEquals(4, networkController.getList().size());
        assertEquals(SWITCH, networkController.get("MAC11").getType());
        assertEquals(ACCESS_POINT, networkController.get("MAC12").getType());
        assertEquals(GATEWAY, networkController.get("MAC2").getType());
    }
}
