package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController  {
    public TextField tfFirstName;
    public TextField tfLastName;
    public TextField tfUserName;
    public TextField tfEmail;
    public PasswordField pfPassword1;
    public PasswordField pfPassword2;
    public Button btnCancel;
    public Button btnContinue;
    public AnchorPane anchorSign;

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

    public void actionCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            LoginController ctrl = new LoginController();
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.translateYProperty().set(454);

        anchorSign.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.45), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            anchorSign.getChildren().remove(anchorSign);
        });
        timeline.play();
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
