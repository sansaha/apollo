package com.lexmark.apollo.api.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusDto {

    public String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private Date currentDate;
    private String version;

}
