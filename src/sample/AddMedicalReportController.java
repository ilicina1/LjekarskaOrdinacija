package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AddMedicalReportController {
    public DatePicker dpDate;

    public KlasaDAO dao;
    public MedicalReports report;
    private ObservableList<MedicalReports> listMedicalReports;
    private ObservableList<Results> listResults;
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
            AddResults ctrl = new AddResults(dao.rezultati(report.getId()));
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Add results!");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Results noviRezultat = ctrl.getRezultat();
                if (noviRezultat != null) {
                    dao.dodajRezultat(noviRezultat);
                    try {
                        listResults.setAll(dao.rezultati(report.getId()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
