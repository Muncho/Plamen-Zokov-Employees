package employees.domain;

import java.time.LocalDate;

public class WorkHistory {

    private String employeeId;
    private String projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public WorkHistory(String emplId, String projId, LocalDate df, LocalDate dt) {
        employeeId = emplId;
        projectId = projId;
        dateFrom = df;
        dateTo = dt;
    }

    public WorkHistory() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
