package device;

public enum DeviceType
{
    GATEWAY(1),
    SWITCH(2),
    ACCESS_POINT(0),
    ROOT(-1),
    ;
    
    private int priority;

    private DeviceType(int priority)
    {
        this.priority = priority;
    }

    public int getPriority()
    {
        return priority;
    }
    
    
}
