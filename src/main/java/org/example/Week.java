package org.example;

import java.time.LocalDate;

public class Week {

    private LocalDate startDate;
    private LocalDate endDate;

    public Week(LocalDate startDate) {
        this.startDate = startDate;
        this.endDate = startDate.plusDays(6);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}
