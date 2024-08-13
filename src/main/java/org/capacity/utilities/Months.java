package org.capacity.utilities;

import lombok.Getter;

import java.time.Month;

@Getter
public enum Months {
    JANUARY("January", Month.JANUARY), FEBRUARY("February", Month.FEBRUARY), MARCH("March", Month.MARCH), APRIL("Abril", Month.APRIL), MAY("May", Month.MAY),
    JUNE("June", Month.JUNE), JULY("July", Month.JULY), AUGUST("August", Month.AUGUST), SEPTEMBER("September", Month.SEPTEMBER), OCTOBER("October", Month.OCTOBER),
    NOVEMBER("November", Month.NOVEMBER), DECEMBER("December", Month.DECEMBER);

    private final String monthName;
    private final Month month;
    Months(String monthName, Month month) {
        this.monthName = monthName;
        this.month = month;
    }
}
