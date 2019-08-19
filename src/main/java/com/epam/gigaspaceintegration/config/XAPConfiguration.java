package com.epam.gigaspaceintegration.config;

import com.epam.gigaspaceintegration.constant.GSGridMode;
import com.epam.gigaspaceintegration.constant.XAPSpaceInstance;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Factory class to get the instance of Gigaspace
 * can be refactored to multiple implementation of the type of objects
 */
public class XAPConfiguration {
    private static Logger logger = LoggerFactory.getLogger(XAPConfiguration.class);

    public GigaSpace gigaSpaceFactory(GSGridMode gridMode, XAPSpaceInstance XAPSpaceInstance) {
            if(Objects.nonNull(gridMode) && Objects.nonNull(XAPSpaceInstance)) {

                switch (gridMode) {
                    case EMBEDDED: {
                        return gigaSpaceFactoryEmbedded(XAPSpaceInstance);
                    }
                    case REMOTE: {
                        return gigaSpaceFactoryRemote(XAPSpaceInstance);
                    }
                }
            }
        throw new IllegalArgumentException("Bad gigaspace configuration details");

    }

    private GigaSpace gigaSpaceFactoryEmbedded(XAPSpaceInstance defaultConfig) {
        GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(defaultConfig.getSpaceName()))
                .gigaSpace();
        logger.info("Created embedded data-grid: " + defaultConfig.getSpaceName());
        return space;
    }

    private GigaSpace gigaSpaceFactoryRemote(XAPSpaceInstance defaultConfig) {
        GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(defaultConfig.getSpaceName())
                .versioned(true)
                .lookupLocators(defaultConfig.getHostName())) //automatically it connects to localhost
                .gigaSpace();
        // it can be extended to use jinni
        //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
        logger.info("Connected to remote data-grid: " + defaultConfig.getSpaceName());
        return space;

    }


}
