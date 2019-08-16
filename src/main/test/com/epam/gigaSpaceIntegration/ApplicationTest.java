package com.epam.gigaSpaceIntegration;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.bean.PersonV1;
import com.epam.gigaSpaceIntegration.constant.QueryConstants;
import com.epam.gigaSpaceIntegration.service.CacheQueryService;
import com.epam.gigaSpaceIntegration.service.GSCacheQueryServiceImpl;
import com.epam.gigaSpaceIntegration.service.GSPersonService;
import com.epam.gigaSpaceIntegration.util.QueryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openspaces.core.UnusableEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ApplicationTest {
    private static Logger logger = LoggerFactory.getLogger(GSPersonService.class);
    private static List<Person> personsList = new ArrayList(1000);
    private static Random rnd = new Random(999);

    private static String[] firstNames = {"Saurabh", "Rahul", "Amit", "Jhony", "Ravi", "Mohit", "Piyush", "Ajit", "Shweta", "Sunil"};
    private static String[] lastNames = {"Mishra", "Jain", "Sharma", "KKKK", "MMMM", "PPPP", "ZZZZ", "LLLL", "RRRRR", "NNNNN"};

    private static CacheQueryService<Person> personCacheService = new GSCacheQueryServiceImpl<Person>();

    @Rule
    public ExpectedException unusableEntryException = ExpectedException.none();

    @BeforeClass
    public static void init() {

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

    @AfterClass
    public static void cleanup() {
        //clean after all is processed
        personsList.forEach(
                v -> {
                    Arrays.stream(personCacheService.readAll(new Person())).forEach(q ->
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
        Optional<Person> result2 = personCacheService.readByQuery(new Person(), "firstName", "saurabh");
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
        assertTrue(count == arr.length);
    }

    @Test
    public void getAllPersonWithLastName() {
        String nameToSearch = "Mishra";
        Person[] arr = personCacheService.readAll(new Person("", nameToSearch));
        long count = personsList.stream().filter(p -> p.getLastName().equals(nameToSearch)).count();
        logger.info("search all by last name count :: " + arr.length);
        assertTrue(count == arr.length);
    }

    @Test
    public void getAllPersonWithAgeGtThen30() {
        Person[] arr = personCacheService.readMultipleByQuery(new Person(), QueryBuilder.queryBuilder(QueryConstants.GT, "age", "30"));
        long count = personsList.stream().filter(p -> p.getAge() > 30).count();
        logger.info("search all age >30 count :: " + arr.length);
        assertTrue(count == arr.length);
    }

    @Test
    public void writeDifferentVersionOfPersonShouldThrowException() {

        int indF = rnd.nextInt(9);
        int indL = rnd.nextInt(9);
        String firstName = firstNames[indF];
        String lastNmae = lastNames[indL];
        int age = rnd.nextInt(20) + 20;
        Person person = new Person(1001, firstName, lastNmae, "", age);
        personCacheService.write(person);

        Optional<Person> person2 = personCacheService.readById(new Person(), 1001);
        assertTrue(person2.isPresent());

        CacheQueryService<PersonV1> personV1CacheService = new GSCacheQueryServiceImpl<PersonV1>();
        unusableEntryException.expect(UnusableEntryException.class);
        unusableEntryException.expectMessage("The operation's type description is incompatible with the type description stored in the space.");
        personV1CacheService.write(new PersonV1(1001, firstName, lastNmae, age));
        cleanup();
    }

    @Test
    public void writeShouldThrowVersionConflictError() {

        int indF = rnd.nextInt(9);
        int indL = rnd.nextInt(9);
        String firstName = firstNames[indF];
        String lastNmae = lastNames[indL];
        int age = rnd.nextInt(20) + 20;
        Person person = new Person(1001, firstName, lastNmae, "", age);
        person.setVersion(1);
        Optional<Person> personOptional = personCacheService.readById(new Person(), 1001);
        Person personOld = personOptional.get();
        System.out.println(personOld);
        personCacheService.write(person);
        Person person1 = new Person(1001, firstName + "S", lastNmae + "M", "", age + 10);
        person1.setVersion(2);
        personCacheService.write(person1);

        //oldversion
        // no effect in update of version
        personCacheService.write(personOld);

    }


    @Test
    public void pollingContainerTest() {

    }


}
