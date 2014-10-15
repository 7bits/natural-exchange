package it.sevenbits.web.util;

public class DatePair {
    private Long dateFrom;
    private Long dateTo;

    public DatePair(Long dateFrom, Long dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }


    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }
}
