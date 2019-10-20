package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public void actionConfirm(ActionEvent actionEvent) throws IOException, SQLException {
        if(file == null) {
            System.out.println("nevalja");
            return;
        }
        fis = new FileInputStream(file);
        Xray xray = new Xray(dao.dajNajveciId5() + 1, tfWhatsOnRay.getText(), dpDate.getValue(), pacijent);
        dao.dodajXray(xray, fis, (int)file.length());
    }
}
