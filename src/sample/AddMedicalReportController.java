package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AddMedicalReportController {
    public DatePicker dpDate;

    public KlasaDAO dao;
    public MedicalReports report;
    public Patients pacijent;

    public AddMedicalReportController(ArrayList<MedicalReports> medicalReports, Patients pacijent) throws SQLException {
        this.pacijent = pacijent;
        dao = KlasaDAO.getInstance();
    }

    public MedicalReports getReport() {
        return report;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) dpDate.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        report = new MedicalReports(dao.dajNajveciId2() + 1 , dpDate.getValue(), pacijent);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added report! \n Write down the results");
        alert.showAndWait();
        Stage stage = (Stage) dpDate.getScene().getWindow();
        stage.close();
        // ovdje treba ici otvaranje novog prozora za upis rezultata nalaza!!!
        Stage stage1 = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addResults.fxml"));
            AddResultsController ctrl = new AddResultsController(dao.rezultati(report.getId()), report);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage1.setTitle("Add results!");
            stage1.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage1.setResizable(false);
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
