package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class XraysController {
    public AnchorPane anchor;
    public TableView<Xray> tableViewXrays;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colWhatsOnRay;

    public KlasaDAO dao;
    private Patients pacijent;
    private ObservableList<Xray> listXrays;

    public XraysController(Patients pacijent) throws SQLException {
        dao = KlasaDAO.getInstance();
        this.pacijent = pacijent;
        listXrays = FXCollections.observableArrayList(dao.xrays(pacijent.getMedicalRecordNumber()));
    }

    @FXML
    public void initialize() {
        tableViewXrays.setItems(listXrays);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colWhatsOnRay.setCellValueFactory(new PropertyValueFactory("whatsOnRay"));
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

    public void actionOpen(ActionEvent actionEvent) throws IOException, SQLException {
        Xray xray = tableViewXrays.getSelectionModel().getSelectedItem();
        Image image = dao.dajSliku(xray.getId());
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showXray.fxml"));
            showXrayController ctrl = new showXrayController(image);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("X-ray");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
//            stage.setResizable(false);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
