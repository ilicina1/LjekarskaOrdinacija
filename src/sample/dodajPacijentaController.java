package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class dodajPacijentaController {
    public TextField tfMedicalRN;
    public TextField tfFullName;
    public TextField tfPhoneNum;
    public TextField tfCity;
    public TextField tfAddress;
    public TextField tfBirthDate;

    private Patients pacijent;
    private ObservableList<Patients> listPacijenti;

    public Patients getPacijent() {
        return pacijent;
    }

    public void actionConfirm(ActionEvent actionEvent) throws IOException, SQLException {
        pacijent = new Patients(Integer.parseInt(tfMedicalRN.getText()), tfFullName.getText(), tfPhoneNum.getText(), tfCity.getText(), tfAddress.getText(), tfBirthDate.getText());
    }
}
