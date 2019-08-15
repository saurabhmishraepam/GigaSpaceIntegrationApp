package com.epam.gigaSpaceIntegration.config;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XAPConfiguration {
    private static Logger logger = LoggerFactory.getLogger(XAPConfiguration.class);
    private static final String SPACE_NAME = "person";
    private static final String MODE = "remote";
    private static final String HOST_NAME = "saurabh-home";

    public GigaSpace gigaSpaceFactory() {
        if (MODE.equals("embedded")) {
            GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(SPACE_NAME)).gigaSpace();
            logger.info("Created embedded data-grid: " + SPACE_NAME);
            return space;

        } else if (MODE.equals("remote")) {
            GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(SPACE_NAME).lookupLocators(HOST_NAME)).gigaSpace();
            //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
            logger.info("Connected to remote data-grid: " + SPACE_NAME);
            return space;

        } else {
            throw new IllegalArgumentException("unexpected parsing of properties: ");
        }
    }
}
