package entity;

public enum DeviceType {
    GATEWAY(1),
    SWITCH(2),
    ACCESS_POINT(3);

    private int priority;

    private DeviceType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }


}
