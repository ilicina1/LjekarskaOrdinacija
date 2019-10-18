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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_PREF_SIZE;


public class PatientsController {
    public TableView<Patients> tableViewPacijenti;
    public TableColumn colMedicalRN;
    public TableColumn colFullName;
    public TableColumn colPhoneNum;
    public TableColumn colCity;
    public TableColumn colAddress;
    public TableColumn colBirthDate;
    public AnchorPane anchor;

    public KlasaDAO dao;
    private ObservableList<Patients> listPacijenti;

    public PatientsController() {
        dao = KlasaDAO.getInstance();
        listPacijenti = FXCollections.observableArrayList(dao.pacijenti());
    }

    @FXML
    public void initialize() {
        tableViewPacijenti.setItems(listPacijenti);
        colMedicalRN.setCellValueFactory(new PropertyValueFactory("medicalRecordNumber"));
        colFullName.setCellValueFactory(new PropertyValueFactory("fullName"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colCity.setCellValueFactory(new PropertyValueFactory("city"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
    }

    public void actionNew(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajPacijenta.fxml"));
            dodajPacijentaController ctrl = new dodajPacijentaController(null, dao.pacijenti());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("New patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.dodajPacijenta(noviPacijent);
                    listPacijenti.setAll(dao.pacijenti());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionEdit(ActionEvent actionEvent) throws IOException, SQLException {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();
        if (pacijent == null) return;
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajPacijenta.fxml"));
            dodajPacijentaController ctrl = new dodajPacijentaController(pacijent, dao.pacijenti());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Edit patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.izmijeniPacijenta(noviPacijent);
                    listPacijenti.setAll(dao.pacijenti());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionDelete(ActionEvent actionEvent) {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();

        if (pacijent == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Brisanje pacijenta " + pacijent.getFullName());
        alert.setContentText("Da li ste sigurni da želite obrisati pacijenta " + pacijent.getFullName()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.obrisiPacijenta(pacijent);
//            dao.obrisiSveDijagnoze(pacijent);
//            dao.obrisiSveHistorije(pacijent);
//            dao.obrisiSveNalaze(pacijent);

            listPacijenti.setAll(dao.pacijenti());
        }
    }

    public void actionDiagnosis(ActionEvent actionEvent) throws IOException, SQLException {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/diagnosis.fxml"));
            DiagnosisController ctrl = new DiagnosisController(pacijent);
            loader.setController(ctrl);
            stage.setTitle("Diagnosis");
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

    public void actionMedicalHistory(ActionEvent actionEvent) throws IOException, SQLException {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medicalHistory.fxml"));
            MedicalHistoryController ctrl = new MedicalHistoryController(pacijent);
            loader.setController(ctrl);
            stage.setTitle("Medical History");
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

    public void actionMedicalReports(ActionEvent actionEvent) throws IOException, SQLException {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/medicalReports.fxml"));
            MedicalReportsController ctrl = new MedicalReportsController(pacijent);
            loader.setController(ctrl);
            stage.setTitle("Medical Findings");
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

    public void actionAppointments(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/appointments.fxml"));
            AppointmentsController ctrl = new AppointmentsController();
            loader.setController(ctrl);
            stage.setTitle("Appointments");
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

    public void actionXrays(ActionEvent actionEvent) throws IOException, SQLException {
        Patients pacijent = tableViewPacijenti.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/xrays.fxml"));
            XraysController ctrl = new XraysController(pacijent);
            loader.setController(ctrl);
            stage.setTitle("X-rays");
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
