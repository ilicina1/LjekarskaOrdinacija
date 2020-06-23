package sample;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;

public class ShowXrayController {
    public Image image;
    public ImageView imgView;
    public AnchorPane anchor;

    public ShowXrayController(Image image) {
        this.image = image;
    }

    public void actionSet(ActionEvent actionEvent) throws IOException, SQLException {
        imgView.setImage(image);
        imgView.setPreserveRatio(true);
        imgView.fitHeightProperty().bind(anchor.heightProperty());
        imgView.fitWidthProperty().bind(anchor.widthProperty());
    }
}
