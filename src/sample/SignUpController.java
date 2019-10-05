package sample;

import javafx.event.ActionEvent;
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
    public PasswordField PfPassword2;

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

}
