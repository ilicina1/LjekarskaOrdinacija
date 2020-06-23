package sample;

import java.time.LocalDate;

public class Appointments {
    private int id;
    private String nameAndSurname;
    private String time;
    private String reason;
    private LocalDate date;

    public Appointments() {
    }

    public Appointments(int id, String nameAndSurname, String time, String reason, LocalDate date) {
        this.id = id;
        this.nameAndSurname = nameAndSurname;
        this.time = time;
        this.reason = reason;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
