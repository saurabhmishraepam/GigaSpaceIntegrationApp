package com.epam.gigaSpaceIntegration;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.service.CacheQueryService;
import com.epam.gigaSpaceIntegration.service.CacheService;
import com.epam.gigaSpaceIntegration.service.GSCacheQueryServiceImpl;
import com.epam.gigaSpaceIntegration.service.GSPersonService;
import com.j_spaces.core.client.SQLQuery;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RunWith(JUnit4.class)
public class ApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(GSPersonService.class);
    private static List<Person> personsList = new ArrayList(1000);
    private Random rnd = new Random(1000);

    private static CacheQueryService<Person> personCacheService=new GSCacheQueryServiceImpl<Person>();
    @BeforeClass
    public static void init() {
        Random rnd = new Random(1000);

        String[] firstNames = {"Saurabh", "Rahul", "Amit", "Jhony", "Ravi", "Mohit", "Piyush", "Ajit", "Shweta", "Sunil"};
        String[] lastNames = {"Mishra", "Jain", "Sharma", "KKKK", "MMMM", "PPPP", "ZZZZ", "LLLL", "RRRRR", "NNNNN"};

        for (int i = 0; i < 1000; i++) {
            int indF = rnd.nextInt(9);
            int indL = rnd.nextInt(9);
            String firstName = firstNames[indF];
            String lastNmae = lastNames[indL];
            int age = rnd.nextInt(20) + 20;
            Person person = new Person(i, firstName, lastNmae, "", age);
            personsList.add(person);
            personCacheService.write(person);
        }
    }

   // @AfterClass
    public static void cleanup() {
        //clean after all is processed
        personsList.forEach(
                v -> {
                    personCacheService.readByQuery(new Person(),"firstName", v.getFirstName()).ifPresent(q ->
                            {
                                personCacheService.deletePerson(q);
                            }
                    );
                }
        );

    }

    @Test
    public void shouldConnectAndValidateAll() {
        int age = 30;
        personCacheService.deletePerson(new Person(1, "saurabh", "mishra", "", age));
        personCacheService.deletePerson(new Person(2, "saurabh", "mishra", "", age));
        personCacheService.write(new Person(1, "saurabh", "mishra", "", age));
        personCacheService.write(new Person(2, "saurabh", "mishra", "", age));
        Optional<Person> result2 = personCacheService.readByQuery(new Person(),"firstName", "saurabh");
        logger.info("Result: " + result2);
        Person[] results = personCacheService.readAll(new Person());
        logger.info("Result: " + java.util.Arrays.toString(results));
    }

    @Test
    public void getAllPersonWithName() {
        String nameToSearch = "Saurabh";
        Person[] arr = personCacheService.readAll(new Person(nameToSearch));
        logger.info("search all by name count :: " + arr.length);
        long count = personsList.stream().filter(p -> p.getFirstName().equals(nameToSearch)).count();
        Assert.assertTrue(count == arr.length);
    }

    @Test
    public void getAllPersonWithLastName() {
        String nameToSearch = "Mishra";
        Person[] arr = personCacheService.readAll(new Person("", nameToSearch));
        long count = personsList.stream().filter(p -> p.getLastName().equals(nameToSearch)).count();
        logger.info("search all by last name count :: " + arr.length);
        Assert.assertTrue(count == arr.length);
    }

    @Test
    public void getAllPersonWithAgeGtThen30() {
        Person[] arr = personCacheService.readMultipleByQuery(new Person(), "age >30");
        long count = personsList.stream().filter(p -> p.getAge() > 30).count();
        logger.info("search all age >30 count :: " + arr.length);
        Assert.assertTrue(count == arr.length);

    }

    @Test
    public void pollingContainerTest() {

    }

}
