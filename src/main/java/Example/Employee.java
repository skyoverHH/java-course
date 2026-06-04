package Example;

public class Employee {
    private final String FirstName;
    private final Integer Age;
    private final String JobTitle;

    public String getFirstName() {
        return FirstName;
    }

    public Integer getAge() {
        return Age;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public Employee(String firstName, Integer age, String jobTitle) {
        this.FirstName = firstName;
        this.Age = age;
        this.JobTitle = jobTitle;
    }
}
