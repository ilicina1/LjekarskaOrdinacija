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

public class MedicalReportsController {
    public AnchorPane anchor;
    public TableView<MedicalReports> tableViewMedicalReports;
    public TableColumn colId;
    public TableColumn colDate;

    public ClassDAO dao;
    public Patients pacijent;
    private ObservableList<MedicalReports> listMedicalReports;

    public MedicalReportsController(Patients pacijent) throws SQLException {
        dao = ClassDAO.getInstance();
        this.pacijent = pacijent;
        listMedicalReports = FXCollections.observableArrayList(dao.reports(pacijent.getMedicalRecordNumber()));
    }

    @FXML
    public void initialize() {
        tableViewMedicalReports.setItems(listMedicalReports);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addReport.fxml"));
            AddMedicalReportController ctrl = new AddMedicalReportController(pacijent);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Create medical report");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                MedicalReports noviNalaz = ctrl.getReport();
                if (noviNalaz != null) {
                    dao.addReport(noviNalaz);
                    try {
                        listMedicalReports.setAll(dao.reports(pacijent.getMedicalRecordNumber()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionDelete(ActionEvent actionEvent) throws SQLException {
        MedicalReports nalaz = tableViewMedicalReports.getSelectionModel().getSelectedItem();

        if (nalaz == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Brisanje nalaza " + nalaz.getId());
        alert.setContentText("Da li ste sigurni da želite obrisati nalaz " + nalaz.getId()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteReport(nalaz);
            dao.deleteResults(nalaz);
            listMedicalReports.setAll(dao.reports(pacijent.getMedicalRecordNumber()));
        }
    }


    public void actionResults(ActionEvent actionEvent) throws IOException, SQLException {
        MedicalReports report = tableViewMedicalReports.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/result.fxml"));
            ResultsController ctrl = new ResultsController(report, pacijent);
            loader.setController(ctrl);
            stage.setTitle("Results");
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.translateYProperty().set(-533);

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
