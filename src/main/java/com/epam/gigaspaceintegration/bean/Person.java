package com.epam.gigaspaceintegration.bean;

import com.gigaspaces.annotation.pojo.*;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer age;
    private Integer version;

    public Person() { }
    public Person(String firstName) {
        this.firstName = firstName;
    }
    public Person(String firstName, String lastName) {
        this.lastName = lastName;
    }
    public Person(Integer id, String firstName, String lastName, String phoneNumber, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @SpaceRouting
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @SpaceIndex(type = SpaceIndexType.EQUAL)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SpaceVersion
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", version=" + version +
                '}';
    }
}