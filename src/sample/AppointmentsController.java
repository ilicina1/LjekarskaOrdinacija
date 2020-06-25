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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AppointmentsController {
    public TableView<Appointments> tableViewAppointments;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTime;
    public TableColumn colReason;
    public TableColumn colDate;
    public AnchorPane anchor2;
    public DatePicker searchBox;

    private ClassDAOBase dao;
    private ObservableList<Appointments> listAppointments;
    private ObservableList<Appointments> filteredData = FXCollections.observableArrayList();


    public AppointmentsController() {
        dao = ClassDAOBase.getInstance();
        listAppointments = FXCollections.observableArrayList(dao.appointments());
        filteredData.addAll(listAppointments);
    }

    @FXML
    public void initialize() {
        tableViewAppointments.setItems(filteredData);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("nameAndSurname"));
        colTime.setCellValueFactory(new PropertyValueFactory("time"));
        colReason.setCellValueFactory(new PropertyValueFactory("reason"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        searchBox.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                                LocalDate oldValue, LocalDate newValue) {

                updateFilteredData();
            }
        });
    }


    private void updateFilteredData() {
        filteredData.clear();

        for (Appointments p : listAppointments) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }

    private boolean matchesFilter(Appointments appointments) {
        LocalDate filterDate = searchBox.getValue();
        if (filterDate == null) {
            // No filter --> Add all.
            return true;
        }

        if (filterDate.isEqual(appointments.getDate())) {
            return true;
        }

        return false; // Does not match
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Appointments, ?>> sortOrder = new ArrayList<>(tableViewAppointments.getSortOrder());
        tableViewAppointments.getSortOrder().clear();
        tableViewAppointments.getSortOrder().addAll(sortOrder);
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


    public void actionDelete(ActionEvent actionEvent) throws SQLException {
        Appointments appointment = tableViewAppointments.getSelectionModel().getSelectedItem();

        if (appointment == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Deleting an appointment " + appointment.getId());
        alert.setContentText("Are you sure you want to delete the appointment " + appointment.getId()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dao.deleteAppointment(appointment);
            listAppointments = FXCollections.observableArrayList(dao.appointments());
            filteredData.addAll(listAppointments);
            updateFilteredData();
        }
    }

    public void actionNew(ActionEvent actionEvent){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addAppointment.fxml"));
            AddAppointmentController ctrl = new AddAppointmentController();
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Create an appointment");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
           stage.setOnHiding( event -> {
               listAppointments = FXCollections.observableArrayList(dao.appointments());
               filteredData.addAll(listAppointments);
               updateFilteredData();
           } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
