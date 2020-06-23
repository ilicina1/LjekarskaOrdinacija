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

    public ClassDAO dao;
    public Results rezultat;
    public MedicalReports report;
    private ObservableList<Results> listResults;

    public EditResultController(Results rezultat, MedicalReports report) {
        dao = ClassDAO.getInstance();
        this.rezultat = rezultat;
        this.report = report;
    }

    @FXML
    public void initialize() {
        tfSample.setText(rezultat.getSample());
        tfTypeOfAnalysis.setText(rezultat.getTypeOfAnalysis());
        tfResult.setText(Double.toString(rezultat.getResult()));
        tfNormalValue.setText(rezultat.getNormalValue());
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

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        rezultat = new Results(rezultat.getId() , tfSample.getText(), tfTypeOfAnalysis.getText(), Double.parseDouble(tfResult.getText()), tfNormalValue.getText(), report);
        dao.changeResult(rezultat);
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
