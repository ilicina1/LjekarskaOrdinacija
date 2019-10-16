package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddMedicalReportController {
    public TextField tfSample;
    public TextField tfTypeOfAnalysis;
    public TextField tfResult;
    public TextField tfNormalValue;
    public DatePicker dpDate;

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

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        report = new MedicalReports(dao.dajNajveciId2() + 1 , dpDate.getValue(), pacijent);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added report");
        alert.showAndWait();
        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
        
    }
}
