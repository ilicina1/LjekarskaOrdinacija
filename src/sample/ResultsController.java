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

public class ResultsController {
    public TableView<Results> tableViewResults;
    public TableColumn colSample;
    public TableColumn colTypeOfAnalysis;
    public TableColumn colResult;
    public TableColumn colNormalValue;
    public AnchorPane anchor;

    public KlasaDAO dao;
    public MedicalReports report;
    private ObservableList<Results> listResults;

    public ResultsController(MedicalReports report) throws SQLException {
        dao = KlasaDAO.getInstance();
        this.report = report;
        listResults = FXCollections.observableArrayList(dao.rezultati(report.getId()));
    }

    @FXML
    public void initialize() {
        tableViewResults.setItems(listResults);
        colSample.setCellValueFactory(new PropertyValueFactory("sample"));
        colTypeOfAnalysis.setCellValueFactory(new PropertyValueFactory("typeOfAnalysis"));
        colResult.setCellValueFactory(new PropertyValueFactory("result"));
        colNormalValue.setCellValueFactory(new PropertyValueFactory("normalValue"));

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
}
