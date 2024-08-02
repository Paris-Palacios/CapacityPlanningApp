package org.capacity.utilities;

import java.sql.Date;
import java.time.LocalDate;

public class Utils {
    public static LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }
}
