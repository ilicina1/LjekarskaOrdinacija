package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {
    public Button btnExit;
    public Button btnSignUp;
    public Button btnForgot;
    public Button btnLogin;
    public TextField tfUserName;
    public PasswordField pfPassword;
    public ImageView imgLogin;
    public KlasaDAO dao;

    public LoginController() {
        dao = KlasaDAO.getInstance();
        //listGradovi = FXCollections.observableArrayList(dao.gradovi());
    }

    public void actionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) tfUserName.getScene().getWindow();
        stage.close();
    }


    public void actionSignUp(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            //SignUpController signUpController = new SignUpController();
           // loader.setController(signUpController);
            root = loader.load();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();

//            stage.setOnHiding( event -> {
//                Grad grad = gradController.getGrad();
//                if (grad != null) {
//                    dao.dodajGrad(grad);
//                    listGradovi.setAll(dao.gradovi());
//                }
//            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
