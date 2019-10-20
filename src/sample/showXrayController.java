package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;


public class showXrayController {
    public Image image;
    public ImageView imgView;
    public showXrayController(Image image) {
        this.image = image;
    }

    public void actionSet(ActionEvent actionEvent) throws IOException, SQLException {
        imgView.setImage(image);
    }
}
