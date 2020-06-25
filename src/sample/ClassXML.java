package sample;

import java.beans.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClassXML {
    private ArrayList<Patients> patients = new ArrayList<>();
    private ArrayList<Xray> xrays = new ArrayList<>();
    private ArrayList<Results> results = new ArrayList<>();
    private ArrayList<Appointments> appointments = new ArrayList<>();
    private ArrayList<MedicalReports> reports = new ArrayList<>();
    private ArrayList<MedicalHistory> histories = new ArrayList<>();
    private ArrayList<Diagnosis> diagnoses = new ArrayList<>();

    ClassXML(ArrayList<Patients> patients, ArrayList<Xray> xrays, ArrayList<Results> results, ArrayList<Appointments> appointments, ArrayList<MedicalReports> reports, ArrayList<MedicalHistory> histories, ArrayList<Diagnosis> diagnoses) throws IOException, SQLException {
        this.patients = patients;
        this.xrays = xrays;
        this.results = results;
        this.appointments = appointments;
        this.reports = reports;
        this.histories = histories;
        this.diagnoses = diagnoses;

        save();
    }

    public void save() throws IOException, SQLException {
        try {
            XMLEncoder XMLdecoder = new XMLEncoder(new FileOutputStream("patients.xml"));
            XMLdecoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });
            XMLdecoder.writeObject(patients);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("xray.xml"));
            XMLdecoder.writeObject(xrays);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("results.xml"));
            XMLdecoder.writeObject(results);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("appointments.xml"));
            XMLdecoder.writeObject(appointments);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("reports.xml"));
            XMLdecoder.writeObject(reports);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("histories.xml"));
            XMLdecoder.writeObject(histories);
            XMLdecoder.close();

            XMLdecoder = new XMLEncoder(new FileOutputStream("diagnoses.xml"));
            XMLdecoder.writeObject(diagnoses);
            XMLdecoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
