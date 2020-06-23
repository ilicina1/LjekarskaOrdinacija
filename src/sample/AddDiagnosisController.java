package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddDiagnosisController {
    public TextField tfId;
    public TextArea taDiagnosis;

    private Diagnosis diagnosis;
    private Patients patient;
    private ClassDAO dao;

    public AddDiagnosisController(Diagnosis diagnosis, Patients patient) {
        this.diagnosis = diagnosis;
        this.patient = patient;

        dao = ClassDAO.getInstance();
    }

    @FXML
    public void initialize() {
            tfId.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateId(newIme)) {
                    tfId.getStyleClass().removeAll("fieldIncorrect");
                    tfId.getStyleClass().add("fieldCorrect");
                } else {
                    tfId.getStyleClass().removeAll("fieldCorrect");
                    tfId.getStyleClass().add("fieldIncorrect");
                }
            });
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        boolean id=false;
        boolean sveOk = true;

        id = dao.validateDiagnosis(Integer.parseInt(tfId.getText().trim()));

        if (validateId(tfId.getText().trim())) {

            tfId.getStyleClass().removeAll("fieldIncorrect");
            tfId.getStyleClass().add("fieldCorrect");
        } else {
            tfId.getStyleClass().removeAll("fieldCorrect");
            tfId.getStyleClass().add("fieldIncorrect");
            sveOk = false;
        }

        if(id) {
            sveOk = false;
            tfId.getStyleClass().removeAll("fieldCorrect");
            tfId.getStyleClass().add("fieldIncorrect");
        }

        if (!sveOk) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Ooops, there was an error!");
            alert.setContentText("Check id!");
            alert.showAndWait();
            return;
        } else {
            diagnosis = new Diagnosis(Integer.parseInt(tfId.getText()), taDiagnosis.getText(), patient);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully added a diagnosis");
            alert.showAndWait();
        }
        Stage stage = (Stage) tfId.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfId.getScene().getWindow();
        stage.close();
    }

    public boolean validateId(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        }
        return true;
    }
}
