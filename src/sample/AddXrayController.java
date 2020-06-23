package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.sql.SQLException;

public class AddXrayController {
    public TextField tfWhatsOnRay;
    public DatePicker dpDate;

    private ClassDAO dao;
    private Patients pacijent;
    private FileInputStream fis;
    private File file;

    public AddXrayController(Patients pacijent) {
        dao = ClassDAO.getInstance();
        this.pacijent = pacijent;
    }

    public void actionCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) tfWhatsOnRay.getScene().getWindow();
        stage.close();
    }

    public void actionChoose(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
    }

    public void actionConfirm(ActionEvent actionEvent) throws IOException, SQLException {
        if(file == null) {
            System.out.println("nevalja");
            return;
        }
        fis = new FileInputStream(file);
        Xray xray = new Xray(dao.getMaxId5() + 1, tfWhatsOnRay.getText(), dpDate.getValue(), pacijent);
        dao.addXray(xray, fis, (int)file.length());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully added an x-ray!");
        alert.showAndWait();

        Stage stage = (Stage) tfWhatsOnRay.getScene().getWindow();
        stage.close();
    }
}
