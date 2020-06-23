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
    private MedicalHistory historija;
    private ObservableList<MedicalHistory> listHistorije;
    private Patients pacijent;

    public AddHistoryController(Patients pacijent) {
        this.pacijent = pacijent;
        dao = ClassDAO.getInstance();
    }

    public MedicalHistory getHistorija() {
        return historija;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfAllergies.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        historija = new MedicalHistory(dao.getMaxId() + 1 ,tfAllergies.getText(), tfFmi.getText(), tfAddictions.getText(), tfChi.getText(), pacijent);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added a history");
        alert.showAndWait();
        Stage stage = (Stage) tfAllergies.getScene().getWindow();
        stage.close();
    }
}
