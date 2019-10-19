package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class XraysController {
    private AnchorPane anchor;
    private TableView<Xray> tableViewXrays;
    private TableColumn colId;
    private TableColumn colDate;
    private TableColumn colWhatsOnRay;

    private KlasaDAO dao;
    private Patients pacijent;
    private ObservableList<Xray> listXrays;

    public XraysController(Patients pacijent) throws SQLException {
        dao = KlasaDAO.getInstance();
        this.pacijent = pacijent;
        listXrays = FXCollections.observableArrayList(dao.xrays(pacijent.getMedicalRecordNumber()));
    }

    public void actionBack(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/patients.fxml"));
            PatientsController ctrl = new PatientsController();
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Patients");
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.translateYProperty().set(533);

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

    public void actionNew(ActionEvent actionEvent){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addXray.fxml"));
            AddXrayController ctrl = new AddXrayController(pacijent);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Add x-ray");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                try {
                    listXrays.setAll(dao.xrays(pacijent.getMedicalRecordNumber()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
