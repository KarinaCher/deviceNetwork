package entry;

import java.util.ArrayList;
import java.util.List;

public class TopologyEntry {

    private String macAddress;
    private List<TopologyEntry> nodes;

    public TopologyEntry(String macAddress) {
        this.macAddress = macAddress;
        this.nodes = new ArrayList<>();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public List<TopologyEntry> getNodes() {
        return nodes;
    }
}
