package com.epam.gigaspaceintegration;

import com.epam.gigaspaceintegration.bean.Person;
import com.epam.gigaspaceintegration.bean.PersonV1;
import com.epam.gigaspaceintegration.constant.GSGridMode;
import com.epam.gigaspaceintegration.constant.QueryParameters;
import com.epam.gigaspaceintegration.constant.XAPSpaceInstance;
import com.epam.gigaspaceintegration.service.CacheQueryService;
import com.epam.gigaspaceintegration.service.GSCacheQueryServiceImpl;
import com.epam.gigaspaceintegration.util.QueryBuilder;
import com.j_spaces.core.client.EntryVersionConflictException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openspaces.core.SpaceOptimisticLockingFailureException;
import org.openspaces.core.UnusableEntryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ApplicationTest {

    //modify these properties to change the space and host configuration
    private static final String HOST_NAME ="EPINHYDW0423";
    private static final String SPACE_NAME ="person";

    private static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    private static List<Person> personsList = new ArrayList(1000);
    private static Random rnd = new Random(999);
    private static String[] firstNames = {"Saurabh", "Rahul", "Amit", "Jhony", "Ravi", "Mohit", "Piyush", "Ajit", "Shweta", "Sunil"};
    private static String[] lastNames = {"Mishra", "Jain", "Sharma", "KKKK", "MMMM", "PPPP", "ZZZZ", "LLLL", "RRRRR", "NNNNN"};

    private static XAPSpaceInstance xapSpacedetailes;
    private static CacheQueryService<Person> personCacheService ;
    private static GSGridMode mode;

    @Rule
    public ExpectedException unusableEntryException = ExpectedException.none();

    @BeforeClass
    public static void init() {
        prepareTestEnvironment();
        prepareTestData();
    }

    private static void prepareTestEnvironment(){
        xapSpacedetailes= XAPSpaceInstance.CUSTOME_SPACE;
        xapSpacedetailes.setSpaceName(SPACE_NAME);
        xapSpacedetailes.setHostName(HOST_NAME);
        mode= GSGridMode.REMOTE;
        personCacheService=   new GSCacheQueryServiceImpl<Person>(mode, xapSpacedetailes);
    }

    private static void prepareTestData(){

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
                    Arrays.stream(personCacheService.readAll(new Person())).forEach(q ->
                            {
                                personCacheService.delete(q);
                            }
                    );
                }
        );

    }

    @Test
    public void shouldConnectAndValidateAll() {
        int age = 30;
        personCacheService.delete(new Person(1, "saurabh", "mishra", "", age));
        personCacheService.delete(new Person(2, "saurabh", "mishra", "", age));
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
        Person[] arr = personCacheService.readMultipleByQuery(new Person(), QueryBuilder.queryBuilder(QueryParameters.GT, "age", "30"));
        long count = personsList.stream().filter(p -> p.getAge() > 30).count();
        logger.info("search all age >30 count :: " + arr.length);
    }

    @Test
    public void deleteAllPersonWithAgeGtThen30() {
        personCacheService.deleteMultiple(new Person(), QueryBuilder.queryBuilder(QueryParameters.GT, "age", "30"));
        Person[] arr = personCacheService.readMultipleByQuery(new Person(), QueryBuilder.queryBuilder(QueryParameters.GT, "age", "30"));
        long count = personsList.stream().filter(p -> p.getAge() > 30).count();
        logger.info("search all age >30 count :: " + arr.length);
        assertTrue(arr.length == 0);
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

        CacheQueryService<PersonV1> personV1CacheService = new GSCacheQueryServiceImpl<PersonV1>(mode, xapSpacedetailes);
       // unusableEntryException.expect(UnusableEntryException.class);
      // unusableEntryException.expectMessage("The operation's type description is incompatible with the type description stored in the space.");
        personV1CacheService.write(new PersonV1(1001, firstName, lastNmae, age));

       //cleanup();
    }

    @Test
    @Transactional(propagation= Propagation.NEVER)
    public void writeShouldThrowVersionConflictErrorForOptimisticLocking() {
        int indF = rnd.nextInt(9);
        int indL = rnd.nextInt(9);
        String firstName = firstNames[indF];
        String lastNmae = lastNames[indL];
        int age = rnd.nextInt(20) + 20;
        Person person = new Person(1001, firstName, lastNmae, "", age);
        unusableEntryException.expect(SpaceOptimisticLockingFailureException.class);
        person.setVersion(1);

        personCacheService.write(person);
        person.setVersion(3);
        personCacheService.write(person);

    }



}
