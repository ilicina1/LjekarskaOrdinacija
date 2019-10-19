package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class AddAppointmentController {
    public TextField tfNameAndSurname;
    public TextField tfTime;
    public TextField tfReason;
    public DatePicker dpDate;

    public KlasaDAO dao;
    public Appointments appointment;

    public AddAppointmentController() {
        dao = KlasaDAO.getInstance();
    }

    public Appointments getAppointment() {
        return appointment;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfNameAndSurname.getScene().getWindow();
        stage.close();
    }

    public void actionConfirm(ActionEvent actionEvent) throws SQLException {
        appointment = null;
        appointment = new Appointments(dao.dajNajveciId4() + 1 , tfNameAndSurname.getText(), tfTime.getText(),tfReason.getText(), dpDate.getValue());
        dao.dodajAppointment(appointment);
        ButtonType Yes = new ButtonType("Yes");
        ButtonType No = new ButtonType("No");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Would you like to add another one? \n Press Yes if you do!", Yes, No);
        alert.setTitle("");
        alert.setHeaderText("You have successfully added an appointment!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == Yes){
            tfNameAndSurname.setText("");
            tfTime.setText("");
            tfReason.setText("");
            tfNameAndSurname.getStyleClass().removeAll("poljeNijeIspravno");
            tfNameAndSurname.getStyleClass().removeAll("poljeIspravno");
            tfTime.getStyleClass().removeAll("poljeNijeIspravno");
            tfTime.getStyleClass().removeAll("poljeIspravno");
            tfReason.getStyleClass().removeAll("poljeNijeIspravno");
            tfReason.getStyleClass().removeAll("poljeIspravno");
        } else {
            Stage stage = (Stage) tfNameAndSurname.getScene().getWindow();
            stage.close();
        }
    }
}
