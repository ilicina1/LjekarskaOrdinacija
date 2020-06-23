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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class ResultsController {
    public TableView<Results> tableViewResults;
    public TableColumn colSample;
    public TableColumn colTypeOfAnalysis;
    public TableColumn colResult;
    public TableColumn colNormalValue;
    public AnchorPane anchor;

    private Patients pacijent;
    private ClassDAO dao;
    private MedicalReports report;
    private ObservableList<Results> listResults;

    public ResultsController(MedicalReports report, Patients pacijent) throws SQLException {
        dao = ClassDAO.getInstance();
        this.report = report;
        listResults = FXCollections.observableArrayList(dao.results(report.getId()));
        this.pacijent = pacijent;
    }

    @FXML
    public void initialize() {
        tableViewResults.setItems(listResults);
        colSample.setCellValueFactory(new PropertyValueFactory("sample"));
        colTypeOfAnalysis.setCellValueFactory(new PropertyValueFactory("typeOfAnalysis"));
        colResult.setCellValueFactory(new PropertyValueFactory("result"));
        colNormalValue.setCellValueFactory(new PropertyValueFactory("normalValue"));

    }

    public void actionBack(ActionEvent actionEvent) throws SQLException {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medicalReports.fxml"));
            MedicalReportsController ctrl = new MedicalReportsController(pacijent);
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Medical Reports");
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

    public void actionDelete(ActionEvent actionEvent) throws SQLException {
        Results rezultat = tableViewResults.getSelectionModel().getSelectedItem();

        if (rezultat == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Deleting a result " + rezultat.getId());
        alert.setContentText("Are you sure you want to delete result " + rezultat.getId()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteResult(rezultat);
            listResults.setAll(dao.results(report.getId()));
        }
    }

    public void actionEdit(ActionEvent actionEvent) throws IOException, SQLException {
        Results rezultat = tableViewResults.getSelectionModel().getSelectedItem();
        if (rezultat == null) return;
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addResults.fxml"));
            EditResultController ctrl = new EditResultController(rezultat, report);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Edit result");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                try {
                    listResults.setAll(dao.results(report.getId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
