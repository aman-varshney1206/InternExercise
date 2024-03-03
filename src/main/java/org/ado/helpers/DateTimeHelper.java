package org.ado.helpers;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Log4j2
public class DateTimeHelper {

    public static int compareDates(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public static Date parseDateString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat;
        // Use one format for dates with time and another for dates without time
        if (dateString.contains("T")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return dateFormat.parse(dateString);
    }

    public static String readDateFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.readLine();
        }
    }

    public static void writeDateToFile(String date, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(date);
            log.info("ChangedDates written to file: " + filePath);
        }
    }
}
