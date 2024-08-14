package org.capacity.utilities;

import javafx.scene.control.ListCell;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.Function;

public class Utils {
    public static LocalDate convertToLocalDate(Date date) {
        System.out.println(date);
        if (date == null) {
            return null;
        }
        return date.toLocalDate();
    }

    public static <T> ListCell<T> setCellText(Function<T, String> setText) {
        return new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(item != null ? setText.apply(item) : null);
            }
        };
    }
}
