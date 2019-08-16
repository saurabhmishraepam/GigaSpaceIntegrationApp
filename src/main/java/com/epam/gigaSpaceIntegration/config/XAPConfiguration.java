package com.epam.gigaSpaceIntegration.config;

import com.epam.gigaSpaceIntegration.constant.GSGridModeConstant;
import com.epam.gigaSpaceIntegration.constant.XAPSpaceConstant;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XAPConfiguration {
    private static Logger logger = LoggerFactory.getLogger(XAPConfiguration.class);
    private XAPSpaceConstant defaultConfig = XAPSpaceConstant.DEFAULT_SPACE;

    public GigaSpace gigaSpaceFactory(GSGridModeConstant gridMode) {

        switch (gridMode) {
            case EMBEDDED: {
                return gigaSpaceFactoryEmbedded();
            }
            case REMOTE: {
                return gigaSpaceFactoryRemote();
            }

        }
        throw new IllegalArgumentException("Bad configuration");

    }

    private GigaSpace gigaSpaceFactoryEmbedded() {
        GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(defaultConfig.getSpaceName())).gigaSpace();
        logger.info("Created embedded data-grid: " + defaultConfig.getSpaceName());
        return space;
    }

    private GigaSpace gigaSpaceFactoryRemote() {
        GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(defaultConfig.getSpaceName()).lookupLocators(defaultConfig.getHostName())).gigaSpace();
        //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
        logger.info("Connected to remote data-grid: " + defaultConfig.getSpaceName());
        return space;

    }


}
