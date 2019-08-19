package com.epam.gigaspaceintegration.config;

import com.epam.gigaspaceintegration.constant.GSGridModeConstant;
import com.epam.gigaspaceintegration.constant.XAPSpaceConstant;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class to get the instance of Gigaspace
 * can be refactored to multiple implementation of the type of objects
 */
public class XAPConfiguration {
    private static Logger logger = LoggerFactory.getLogger(XAPConfiguration.class);

    public GigaSpace gigaSpaceFactory(GSGridModeConstant gridMode, XAPSpaceConstant XAPSpaceDetails) {

            switch (gridMode) {
                case EMBEDDED: {
                    return gigaSpaceFactoryEmbedded(XAPSpaceDetails);
                }
                case REMOTE: {
                    return gigaSpaceFactoryRemote(XAPSpaceDetails);
                }
            }
        throw new IllegalArgumentException("Bad gigaspace configuration details");

    }

    private GigaSpace gigaSpaceFactoryEmbedded(XAPSpaceConstant defaultConfig) {
        GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(defaultConfig.getSpaceName()))
                .gigaSpace();
        logger.info("Created embedded data-grid: " + defaultConfig.getSpaceName());
        return space;
    }

    private GigaSpace gigaSpaceFactoryRemote(XAPSpaceConstant defaultConfig) {
        GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(defaultConfig.getSpaceName())
                .lookupLocators(defaultConfig.getHostName())) //automatically it connects to localhost
                .gigaSpace();
        //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
        logger.info("Connected to remote data-grid: " + defaultConfig.getSpaceName());
        return space;

    }


}
