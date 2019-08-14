package com.epam.gigaSpaceIntegration.app;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.service.GSPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {
        GSPersonService gsPersonService = new GSPersonService();
        int age = 30;
        gsPersonService.write(new Person(1, "saurabh", "mishra", "", age));
        gsPersonService.write(new Person(2, "saurabh", "mishra", "", age));
        Optional<Person> result1 = gsPersonService.readById(1);
        logger.info("Reult:: " + result1);
        Optional<Person> result2 = gsPersonService.readByQuery("firstName", "Johnny");
        logger.info("Result: " + result2);
        Person[] results = gsPersonService.readAll(new Person());
        logger.info("Result: " + java.util.Arrays.toString(results));
    }

}
