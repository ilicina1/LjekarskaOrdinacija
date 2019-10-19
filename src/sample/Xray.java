package sample;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;

public class Xray {
    private int id;
    private String whatsOnRay;
    private LocalDate date;
    private Patients patient;

    public Xray() {
    }

    public Xray(int id, String whatsOnRay, LocalDate date, Patients patient) {
        this.id = id;
        this.whatsOnRay = whatsOnRay;
        this.date = date;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhatsOnRay() {
        return whatsOnRay;
    }

    public void setWhatsOnRay(String whatsOnRay) {
        this.whatsOnRay = whatsOnRay;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

}
