package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {
    public Button btnExit;
    public Button btnSignUp;
    public Button btnForgot;
    public Button btnLogin;
    public TextField tfUserName;
    public PasswordField pfPassword;
    public ImageView imgLogin;

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfUserName.getScene().getWindow();
        stage.close();
    }

}
