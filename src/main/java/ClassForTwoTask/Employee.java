package ClassForTwoTask;

public class Employee {
    private final String SecondName;
    private final Integer Age;
    private final String JobTitle;

    public String getSecondName() {
        return SecondName;
    }

    public Integer getAge() {
        return Age;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public Employee(String SecondName, Integer age, String jobTitle) {
        this.SecondName = SecondName;
        this.Age = age;
        this.JobTitle = jobTitle;
    }
}
