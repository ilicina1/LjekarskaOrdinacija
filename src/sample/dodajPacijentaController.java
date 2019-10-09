package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class dodajPacijentaController {
    public TextField tfMedicalRN;
    public TextField tfFullName;
    public TextField tfPhoneNum;
    public TextField tfCity;
    public TextField tfAddress;
    public TextField tfBirthDate;

    private Patients pacijent;
    private ObservableList<Patients> listPacijenti;

    public dodajPacijentaController(Patients pacijent, ArrayList<Patients> pacijenti) {
        this.pacijent = pacijent;
        listPacijenti = FXCollections.observableArrayList(pacijenti);
    }

    @FXML
    public void initialize() {
        if(pacijent != null){
            tfMedicalRN.setText(Integer.toString(pacijent.getMedicalRecordNumber()));
            tfFullName.setText(pacijent.getFullName());
            tfPhoneNum.setText(pacijent.getPhoneNumber());
            tfCity.setText(pacijent.getCity());
            tfAddress.setText(pacijent.getAddress());
            tfBirthDate.setText(pacijent.getBirthDate());
        }else {
            tfMedicalRN.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateMedicalRN(newIme)) {
                    tfMedicalRN.getStyleClass().removeAll("poljeNijeIspravno");
                    tfMedicalRN.getStyleClass().add("poljeIspravno");
                } else {
                    tfMedicalRN.getStyleClass().removeAll("poljeIspravno");
                    tfMedicalRN.getStyleClass().add("poljeNijeIspravno");
                }
            });

            tfFullName.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateFullName(newIme)) {
                    tfFullName.getStyleClass().removeAll("poljeNijeIspravno");
                    tfFullName.getStyleClass().add("poljeIspravno");
                } else {
                    tfFullName.getStyleClass().removeAll("poljeIspravno");
                    tfFullName.getStyleClass().add("poljeNijeIspravno");
                }
            });

            tfPhoneNum.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validatePhoneNum(newIme)) {
                    tfPhoneNum.getStyleClass().removeAll("poljeNijeIspravno");
                    tfPhoneNum.getStyleClass().add("poljeIspravno");
                } else {
                    tfPhoneNum.getStyleClass().removeAll("poljeIspravno");
                    tfPhoneNum.getStyleClass().add("poljeNijeIspravno");
                }
            });

            tfCity.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateCity(newIme)) {
                    tfCity.getStyleClass().removeAll("poljeNijeIspravno");
                    tfCity.getStyleClass().add("poljeIspravno");
                } else {
                    tfCity.getStyleClass().removeAll("poljeIspravno");
                    tfCity.getStyleClass().add("poljeNijeIspravno");
                }
            });

            tfAddress.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateAddress(newIme)) {
                    tfAddress.getStyleClass().removeAll("poljeNijeIspravno");
                    tfAddress.getStyleClass().add("poljeIspravno");
                } else {
                    tfAddress.getStyleClass().removeAll("poljeIspravno");
                    tfAddress.getStyleClass().add("poljeNijeIspravno");
                }
            });

            tfBirthDate.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateBirthDate(newIme)) {
                    tfBirthDate.getStyleClass().removeAll("poljeNijeIspravno");
                    tfBirthDate.getStyleClass().add("poljeIspravno");
                } else {
                    tfBirthDate.getStyleClass().removeAll("poljeIspravno");
                    tfBirthDate.getStyleClass().add("poljeNijeIspravno");
                }
            });
        }
        tfMedicalRN.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateMedicalRN(newIme)) {
                tfMedicalRN.getStyleClass().removeAll("poljeNijeIspravno");
                tfMedicalRN.getStyleClass().add("poljeIspravno");
            } else {
                tfMedicalRN.getStyleClass().removeAll("poljeIspravno");
                tfMedicalRN.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfFullName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateFullName(newIme)) {
                tfFullName.getStyleClass().removeAll("poljeNijeIspravno");
                tfFullName.getStyleClass().add("poljeIspravno");
            } else {
                tfFullName.getStyleClass().removeAll("poljeIspravno");
                tfFullName.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfPhoneNum.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validatePhoneNum(newIme)) {
                tfPhoneNum.getStyleClass().removeAll("poljeNijeIspravno");
                tfPhoneNum.getStyleClass().add("poljeIspravno");
            } else {
                tfPhoneNum.getStyleClass().removeAll("poljeIspravno");
                tfPhoneNum.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfCity.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateCity(newIme)) {
                tfCity.getStyleClass().removeAll("poljeNijeIspravno");
                tfCity.getStyleClass().add("poljeIspravno");
            } else {
                tfCity.getStyleClass().removeAll("poljeIspravno");
                tfCity.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfAddress.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateAddress(newIme)) {
                tfAddress.getStyleClass().removeAll("poljeNijeIspravno");
                tfAddress.getStyleClass().add("poljeIspravno");
            } else {
                tfAddress.getStyleClass().removeAll("poljeIspravno");
                tfAddress.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfBirthDate.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateBirthDate(newIme)) {
                tfBirthDate.getStyleClass().removeAll("poljeNijeIspravno");
                tfBirthDate.getStyleClass().add("poljeIspravno");
            } else {
                tfBirthDate.getStyleClass().removeAll("poljeIspravno");
                tfBirthDate.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public Patients getPacijent() {
        return pacijent;
    }

    //ovdje dodati validacije
    public void actionConfirm(ActionEvent actionEvent) throws IOException, SQLException {
        boolean sveOk = true;

        if (validateMedicalRN(tfMedicalRN.getText().trim())) {
            tfMedicalRN.getStyleClass().removeAll("poljeNijeIspravno");
            tfMedicalRN.getStyleClass().add("poljeIspravno");
        } else {
            tfMedicalRN.getStyleClass().removeAll("poljeIspravno");
            tfMedicalRN.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        if (validateFullName(tfFullName.getText())) {
            tfFullName.getStyleClass().removeAll("poljeNijeIspravno");
            tfFullName.getStyleClass().add("poljeIspravno");
        } else {
            tfFullName.getStyleClass().removeAll("poljeIspravno");
            tfFullName.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        if (validatePhoneNum(tfPhoneNum.getText().trim())) {
            tfPhoneNum.getStyleClass().removeAll("poljeNijeIspravno");
            tfPhoneNum.getStyleClass().add("poljeIspravno");
        } else {
            tfPhoneNum.getStyleClass().removeAll("poljeIspravno");
            tfPhoneNum.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        if (validateCity(tfCity.getText().trim())) {
            tfCity.getStyleClass().removeAll("poljeNijeIspravno");
            tfCity.getStyleClass().add("poljeIspravno");
        } else {
            tfCity.getStyleClass().removeAll("poljeIspravno");
            tfCity.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        if (validateAddress(tfAddress.getText().trim())) {
            tfAddress.getStyleClass().removeAll("poljeNijeIspravno");
            tfAddress.getStyleClass().add("poljeIspravno");
        } else {
            tfAddress.getStyleClass().removeAll("poljeIspravno");
            tfAddress.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }
        if (validateBirthDate(tfBirthDate.getText().trim())) {
            tfBirthDate.getStyleClass().removeAll("poljeNijeIspravno");
            tfBirthDate.getStyleClass().add("poljeIspravno");
        } else {
            tfBirthDate.getStyleClass().removeAll("poljeIspravno");
            tfBirthDate.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        }

//        if (!sveOk){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error Dialog");
//            alert.setHeaderText("Ooops, there was an error!");
//            alert.setContentText("Check information format!");
//            alert.showAndWait();
//        }
//        if (pacijent == null) pacijent = new Patients();
//        pacijent = new Patients(Integer.parseInt(tfMedicalRN.getText()), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), tfBirthDate.getText());


        if (!sveOk) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Ooops, there was an error!");
            alert.setContentText("Check information format!");
            alert.showAndWait();
            return;
        } else {
            if (pacijent == null) {
                pacijent = new Patients(Integer.parseInt(tfMedicalRN.getText()), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), tfBirthDate.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully added a patient");
                alert.showAndWait();
            } else {
                pacijent = new Patients(pacijent.getMedicalRecordNumber(), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), tfBirthDate.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Changes saved!");
                alert.showAndWait();
            }
            Stage stage = (Stage) tfFullName.getScene().getWindow();
            stage.close();
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfFullName.getScene().getWindow();
        stage.close();
    }

    public boolean validateFullName(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < 65 && str.charAt(i) != 32) return false;
            if(str.charAt(i) > 90 && str.charAt(i) < 97) return false;
            if(str.charAt(i) > 122) return false;
        }
        if(str.length() < 5) return false;
        return true;
    }

    public boolean validateMedicalRN(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        }
        if(str.length() < 1) return false;
        return true;
    }

    public boolean validatePhoneNum(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        }
        if(str.length() < 6) return false;
        return true;
    }

    public boolean validateCity(String str){
        if(str.length() < 3) return false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < 'A' || str.charAt(i) > 'Z' && str.charAt(i) < 'a' || str.charAt(i) > 'z') return false;
        }
        return true;
    }

    public boolean validateAddress(String str){
        if(str.length() < 3) return false;
        for(int i = 0; i < str.length(); i++){
            if( (str.charAt(i) < '0' || str.charAt(i) > '9') && (str.charAt(i) < 'A' || str.charAt(i) > 'Z') && (str.charAt(i) < 'a' || str.charAt(i) > 'z') && str.charAt(i) != 32) return false;
        }
        return true;
    }

    public boolean validateBirthDate(String str){
        int brojacCrtica = 0;
        if(str.charAt(0) == '0' && str.charAt(1) == '0') return false;
        else if(str.charAt(3) == '0' && str.charAt(4) == '0') return false;
        else if(str.charAt(3) == '1' && str.charAt(4) > '2') return false;
        if(str.charAt(0) == '3' && str.charAt(1) > '1') return false;
        if(str.charAt(2) != '-' && str.charAt(5) != '-') return false;
        if(str.charAt(0) > '3') return false;
        if(str.charAt(3) > '2') return false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '-') brojacCrtica++;
        }
        if(brojacCrtica != 2) return false;
        return true;
    }
}