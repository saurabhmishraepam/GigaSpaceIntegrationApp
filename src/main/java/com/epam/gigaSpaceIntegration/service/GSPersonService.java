package com.epam.gigaSpaceIntegration.service;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.config.XAPConfiguration;
import com.epam.gigaSpaceIntegration.constant.GSGridModeConstant;
import com.j_spaces.core.LeaseContext;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Deprecated
public class GSPersonService {
    private static Logger logger = LoggerFactory.getLogger(GSPersonService.class);
    private XAPConfiguration xapConfiguration = new XAPConfiguration();
    private GigaSpace gigaSpace;

    public GSPersonService() {
        gigaSpace = xapConfiguration.gigaSpaceFactory(GSGridModeConstant.REMOTE);
    }

    public void write(final Person person) {
        LeaseContext<Person> context = gigaSpace.write(person);
        if (context.getVersion() == 1) {
            logger.info("write - " + person);
        } else {
            logger.info("update - " + person);
        }
    }

    public void deletePerson(final Person person) {
        gigaSpace.clear(person);

    }

    public Person[] readAll(final Person person) {
        Person[] results = gigaSpace.readMultiple(person);
        return results;
    }


    public Optional<Person> readById(int id) {

        return Optional.of(gigaSpace.readById(Person.class, id));
    }


    public Optional<Person> readByQuery(String key, String value) {

        return readByQuery(new SQLQuery<>(Person.class, key + "=?", value));

    }

    public Optional<Person> readByQuery(SQLQuery<Person> query) {
        Person result = gigaSpace.read(query);
        return Optional.of(result);
    }

    public Person[] readMultipleByQuery(SQLQuery<Person> query) {
        Person[] result = gigaSpace.readMultiple(query);
        return result;
    }


}
