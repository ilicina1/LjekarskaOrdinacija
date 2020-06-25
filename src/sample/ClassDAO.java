package sample;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ClassDAO {
    void registerDoctor(String firstName, String lastName, String userName, String password, String eMail) throws SQLException;

    ArrayList<Patients> patients();

    ArrayList<Diagnosis> diagnosis(int id) throws SQLException;

    ArrayList<MedicalHistory> history(int id) throws SQLException;

    ArrayList<MedicalReports> reports(int id) throws SQLException;

    ArrayList<Results> results(int id) throws SQLException;

    ArrayList<Appointments> appointments();

    ArrayList<Xray> xrays(int id) throws SQLException;

    void changePatient(Patients pacijent);

    void addPatient(Patients pacijent);

    void deletePatient(Patients pacijent);

    void addDiagnosis(Diagnosis dijagnoza);

    void changeDiagnosis(Diagnosis dijagnoza);

    void deleteDiagnosis(Diagnosis dijagnoza);

    void addHistory(MedicalHistory historija);

    void deleteHistory(MedicalHistory historija);

    void addReport(MedicalReports medicalReport);

    void deleteReport(MedicalReports nalaz);

    void addResult(Results rezultat);

    void deleteResult(Results rezultat);

    void deleteResults(MedicalReports nalaz);

    void changeResult(Results rezultat);

    void deleteAppointment(Appointments appointment);

    void addAppointment(Appointments appointment);

    void addXray(Xray xray, FileInputStream fis, int len);

    void deleteXray(Xray xray);
}
