package sample;

public class Diagnosis {
    public int id;
    public String text;
    public Patients patient;

    public Diagnosis() {
    }

    public Diagnosis(int id, String text, Patients patient) {
        this.id = id;
        this.text = text;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }
}
