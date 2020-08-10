package employees;

import employees.domain.WorkHistory;
import employees.domain.WorkTime;

import java.util.List;

public class Sorting {
    public static void main(String[] args) {
        List<WorkHistory> whList = Common.getWorkHistoryFromFile("records.txt");
        List<WorkTime> wtList = Common.getWorkTimeFromWorkHistory(whList);
        Common.displayCommonWorkTime(wtList);
    }
}
