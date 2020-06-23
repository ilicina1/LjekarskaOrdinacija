package sample;

import java.time.LocalDate;

public class MedicalReports {
    private int id;
    private LocalDate date;
    private Patients patient;

    public MedicalReports() {
    }

    public MedicalReports(int id, LocalDate date, Patients patient) {
        this.id = id;
        this.date = date;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
