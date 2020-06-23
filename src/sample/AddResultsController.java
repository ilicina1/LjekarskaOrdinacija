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
    private Results rezultat;
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
                tfSample.getStyleClass().removeAll("poljeNijeIspravno");
                tfSample.getStyleClass().add("poljeIspravno");
            } else {
                tfSample.getStyleClass().removeAll("poljeIspravno");
                tfSample.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfTypeOfAnalysis.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfTypeOfAnalysis.getStyleClass().removeAll("poljeNijeIspravno");
                tfTypeOfAnalysis.getStyleClass().add("poljeIspravno");
            } else {
                tfTypeOfAnalysis.getStyleClass().removeAll("poljeIspravno");
                tfTypeOfAnalysis.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfResult.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateResult(newIme)) {
                tfResult.getStyleClass().removeAll("poljeNijeIspravno");
                tfResult.getStyleClass().add("poljeIspravno");
            } else {
                tfResult.getStyleClass().removeAll("poljeIspravno");
                tfResult.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfNormalValue.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfNormalValue.getStyleClass().removeAll("poljeNijeIspravno");
                tfNormalValue.getStyleClass().add("poljeIspravno");
            } else {
                tfNormalValue.getStyleClass().removeAll("poljeIspravno");
                tfNormalValue.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public Results getRezultat() {
        return rezultat;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfSample.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        rezultat = null;
        rezultat = new Results(dao.getMaxId3() + 1 , tfSample.getText(), tfTypeOfAnalysis.getText(), Double.parseDouble(tfResult.getText()), tfNormalValue.getText(), report);
        dao.addResult(rezultat);
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
            tfSample.getStyleClass().removeAll("poljeNijeIspravno");
            tfSample.getStyleClass().removeAll("poljeIspravno");
            tfTypeOfAnalysis.getStyleClass().removeAll("poljeNijeIspravno");
            tfTypeOfAnalysis.getStyleClass().removeAll("poljeIspravno");
            tfResult.getStyleClass().removeAll("poljeNijeIspravno");
            tfResult.getStyleClass().removeAll("poljeIspravno");
            tfNormalValue.getStyleClass().removeAll("poljeNijeIspravno");
            tfNormalValue.getStyleClass().removeAll("poljeIspravno");
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
