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

public class MedicalHistoryController {
    public TableView<MedicalHistory> tableViewMedicalHistory;
    public TableColumn colAllergies;
    public TableColumn colFamilyIssues;
    public TableColumn colAddictions;
    public TableColumn colCurrentIssues;
    public AnchorPane anchor2;

    private ObservableList<MedicalHistory> listMedicalHistory;
    private Patients patient;
    private ClassDAOBase dao;

    public MedicalHistoryController(Patients patient) throws SQLException {
        dao = ClassDAOBase.getInstance();
        this.patient = patient;
        listMedicalHistory = FXCollections.observableArrayList(dao.history(patient.getMedicalRecordNumber()));
    }

    @FXML
    public void initialize() {
        tableViewMedicalHistory.setItems(listMedicalHistory);
        colAllergies.setCellValueFactory(new PropertyValueFactory("allergies"));
        colFamilyIssues.setCellValueFactory(new PropertyValueFactory("familyMedicalIssues"));
        colAddictions.setCellValueFactory(new PropertyValueFactory("addictions"));
        colCurrentIssues.setCellValueFactory(new PropertyValueFactory("currentHealthIssues"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addHistory.fxml"));
            AddHistoryController ctrl = new AddHistoryController(patient);
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("New medical history");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                MedicalHistory novaHistorija = ctrl.getHistory();
                if (novaHistorija != null) {
                    dao.addHistory(novaHistorija);
                    try {
                        listMedicalHistory.setAll(dao.history(patient.getMedicalRecordNumber()));
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
        MedicalHistory history = tableViewMedicalHistory.getSelectionModel().getSelectedItem();

        if (history == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Deleting history " + history.getId());
        alert.setContentText("Are us sure that u want to delete history: " + history.getId()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteHistory(history);
            listMedicalHistory.setAll(dao.history(patient.getMedicalRecordNumber()));
        }
    }
}
