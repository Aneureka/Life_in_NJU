package util.tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyDate{

    private final LocalDate localDate;

    public MyDate(LocalDate localDate){
        this.localDate = localDate;
    }

    public LocalDate asLocalDate(){
        return localDate;
    }

    public boolean isBetween(LocalDate begin, LocalDate end){
        return (localDate.isEqual(begin) || localDate.isAfter(begin)) && (localDate.isBefore(end) || localDate.isEqual(end));
    }

    public static void main(String[] args){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/dd/yy");

        LocalDate localDate =LocalDate.parse("2012-01-12");

        System.out.print(localDate);
    }

}