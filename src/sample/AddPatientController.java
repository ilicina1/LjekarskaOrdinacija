package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class AddPatientController {
    public TextField tfMedicalRN;
    public TextField tfFullName;
    public TextField tfPhoneNum;
    public TextField tfCity;
    public TextField tfAddress;
    public DatePicker dpDatePicker;

    private Patients patient;
    private ObservableList<Patients> listPatients;

    public AddPatientController(Patients patient, ArrayList<Patients> patients) {
        this.patient = patient;
        listPatients = FXCollections.observableArrayList(patients);
    }

    @FXML
    public void initialize() {
        if(patient != null){
            tfMedicalRN.setText(Integer.toString(patient.getMedicalRecordNumber()));
            tfMedicalRN.setDisable(true);
            tfFullName.setText(patient.getFullName());
            tfPhoneNum.setText(patient.getPhoneNumber());
            tfCity.setText(patient.getCity());
            tfAddress.setText(patient.getAddress());
            dpDatePicker.setValue(patient.getBirthDate());
        }else {
            tfMedicalRN.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateMedicalRN(newIme)) {
                    tfMedicalRN.getStyleClass().removeAll("fieldIncorrect");
                    tfMedicalRN.getStyleClass().add("fieldCorrect");
                } else {
                    tfMedicalRN.getStyleClass().removeAll("fieldCorrect");
                    tfMedicalRN.getStyleClass().add("fieldIncorrect");
                }
            });

            tfFullName.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateFullName(newIme)) {
                    tfFullName.getStyleClass().removeAll("fieldIncorrect");
                    tfFullName.getStyleClass().add("fieldCorrect");
                } else {
                    tfFullName.getStyleClass().removeAll("fieldCorrect");
                    tfFullName.getStyleClass().add("fieldIncorrect");
                }
            });

            tfPhoneNum.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validatePhoneNum(newIme)) {
                    tfPhoneNum.getStyleClass().removeAll("fieldIncorrect");
                    tfPhoneNum.getStyleClass().add("fieldCorrect");
                } else {
                    tfPhoneNum.getStyleClass().removeAll("fieldCorrect");
                    tfPhoneNum.getStyleClass().add("fieldIncorrect");
                }
            });

            tfCity.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateCity(newIme)) {
                    tfCity.getStyleClass().removeAll("fieldIncorrect");
                    tfCity.getStyleClass().add("fieldCorrect");
                } else {
                    tfCity.getStyleClass().removeAll("fieldCorrect");
                    tfCity.getStyleClass().add("fieldIncorrect");
                }
            });

            tfAddress.textProperty().addListener((obs, oldIme, newIme) -> {
                if (validateAddress(newIme)) {
                    tfAddress.getStyleClass().removeAll("fieldIncorrect");
                    tfAddress.getStyleClass().add("fieldCorrect");
                } else {
                    tfAddress.getStyleClass().removeAll("fieldCorrect");
                    tfAddress.getStyleClass().add("fieldIncorrect");
                }
            });
        }
        tfMedicalRN.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateMedicalRN(newIme)) {
                tfMedicalRN.getStyleClass().removeAll("fieldIncorrect");
                tfMedicalRN.getStyleClass().add("fieldCorrect");
            } else {
                tfMedicalRN.getStyleClass().removeAll("fieldCorrect");
                tfMedicalRN.getStyleClass().add("fieldIncorrect");
            }
        });

        tfFullName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateFullName(newIme)) {
                tfFullName.getStyleClass().removeAll("fieldIncorrect");
                tfFullName.getStyleClass().add("fieldCorrect");
            } else {
                tfFullName.getStyleClass().removeAll("fieldCorrect");
                tfFullName.getStyleClass().add("fieldIncorrect");
            }
        });

        tfPhoneNum.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validatePhoneNum(newIme)) {
                tfPhoneNum.getStyleClass().removeAll("fieldIncorrect");
                tfPhoneNum.getStyleClass().add("fieldCorrect");
            } else {
                tfPhoneNum.getStyleClass().removeAll("fieldCorrect");
                tfPhoneNum.getStyleClass().add("fieldIncorrect");
            }
        });

        tfCity.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateCity(newIme)) {
                tfCity.getStyleClass().removeAll("fieldIncorrect");
                tfCity.getStyleClass().add("fieldCorrect");
            } else {
                tfCity.getStyleClass().removeAll("fieldCorrect");
                tfCity.getStyleClass().add("fieldIncorrect");
            }
        });

        tfAddress.textProperty().addListener((obs, oldIme, newIme) -> {
            if (validateAddress(newIme)) {
                tfAddress.getStyleClass().removeAll("fieldIncorrect");
                tfAddress.getStyleClass().add("fieldCorrect");
            } else {
                tfAddress.getStyleClass().removeAll("fieldCorrect");
                tfAddress.getStyleClass().add("fieldIncorrect");
            }
        });
    }

    public Patients getPacijent() {
        return patient;
    }

    public void actionConfirm(ActionEvent actionEvent) throws IOException, SQLException {
        boolean allOk = true;

        if (validateMedicalRN(tfMedicalRN.getText().trim())) {
            tfMedicalRN.getStyleClass().removeAll("fieldIncorrect");
            tfMedicalRN.getStyleClass().add("fieldCorrect");
        } else {
            tfMedicalRN.getStyleClass().removeAll("fieldCorrect");
            tfMedicalRN.getStyleClass().add("fieldIncorrect");
            allOk = false;
        }
        if (validateFullName(tfFullName.getText())) {
            tfFullName.getStyleClass().removeAll("fieldIncorrect");
            tfFullName.getStyleClass().add("fieldCorrect");
        } else {
            tfFullName.getStyleClass().removeAll("fieldCorrect");
            tfFullName.getStyleClass().add("fieldIncorrect");
            allOk = false;
        }
        if (validatePhoneNum(tfPhoneNum.getText().trim())) {
            tfPhoneNum.getStyleClass().removeAll("fieldIncorrect");
            tfPhoneNum.getStyleClass().add("fieldCorrect");
        } else {
            tfPhoneNum.getStyleClass().removeAll("fieldCorrect");
            tfPhoneNum.getStyleClass().add("fieldIncorrect");
            allOk = false;
        }
        if (validateCity(tfCity.getText().trim())) {
            tfCity.getStyleClass().removeAll("fieldIncorrect");
            tfCity.getStyleClass().add("fieldCorrect");
        } else {
            tfCity.getStyleClass().removeAll("fieldCorrect");
            tfCity.getStyleClass().add("fieldIncorrect");
            allOk = false;
        }
        if (validateAddress(tfAddress.getText().trim())) {
            tfAddress.getStyleClass().removeAll("fieldIncorrect");
            tfAddress.getStyleClass().add("fieldCorrect");
        } else {
            tfAddress.getStyleClass().removeAll("fieldCorrect");
            tfAddress.getStyleClass().add("fieldIncorrect");
            allOk = false;
        }

        if(patient == null) {
            for (int i = 0; i < listPatients.size(); i++) {
                if (listPatients.get(i).getMedicalRecordNumber() == Integer.parseInt(tfMedicalRN.getText()))
                    allOk = false;
            }
        }
        if (!allOk) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Ooops, there was an error!");
            alert.setContentText("Check information format!");
            alert.showAndWait();

            return;
        } else {
            if (patient == null) {
                patient = new Patients(Integer.parseInt(tfMedicalRN.getText()), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), dpDatePicker.getValue());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully added a patient");
                alert.showAndWait();
            } else {
                patient = new Patients(patient.getMedicalRecordNumber(), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), dpDatePicker.getValue());

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
        int dashCounter = 0;
        boolean correct = true;
        if(str.charAt(0) == '0' && str.charAt(1) == '0') correct = false;
        if(str.charAt(3) == '0' && str.charAt(4) == '0') correct = false;
        if(str.charAt(3) == '1' && str.charAt(4) > '2') correct = false;
        if(str.charAt(0) == '3' && str.charAt(1) > '1') correct = false;
        if(str.charAt(2) != '-' && str.charAt(5) != '-') correct = false;
        if(str.charAt(0) > '3') correct = false;
        if(str.charAt(3) > '2') correct = false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '-') dashCounter++;
        }
        if(dashCounter != 2 || correct == false) return false;
        return true;
    }
}