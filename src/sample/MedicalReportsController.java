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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class MedicalReportsController {
    public AnchorPane anchor;
    public Patients pacijent;

    public KlasaDAO dao;
    private ObservableList<MedicalReports> listMedicalReports;

    public MedicalReportsController(Patients pacijent) throws SQLException {
        dao = KlasaDAO.getInstance();
        this.pacijent = pacijent;
        listMedicalReports = FXCollections.observableArrayList(dao.nalazi(pacijent.getMedicalRecordNumber()));
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
            AddMedicalReportController ctrl = new AddMedicalReportController(dao.nalazi(pacijent.getMedicalRecordNumber()), pacijent);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Create medical report");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                MedicalReports noviNalaz = ctrl.getReport();
                if (noviNalaz == null) {
                    dao.dodajNalaz(noviNalaz);
                    try {
                        listMedicalReports.setAll(dao.nalazi(pacijent.getMedicalRecordNumber()));
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
}
