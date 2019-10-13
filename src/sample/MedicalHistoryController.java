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

public class MedicalHistoryController {
    public TableView<MedicalHistory> tableViewMedicalHistory;
    public TableColumn colAllergies;
    public TableColumn colFamilyIssues;
    public TableColumn colAddictions;
    public TableColumn colCurrentIssues;
    public AnchorPane anchor2;

    private ObservableList<MedicalHistory> listMedicalHistory;
    private Patients pacijent;
    public KlasaDAO dao;

    public MedicalHistoryController(Patients pacijent) throws SQLException {
        dao = KlasaDAO.getInstance();
        this.pacijent = pacijent;
        listMedicalHistory = FXCollections.observableArrayList(dao.historije(pacijent.getMedicalRecordNumber()));
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
}
