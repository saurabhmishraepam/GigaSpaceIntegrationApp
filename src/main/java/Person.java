import com.gigaspaces.annotation.pojo.SpaceId;

public class Person {

    private Integer ssn;
    private String firstName;
    private String lastName;

    public Person(Integer ssn, String firstName, String lastName) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Default constructor (required by XAP)
    public Person() {}

    @SpaceId
    public Integer getSsn() {
        return ssn;
    }
    public void setSsn(Integer ssn) {
        this.ssn = ssn;
    }

    // Getters and Setters of firstName and lastName are omitted in this snippet.
}