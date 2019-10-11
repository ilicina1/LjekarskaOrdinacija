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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiagnosisController {
    public TableView tableViewDiagnosis;
    public TableColumn colId;
    public TableColumn colDiagnosis;
    public AnchorPane anchor2;

    public KlasaDAO dao;
    private ObservableList<Diagnosis> listDiagnoses;

    public DiagnosisController(Patients pacijent) throws SQLException {
        dao = KlasaDAO.getInstance();
        listDiagnoses = FXCollections.observableArrayList(dao.dijagnoze(pacijent.getMedicalRecordNumber()));
    }

    @FXML
    public void initialize() {
        tableViewDiagnosis.setItems(listDiagnoses);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDiagnosis.setCellValueFactory(new PropertyValueFactory("text"));
    }

    public void actionBackk(ActionEvent actionEvent) {
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

        anchor2.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.45), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            anchor2.getChildren().remove(anchor2);
        });
        timeline.play();
    }
}
