package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;

public class EditResultController {
    public TextField tfSample;
    public TextField tfTypeOfAnalysis;
    public TextField tfResult;
    public TextField tfNormalValue;

    private ClassDAO dao;
    private Results result;
    private MedicalReports report;
    private ObservableList<Results> listResults;

    public EditResultController(Results result, MedicalReports report) {
        dao = ClassDAO.getInstance();
        this.result = result;
        this.report = report;
    }

    @FXML
    public void initialize() {
        tfSample.setText(result.getSample());
        tfTypeOfAnalysis.setText(result.getTypeOfAnalysis());
        tfResult.setText(Double.toString(result.getResult()));
        tfNormalValue.setText(result.getNormalValue());
        tfSample.textProperty().addListener((obs, oldIme, newIme) -> {
            if (newIme.length() < 5) {
                tfSample.getStyleClass().removeAll("fieldIncorrect");
                tfSample.getStyleClass().add("fieldCorrect");
            } else {
                tfSample.getStyleClass().removeAll("fieldCorrect");
                tfSample.getStyleClass().add("fieldIncorrect");
            }
        });

        tfTypeOfAnalysis.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfTypeOfAnalysis.getStyleClass().removeAll("fieldIncorrect");
                tfTypeOfAnalysis.getStyleClass().add("fieldCorrect");
            } else {
                tfTypeOfAnalysis.getStyleClass().removeAll("fieldCorrect");
                tfTypeOfAnalysis.getStyleClass().add("fieldIncorrect");
            }
        });

        tfResult.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateResult(newIme)) {
                tfResult.getStyleClass().removeAll("fieldIncorrect");
                tfResult.getStyleClass().add("fieldCorrect");
            } else {
                tfResult.getStyleClass().removeAll("fieldCorrect");
                tfResult.getStyleClass().add("fieldIncorrect");
            }
        });

        tfNormalValue.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfNormalValue.getStyleClass().removeAll("fieldIncorrect");
                tfNormalValue.getStyleClass().add("fieldCorrect");
            } else {
                tfNormalValue.getStyleClass().removeAll("fieldCorrect");
                tfNormalValue.getStyleClass().add("fieldIncorrect");
            }
        });
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        result = new Results(result.getId() , tfSample.getText(), tfTypeOfAnalysis.getText(), Double.parseDouble(tfResult.getText()), tfNormalValue.getText(), report);
        dao.changeResult(result);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully edited the result!");
        alert.showAndWait();

        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
    }

    public boolean validateResult(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        }
        return true;
    }
}
