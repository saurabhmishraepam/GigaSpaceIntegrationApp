package com.epam.gigaspaceintegration.bean;

import com.gigaspaces.annotation.pojo.*;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class PersonV1 {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer version;

    public PersonV1() { }
    public PersonV1(String firstName) {
        this.firstName = firstName;
    }
    public PersonV1(String firstName, String lastName) {
        this.lastName = lastName;
    }

    public PersonV1(Integer id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @SpaceId
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonV1 person = (PersonV1) o;

        return id.equals(person.id);
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", version=" + version +
                '}';
    }
}
