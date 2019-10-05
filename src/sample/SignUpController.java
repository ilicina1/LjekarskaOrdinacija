package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;


public class SignUpController {
    public TextField tfFirstName;
    public TextField tfLastName;
    public TextField tfUserName;
    public TextField tfEmail;
    public PasswordField pfPassword1;
    public PasswordField pfPassword2;

    @FXML
    public void initialize() {
        tfFirstName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriImeiPrezime(newIme)) {
                tfFirstName.getStyleClass().removeAll("poljeNijeIspravno");
                tfFirstName.getStyleClass().add("poljeIspravno");
            } else {
                tfFirstName.getStyleClass().removeAll("poljeIspravno");
                tfFirstName.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfLastName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriImeiPrezime(newIme)) {
                tfLastName.getStyleClass().removeAll("poljeNijeIspravno");
                tfLastName.getStyleClass().add("poljeIspravno");
            } else {
                tfLastName.getStyleClass().removeAll("poljeIspravno");
                tfLastName.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfUserName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriUserName(newIme)) {
                tfUserName.getStyleClass().removeAll("poljeNijeIspravno");
                tfUserName.getStyleClass().add("poljeIspravno");
            } else {
                tfUserName.getStyleClass().removeAll("poljeIspravno");
                tfUserName.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriEmail(newIme)) {
                tfEmail.getStyleClass().removeAll("poljeNijeIspravno");
                tfEmail.getStyleClass().add("poljeIspravno");
            } else {
                tfEmail.getStyleClass().removeAll("poljeIspravno");
                tfEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });

        pfPassword1.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriPassword(newIme) && newIme.equals(pfPassword2.getText())) {
                pfPassword1.getStyleClass().removeAll("poljeNijeIspravno");
                pfPassword1.getStyleClass().add("poljeIspravno");
                pfPassword2.getStyleClass().removeAll("poljeNijeIspravno");
                pfPassword2.getStyleClass().add("poljeIspravno");
            } else {
                pfPassword1.getStyleClass().removeAll("poljeIspravno");
                pfPassword1.getStyleClass().add("poljeNijeIspravno");
                pfPassword2.getStyleClass().removeAll("poljeIspravno");
                pfPassword2.getStyleClass().add("poljeNijeIspravno");
            }
        });

        pfPassword2.textProperty().addListener((obs, oldIme, newIme) -> {
            if (provjeriPassword(newIme) && newIme.equals(pfPassword1.getText())) {
                pfPassword1.getStyleClass().removeAll("poljeNijeIspravno");
                pfPassword1.getStyleClass().add("poljeIspravno");
                pfPassword2.getStyleClass().removeAll("poljeNijeIspravno");
                pfPassword2.getStyleClass().add("poljeIspravno");
            } else {
                pfPassword1.getStyleClass().removeAll("poljeIspravno");
                pfPassword1.getStyleClass().add("poljeNijeIspravno");
                pfPassword2.getStyleClass().removeAll("poljeIspravno");
                pfPassword2.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage1 = (Stage) tfUserName.getScene().getWindow();
        stage1.close();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            root = loader.load();
            stage.setTitle("Ljekarska Ordinacija");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean provjeriImeiPrezime(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) < 65) return false;
            if(str.charAt(i) > 90 && str.charAt(i) < 97) return false;
            if(str.charAt(i) > 122) return false;
        }
        if(str.length() < 2) return false;
        return true;
    }

    public boolean provjeriUserName(String str){
        if(str.length() < 5) return false;
        return true;
    }

    public boolean provjeriEmail(String str){
        if(str.length() < 5) return false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == '@') return true;
        }
        return false;
    }

    public boolean provjeriPassword(String str){
        boolean broj = false;
        boolean velikoSlovo = false;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9') broj = true;
            if(str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') velikoSlovo = true;
        }
        if(broj && velikoSlovo && str.length() >= 8) return true;
        return false;
    }
}
