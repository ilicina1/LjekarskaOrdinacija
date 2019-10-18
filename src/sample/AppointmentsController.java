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
import java.util.Optional;

public class AppointmentsController {
    public TableView<Appointments> tableViewAppointments;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colTime;
    public TableColumn colReason;
    public AnchorPane anchor2;

    public KlasaDAO dao;
    private ObservableList<Appointments> listAppointments;
    private ObservableList<Appointments> listAppointmentsFinal;


    public AppointmentsController() {
        dao = KlasaDAO.getInstance();
        listAppointments = FXCollections.observableArrayList(dao.appointments());
    }

    @FXML
    public void initialize() {
        listAppointmentsFinal = inputTodaysAppointments(listAppointments);
        tableViewAppointments.setItems(listAppointmentsFinal);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("nameAndSurname"));
        colTime.setCellValueFactory(new PropertyValueFactory("time"));
        colReason.setCellValueFactory(new PropertyValueFactory("reason"));
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
            dao.obrisiAppointment(appointment);
            listAppointmentsFinal.setAll(dao.appointments());
        }
    }


    public ObservableList<Appointments> inputTodaysAppointments(ObservableList<Appointments> listAppointments){
        LocalDate today = LocalDate.now();
        ObservableList<Appointments> result = FXCollections.observableArrayList();
        for(int i = 0; i < listAppointments.size(); i++){
            if(today.isEqual(listAppointments.get(i).getDate())){
                result.add(listAppointments.get(i));
            }
            if(today.isAfter(listAppointments.get(i).getDate())){
                dao.obrisiAppointment(listAppointments.get(i));
            }
        }
        return result;
    }

}
