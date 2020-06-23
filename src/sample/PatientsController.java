package sample;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public TextField searchBox;
    public AnchorPane anchor;

    public ClassDAO dao;
    private ObservableList<Patients> listPatients;
    private ObservableList<Patients> filteredData = FXCollections.observableArrayList();


    public PatientsController() {
        dao = ClassDAO.getInstance();
        listPatients = FXCollections.observableArrayList(dao.patients());
        filteredData.addAll(listPatients);
    }

    @FXML
    public void initialize() {
        tableViewPacijenti.setItems(filteredData);
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                updateFilteredData();
            }
        });
        colMedicalRN.setCellValueFactory(new PropertyValueFactory("medicalRecordNumber"));
        colFullName.setCellValueFactory(new PropertyValueFactory("fullName"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colCity.setCellValueFactory(new PropertyValueFactory("city"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
    }

    private void updateFilteredData() {
        filteredData.clear();

        for (Patients p : listPatients) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Patients patient) {
        String filterString = searchBox.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (patient.getFullName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (patient.getFullName().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Patients, ?>> sortOrder = new ArrayList<>(tableViewPacijenti.getSortOrder());
        tableViewPacijenti.getSortOrder().clear();
        tableViewPacijenti.getSortOrder().addAll(sortOrder);
    }

    public void actionNew(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajPacijenta.fxml"));
            AddPatientController ctrl = new AddPatientController(null, dao.patients());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("New patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.addPatient(noviPacijent);
                    listPatients.setAll(dao.patients());
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
            AddPatientController ctrl = new AddPatientController(pacijent, dao.patients());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Edit patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.changePatient(noviPacijent);
                    listPatients.setAll(dao.patients());
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
            dao.deletePatient(pacijent);
            listPatients.setAll(dao.patients());
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
