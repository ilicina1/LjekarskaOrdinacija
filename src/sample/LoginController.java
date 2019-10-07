package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public Button btnExit;
    public Button btnSignUp;
    public Button btnForgot;
    public Button btnLogin;
    public TextField tfUserName;
    public PasswordField pfPassword;
    public ImageView imgLogin;
    public KlasaDAO dao;

    public AnchorPane anchor;
    public StackPane stackPane;

    public LoginController() {
        dao = KlasaDAO.getInstance();
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
        KlasaDAO jdbcDao = new KlasaDAO();
        boolean flag = jdbcDao.validateUserName("patka");
        if(flag == false) System.out.println("Ne postoji mail u bazi");
        else System.out.println("Postoji mail u bazi");
    }
}
