package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AddMedicalReportController {
    public DatePicker dpDate;

    private ClassDAOBase dao;
    private MedicalReports report;
    private Patients patient;

    public AddMedicalReportController(Patients patient) throws SQLException {
        this.patient = patient;
        dao = ClassDAOBase.getInstance();
    }

    public MedicalReports getReport() {
        return report;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) dpDate.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        report = new MedicalReports(dao.getMaxId2() + 1 , dpDate.getValue(), patient);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added report! \n Write down the results");
        alert.showAndWait();

        Stage stage = (Stage) dpDate.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addResults.fxml"));
            AddResultsController ctrl = new AddResultsController(report);
            loader.setController(ctrl);
            Parent root = loader.load();

            stage1.setTitle("Add results!");
            stage1.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage1.setResizable(false);
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
