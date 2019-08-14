package com.epam.gigaSpaceIntegration;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.service.GSPersonService;
import com.j_spaces.core.client.SQLQuery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openspaces.events.polling.SimplePollingContainerConfigurer;
import org.openspaces.events.polling.SimplePollingEventListenerContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RunWith(JUnit4.class)
public class ApplicationTest {

    private static List<Person> personsList = new ArrayList(1000);
    private GSPersonService gsPersonService = new GSPersonService();
    private Random rnd = new Random(1000);


    @BeforeClass
    public static void init() {
        GSPersonService gsPersonService = new GSPersonService();
        Random rnd = new Random(1000);

        String[] firstNames = {"Saurabh", "Rahul", "Amit", "Jhony", "Ravi", "Mohit", "Piyush", "Ajit", "Shweta", "Sunil"};
        String[] lastNames = {"Mishra", "Jain", "Sharma", "KKKK", "MMMM", "PPPP", "ZZZZ", "LLLL", "RRRRR", "NNNNN"};

        for (int i = 0; i < 1000; i++) {

            int indF = rnd.nextInt(9);
            int indL = rnd.nextInt(9);
            String firstName = firstNames[indF];
            String lastNmae = lastNames[indL];
            int age = rnd.nextInt(20) + 20;
            Person person=new Person(i, firstName, lastNmae, "", age);
            personsList.add(person);
            gsPersonService.write(person);

        }


    }

    @AfterClass
    public static void cleanup() {
        //clean after all is processed
        GSPersonService gsPersonService = new GSPersonService();

        personsList.forEach(
                v -> {
                    gsPersonService.readByQuery("firstName", v.getFirstName()).ifPresent(q ->
                            {
                                gsPersonService.deletePerson(q);
                            }
                    );
                }
        );

    }

    @Test
    public void shouldConnectAndValidateAll() {
        int age = 30;
        gsPersonService.deletePerson(new Person(1, "saurabh", "mishra", "", age));
        gsPersonService.deletePerson(new Person(2, "saurabh", "mishra", "", age));


        gsPersonService.write(new Person(1, "saurabh", "mishra", "", age));
        gsPersonService.write(new Person(2, "saurabh", "mishra", "", age));

        // Optional<Person> result1 = gsPersonService.readById(1);
        // System.out.println("Result: " + result1);

        Optional<Person> result2 = gsPersonService.readByQuery("firstName", "saurabh");
        System.out.println("Result: " + result2);

        System.out.println("Read all entries of type Person from the grid:");
        Person[] results = gsPersonService.readAll(new Person());
        System.out.println("Result: " + java.util.Arrays.toString(results));



    }

    @Test
    public void getAllPersonWithName() {
        String nameToSearch = "Saurabh";
        Person[] arr = gsPersonService.readAll(new Person(nameToSearch));
        System.out.println("search all by name count :: " + arr.length);
        long count =personsList.stream().filter(p ->p.getFirstName().equals(nameToSearch)).count();
        Assert.assertTrue(count ==arr.length);
    }

    @Test
    public void getAllPersonWithLastName() {
        String nameToSearch = "Mishra";
        Person[] arr = gsPersonService.readAll(new Person("", nameToSearch));
        long count =personsList.stream().filter(p ->p.getLastName().equals(nameToSearch)).count();
        System.out.println("search all by last name count :: " + arr.length);
        Assert.assertTrue(count ==arr.length);
    }

    @Test
    public void getAllPersonWithAgeGtThen30() {
        Person[] arr = gsPersonService.readMutlipltByQuery(new SQLQuery<>(Person.class, "age >30"));
        long count =personsList.stream().filter(p ->p.getAge()>30).count();
        System.out.println("search all age >30 count :: " + arr.length);
        Assert.assertTrue(count ==arr.length);

    }
    @Test
    public void pollingContainerTest(){



    }

}
