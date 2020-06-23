package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class AddResultsController {
    public TextField tfSample;
    public TextField tfTypeOfAnalysis;
    public TextField tfResult;
    public TextField tfNormalValue;

    private ClassDAO dao;
    private Results result;
    private ObservableList<Results> listResults;
    private MedicalReports report;


    public AddResultsController(MedicalReports report) {
        dao = ClassDAO.getInstance();
        this.report = report;
    }

    @FXML
    public void initialize() {
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

    public Results getResult() {
        return result;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        result = null;
        result = new Results(dao.getMaxId3() + 1 , tfSample.getText(), tfTypeOfAnalysis.getText(), Double.parseDouble(tfResult.getText()), tfNormalValue.getText(), report);

        dao.addResult(result);

        ButtonType Yes = new ButtonType("Yes");
        ButtonType No = new ButtonType("No");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Would you like to add another one? \n Press Yes if you do!", Yes, No);
        alert.setTitle("");
        alert.setHeaderText("You have successfully added the result!");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Yes){
            tfSample.setText("");
            tfTypeOfAnalysis.setText("");
            tfResult.setText("");
            tfNormalValue.setText("");
            tfSample.getStyleClass().removeAll("fieldIncorrect");
            tfSample.getStyleClass().removeAll("fieldCorrect");
            tfTypeOfAnalysis.getStyleClass().removeAll("fieldIncorrect");
            tfTypeOfAnalysis.getStyleClass().removeAll("fieldCorrect");
            tfResult.getStyleClass().removeAll("fieldIncorrect");
            tfResult.getStyleClass().removeAll("fieldCorrect");
            tfNormalValue.getStyleClass().removeAll("fieldIncorrect");
            tfNormalValue.getStyleClass().removeAll("fieldCorrect");
        } else {
            Stage stage = (Stage) tfSample.getScene().getWindow();
            stage.close();
        }
    }

    public boolean validateResult(String str){
        for(int i = 0; i < str.length(); i++){
            if((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.') return false;
        }
        return true;
    }
}
