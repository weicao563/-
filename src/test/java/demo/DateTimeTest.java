package demo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.testng.annotations.Test;


public class DateTimeTest {

    @Test
    public void test01() {

//        DateTime.now().
//        long millis = new DateTime(new DateTime().toString("2020-04-09 18:20:33")).getMillis();
//        System.out.println(millis);

        long millis = DateTime.parse("2020-04-09 18:20:33", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"))
                .getMillis();
        System.out.println(millis);

    }
}
