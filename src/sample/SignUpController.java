package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SignUpController {
    public TextField tfFirstName;
    public TextField tfLastName;
    public TextField tfUserName;
    public TextField tfEmail;
    public PasswordField pfPassword1;
    public PasswordField PfPassword2;
    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfUserName.getScene().getWindow();
        stage.close();
    }
}
