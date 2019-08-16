package com.epam.gigaSpaceIntegration.service.polling;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.config.XAPConfiguration;
import org.openspaces.core.GigaSpace;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.SimplePollingContainerConfigurer;
import org.openspaces.events.polling.SimplePollingEventListenerContainer;

public class PollingContrllerService {

    private XAPConfiguration xapConfiguration = new XAPConfiguration();
    private GigaSpace gigaSpace;

    public PollingContrllerService() {
    }

    public void pollingControllerRegister(){
        SimplePollingEventListenerContainer pollingEventListenerContainer = new SimplePollingContainerConfigurer(gigaSpace)
                .template(new Person("Saurabh"))
                .eventListenerAnnotation(new Object() {
                    @SpaceDataEvent
                    public void eventHappened() {
                        System.out.println("update happened");
                        //eventCalled.set(true);
                    }
                }).pollingContainer();


    }



}
