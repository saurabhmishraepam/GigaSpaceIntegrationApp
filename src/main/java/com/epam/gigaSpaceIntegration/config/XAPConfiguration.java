package com.epam.gigaSpaceIntegration.config;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XAPConfiguration {
    private static Logger logger = LoggerFactory.getLogger(XAPConfiguration.class);
    private String spaceName = "person";
    private String mode = "remote";
    private String hostName = "EPINHYDW0423";

    public GigaSpace gigaSpaceFactory() {
        mode = "remote";
        if (mode.equals("embedded")) {
            GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(spaceName)).gigaSpace();
            logger.info("Created embedded data-grid: " + spaceName);
            return space;

        } else if (mode.equals("remote")) {
            GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName).lookupLocators(hostName)).gigaSpace();
            //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
            logger.info("Connected to remote data-grid: " + spaceName);
            return space;

        } else {
            throw new IllegalArgumentException("unexpected parsing of properties: ");
        }
    }
}
