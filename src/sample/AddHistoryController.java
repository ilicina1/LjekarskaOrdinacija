package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddHistoryController {
    public TextField tfAllergies;
    public TextField tfFmi;
    public TextField tfAddictions;
    public TextField tfChi;

    private ClassDAO dao;
    private MedicalHistory history;
    private ObservableList<MedicalHistory> historyList;
    private Patients patient;

    public AddHistoryController(Patients patient) {
        this.patient = patient;
        dao = ClassDAO.getInstance();
    }

    public MedicalHistory getHistory() {
        return history;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfAllergies.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        history = new MedicalHistory(dao.getMaxId() + 1 ,tfAllergies.getText(), tfFmi.getText(), tfAddictions.getText(), tfChi.getText(), patient);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added a history");
        alert.showAndWait();

        Stage stage = (Stage) tfAllergies.getScene().getWindow();
        stage.close();
    }
}
