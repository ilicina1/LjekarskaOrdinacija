package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AddXrayController {
    public TextField tfWhatsOnRay;
    public DatePicker dpDate;

    private KlasaDAO dao;
    private FileInputStream fis;
    private File file;
    private Patients pacijent;

    public AddXrayController(Patients pacijent) {
        dao = KlasaDAO.getInstance();
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

    public void actionConfirm(ActionEvent actionEvent) throws FileNotFoundException, SQLException {
        if(file == null) {
            System.out.println("nevalja");
            return;
        }
        fis = new FileInputStream(file);
        Xray xray = new Xray(dao.dajNajveciId5() + 1, tfWhatsOnRay.getText(), dpDate.getValue(), pacijent);
        System.out.println(xray.getId());
        System.out.println(xray.getWhatsOnRay());
        System.out.println(xray.getDate());
        System.out.println(xray.getPatient().getMedicalRecordNumber());
        dao.dodajXray(xray, fis);
    }
}
