package employees.domain;

public class WorkTime {
    private String employee1Id;
    private String employee2Id;
    private long millis;

    public WorkTime() {

    }

    public WorkTime(String empl1Id, String empl2Id, long mills) {
        employee1Id = empl1Id;
        employee2Id = empl2Id;
        millis = mills;
    }

    public String getEmployee1Id() {
        return employee1Id;
    }

    public void setEmployee1Id(String employee1Id) {
        this.employee1Id = employee1Id;
    }

    public String getEmployee2Id() {
        return employee2Id;
    }

    public void setEmployee2Id(String employee2Id) {
        this.employee2Id = employee2Id;
    }

    public long getMillis() {
        return millis;
    }

    public void setMillis(long millis) {
        this.millis = millis;
    }

    @Override
    public String toString() {
        return "WorkTime{" +
                "employee1Id='" + employee1Id + '\'' +
                ", employee2Id='" + employee2Id + '\'' +
                ", millis=" + millis +
                '}';
    }
}
