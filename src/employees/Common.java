package employees;

import employees.domain.WorkHistory;
import employees.domain.WorkTime;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class Common {
    public static DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder()
            .parseCaseInsensitive().parseLenient()
            .appendPattern("[yyyy-MM-dd]")
            .appendPattern("[M/dd/yyyy]")
            .appendPattern("[M/d/yyyy]")
            .appendPattern("[MM/dd/yyyy]")
            .appendPattern("[MMM dd yyyy]");
    public static DateTimeFormatter formatter = builder.toFormatter(Locale.ENGLISH);

    public static List<WorkHistory> getWorkHistoryFromFile(String filename) {
        Path path = Paths.get(filename);
        List<String> records = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(a -> records.add(a));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        List<WorkHistory> whList = new ArrayList<>();
        for(String f : records) {
            String ws = f.replaceAll("\\s+","");
            String[] segments = ws.split(",");
            if("null".equalsIgnoreCase(segments[2])) {
                segments[2] = LocalDate.now().toString();
            }
            if("null".equalsIgnoreCase(segments[3])) {
                segments[3] = LocalDate.now().toString();
            }
            whList.add(new WorkHistory(segments[0], segments[1], LocalDate.parse(segments[2], formatter), LocalDate.parse(segments[3], formatter)));
        }
        return whList;
    }

    public static List<WorkTime> getWorkTimeFromWorkHistory(List<WorkHistory> whList) {
        List<WorkTime> wtList = new ArrayList<>();
        for(int i=0; i<whList.size(); i++) {
            WorkHistory wh1 = whList.get(i);
            for(int j=i+1; j<whList.size(); j++) {
                LocalDate start;
                LocalDate stop;
                WorkHistory wh2 = whList.get(j);
                LocalDate ldf1 = wh1.getDateFrom();
                LocalDate ldt1 = wh1.getDateTo();
                LocalDate ldf2 = wh2.getDateFrom();
                LocalDate ldt2 = wh2.getDateTo();
                if(wh1.getProjectId().equals(wh2.getProjectId())) {
                    start = ldf1.isBefore(ldf2) ? ldf2 : ldf1;
                    stop = ldt1.isBefore(ldt2) ? ldt2 : ldt1;
                    Long startMs = start.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                    Long stopMs = stop.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
                    if(start.isBefore(stop) || start.isEqual(stop)) {
                        wtList.add(new WorkTime(wh1.getEmployeeId(), wh2.getEmployeeId(), stopMs-startMs));
                    }
                }
            }
        }
        return wtList;
    }

    public static void displayCommonWorkTime(List<WorkTime> wtList) {
        List<WorkTime> wtList2 = new ArrayList<>();
        for(int i = 0; i<wtList.size(); i++) {
            WorkTime wt1 = wtList.get(i);
            String wt1id1 = wt1.getEmployee1Id();
            String wt1id2 = wt1.getEmployee2Id();
            long time = wt1.getMillis();
            for(int j=i+1; j<wtList.size(); j++) {
                WorkTime wt2 = wtList.get(j);
                String wt2id1 = wt2.getEmployee1Id();
                String wt2id2 = wt2.getEmployee2Id();
                if((wt1id1.equals(wt2id1) || wt1id1.equals(wt2id2)) && (wt1id2.equals(wt2id2) || wt1id2.equals(wt2id1))) {
                    time += wt2.getMillis();
                    wtList.remove(j);
                }
            }
            wt1.setMillis(time);
            wtList2.add(new WorkTime(wt1id1, wt1id2, time));
        }
        wtList2.sort(Comparator.comparing(WorkTime::getMillis).reversed());
        wtList2.forEach(System.out::println);
    }
}
