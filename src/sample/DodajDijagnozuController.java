package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class DodajDijagnozuController {
    public TextField tfId;
    public TextArea taDiagnosis;
    public Diagnosis dijagnoza;
    private ObservableList<Diagnosis> listDijagnoze;
    public Patients pacijent;
    public KlasaDAO dao;

    public DodajDijagnozuController(Diagnosis dijagnoza, ArrayList<Diagnosis> dijagnoze, Patients pacijent) {
        this.dijagnoza = dijagnoza;
        listDijagnoze = FXCollections.observableArrayList(dijagnoze);
        this.pacijent = pacijent;
        dao = KlasaDAO.getInstance();
    }

    @FXML
    public void initialize() {
            tfId.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateId(newIme)) {
                    tfId.getStyleClass().removeAll("poljeNijeIspravno");
                    tfId.getStyleClass().add("poljeIspravno");
                } else {
                    tfId.getStyleClass().removeAll("poljeIspravno");
                    tfId.getStyleClass().add("poljeNijeIspravno");
                }
            });
    }

    public Diagnosis getDijagnoza() {
        return dijagnoza;
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        boolean id=false;
        boolean sveOk = true;

        id = dao.validateDiagnosis(Integer.parseInt(tfId.getText().trim()));
        if (validateId(tfId.getText().trim())) {

            tfId.getStyleClass().removeAll("poljeNijeIspravno");
            tfId.getStyleClass().add("poljeIspravno");
        } else {
            tfId.getStyleClass().removeAll("poljeIspravno");
            tfId.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }

        if(id) {
            sveOk = false;
            tfId.getStyleClass().removeAll("poljeIspravno");
            tfId.getStyleClass().add("poljeNijeIspravno");
        }
//        for (int i = 0; i < listDijagnoze.size(); i++) {
//            if (listDijagnoze.get(i).getId() == Integer.parseInt(tfId.getText())) sveOk = false;
//        }

        if (!sveOk) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Ooops, there was an error!");
            alert.setContentText("Check id!");
            alert.showAndWait();
            return;
        } else {
            dijagnoza = new Diagnosis(Integer.parseInt(tfId.getText()), taDiagnosis.getText(), pacijent);
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
