package com.epam.gigaSpaceIntegration.constant;

public enum XAPSpaceConstant {

    DEFAULT_SPACE("person", "EPINHYDW0423"),
    //to configure
    CUSTOME_SPACE("", "");

    private String spaceName;
    private String hostName;
    XAPSpaceConstant(String spaceName, String hostName) {
        this.spaceName = spaceName;
        this.hostName = hostName;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
