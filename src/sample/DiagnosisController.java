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

public class DiagnosisController {
    public TableView<Diagnosis> tableViewDiagnosis;
    public TableColumn colId;
    public TableColumn colDiagnosis;
    public AnchorPane anchor2;
    private Patients pacijent;
    private ClassDAO dao;
    private ObservableList<Diagnosis> listDiagnoses;

    public DiagnosisController(Patients pacijent) throws SQLException {
        dao = ClassDAO.getInstance();
        listDiagnoses = FXCollections.observableArrayList(dao.diagnosis(pacijent.getMedicalRecordNumber()));
        this.pacijent = pacijent;
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

    public void actionNew(ActionEvent actionEvent){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajDijagnozu.fxml"));
            AddDiagnosisController ctrl = new AddDiagnosisController(null, pacijent);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("New diagnosis");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Diagnosis novaDijagnoza = ctrl.getDijagnoza();
                if (novaDijagnoza != null) {
                    dao.addDiagnosis(novaDijagnoza);
                    try {
                        listDiagnoses.setAll(dao.diagnosis(pacijent.getMedicalRecordNumber()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDelete(ActionEvent actionEvent) throws SQLException {
        Diagnosis dijagnoza = tableViewDiagnosis.getSelectionModel().getSelectedItem();

        if (dijagnoza == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Brisanje dijagnoze " + dijagnoza.getId());
        alert.setContentText("Da li ste sigurni da želite obrisati dijagnozu " + dijagnoza.getId()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteDiagnosis(dijagnoza);
            listDiagnoses.setAll(dao.diagnosis(pacijent.getMedicalRecordNumber()));
        }
    }
}
