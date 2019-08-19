package com.epam.gigaspaceintegration.service.polling;

import com.epam.gigaspaceintegration.bean.polling.Data;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.EmbeddedSpaceConfigurer;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.SimplePollingContainerConfigurer;
import org.openspaces.events.polling.SimplePollingEventListenerContainer;

import java.util.Calendar;

public class PollingContainerLifeCycleMain {

    static SimplePollingEventListenerContainer pollingEventListenerContainer;
    static GigaSpace gigaSpace;

    public static void main(String[] args) throws Exception {

        gigaSpace = new GigaSpaceConfigurer(new EmbeddedSpaceConfigurer("mySpace")).gigaSpace();

        // Write data to the space
        gigaSpace.write(new Data());
        say("wrote object to space");
        say("pollingContainer about to be created");

        // create a polling listener
        pollingEventListenerContainer = new SimplePollingContainerConfigurer(gigaSpace).template(new Data())
                .autoStart(false).eventListenerAnnotation(new Object() {
                    @SpaceDataEvent
                    public void eventHappened() {
                        say("event consumed");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).pollingContainer();

        say("pollingContainer created");
        Thread.sleep(1000);
        say("pollingContainer about to be started");
        pollingEventListenerContainer.start();
        say("pollingContainer started");
        Thread.sleep(1000);

        say("pollingContainer about to be stopped");
        pollingEventListenerContainer.stop();
        say("pollingContainer stoped");
        Thread.sleep(1000);

        say("pollingContainer about to be restarted");
        pollingEventListenerContainer.start();
        say("pollingContainer started");
        Thread.sleep(1000);

        say("pollingContainer about to be destroyed");
        pollingEventListenerContainer.destroy();
        say("pollingContainer destroyed");
        System.exit(0);
    }

    static public void say(String mes) {
        Calendar d = Calendar.getInstance();

        int ms = Calendar.getInstance().get(Calendar.MILLISECOND);
        String t = d.getTime() + ":" + ms;

        if (pollingEventListenerContainer == null)
            System.out.println(t + " - " + " isActive:" + "false" + " isRunning:" + "false" + " " + mes);
        else
            System.out.println(t + " - " + " isActive:" + pollingEventListenerContainer.isActive() + " isRunning:"
                    + pollingEventListenerContainer.isRunning() + " " + mes);
    }
}
