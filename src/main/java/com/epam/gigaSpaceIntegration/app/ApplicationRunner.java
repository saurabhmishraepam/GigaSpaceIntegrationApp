package com.epam.gigaSpaceIntegration.app;

import com.epam.gigaSpaceIntegration.bean.Person;
import com.epam.gigaSpaceIntegration.service.GSPersonService;
import com.j_spaces.core.client.SQLQuery;

import java.util.Optional;

public class ApplicationRunner {

    public static void main(String[] args) {
        GSPersonService gsPersonService = new GSPersonService();

        int age=30;
        gsPersonService.write(new Person(1,"saurabh", "mishra", "", age));
        gsPersonService.write(new Person(2,"saurabh", "mishra", "", age));

        Optional<Person> result1 = gsPersonService.readById(1);
        System.out.println("Result: " + result1);

        Optional<Person> result2 = gsPersonService.readByQuery("firstName", "Johnny");
        System.out.println("Result: " + result2);

        System.out.println("Read all entries of type Person from the grid:");
        Person[] results = gsPersonService.readAll(new Person());
        System.out.println("Result: " + java.util.Arrays.toString(results));


    }

}
