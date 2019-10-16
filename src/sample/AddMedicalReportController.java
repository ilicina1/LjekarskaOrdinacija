package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AddMedicalReportController {
    public KlasaDAO dao;
    public MedicalReports report;
    private ObservableList<MedicalReports> listMedicalReports;
    public Patients pacijent;

    public AddMedicalReportController(ArrayList<MedicalReports> medicalReports, Patients pacijent) {
        listMedicalReports = FXCollections.observableArrayList(medicalReports);
        this.pacijent = pacijent;
        dao = KlasaDAO.getInstance();
    }

    public MedicalReports getReport() {
        return report;
    }
}
