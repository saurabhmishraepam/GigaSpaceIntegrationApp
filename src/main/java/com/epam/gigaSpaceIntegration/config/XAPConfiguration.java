package com.epam.gigaSpaceIntegration.config;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.core.space.SpaceProxyConfigurer;

public class XAPConfiguration {
    private String spaceName="person";


    private String mode="remote";


    private String hostName="EPINHYDW0423";

    public GigaSpace gigaSpaceFactory(){
        mode="remote";
        // string move to enums to validate the name
        if (mode.equals("embedded")) {
            GigaSpace space = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer(spaceName)).gigaSpace();
            System.out.println("Created embedded data-grid: " + spaceName);
            return space;

        } else if (mode.equals("remote")) {
            // GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName)).gigaSpace();
            GigaSpace space = new GigaSpaceConfigurer(new SpaceProxyConfigurer(spaceName).lookupLocators(hostName)).gigaSpace();
            //GigaSpace space = new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/mySpace?locators=Host1,Host2")).gigaSpace();
            System.out.println("Connected to remote data-grid: " + spaceName);
            return space;

        } else {
            throw new IllegalArgumentException("unexpected parsing of properties: " );
        }


    }


}
