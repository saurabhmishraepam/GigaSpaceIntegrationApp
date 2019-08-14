package com.epam.gigaSpaceIntegration.service;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.config.XAPConfiguration;
import com.j_spaces.core.LeaseContext;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;

import java.util.Optional;


public class GSPersonService {
    private XAPConfiguration xapConfiguration = new XAPConfiguration();
    private GigaSpace gigaSpace;

    public GSPersonService() {
        gigaSpace = xapConfiguration.gigaSpaceFactory();
    }
    public void write(final Person person) {
        LeaseContext<Person> context = gigaSpace.write(person);

        if (context.getVersion() == 1) {
            System.out.println("write - " + person);
        } else {
            System.out.println("update - " + person);
        }
    }
    public void deletePerson(final Person person){
        gigaSpace.clear(person);

    }

    public Person[] readAll(final Person person) {
        Person[] results = gigaSpace.readMultiple(person);
        return results;
    }
    public Optional<Person> readByQuery(String key, String value){

       return  readByQuery(new SQLQuery<>(Person.class, key+"=?", value));

    }


    public   Optional<Person> readByQuery(SQLQuery<Person> query) {
        Person result = gigaSpace.read(query);
        return Optional.of(result);
    }
    public   Person [] readMutlipltByQuery(SQLQuery<Person> query) {
        Person [] result = gigaSpace.readMultiple(query);
        return result;
    }


    public Optional<Person> readById(int id) {

        return Optional.of(gigaSpace.readById(Person.class, id));
    }

}
