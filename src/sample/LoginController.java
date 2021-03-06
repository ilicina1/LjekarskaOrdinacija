package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class LoginController {
    public TextField tfUserName;
    public PasswordField pfPassword;
    public AnchorPane anchor;

    private ClassDAOBase dao;

    public LoginController() {
        dao = ClassDAOBase.getInstance();
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfUserName.getScene().getWindow();
        stage.close();
    }


    public void actionSignUp(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            SignUpController ctrl = new SignUpController();
            loader.setController(ctrl);
            stage.setTitle("SignUp");
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.translateYProperty().set(-454);

        anchor.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.45), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            anchor.getChildren().remove(anchor);
        });
        timeline.play();
    }

    public void actionLogin(ActionEvent actionEvent) throws IOException, SQLException {
        String password = dao.returnPassword(tfUserName.getText().trim());

        if (password.equals(pfPassword.getText().trim())){
            Stage stage1 = (Stage) tfUserName.getScene().getWindow();
            stage1.close();
            Stage stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patients.fxml"));
                PatientsController ctrl = new PatientsController();
                loader.setController(ctrl);
                Parent root = loader.load();
                stage.setTitle("Patients");
                stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The username or password is incorrect!");

            alert.showAndWait();
        }
    }
}
