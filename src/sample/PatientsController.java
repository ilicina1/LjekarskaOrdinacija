package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PatientsController {
    public TableView<Patients> tableViewPacijenti;
    public TableColumn colMedicalRN;
    public TableColumn colFullName;
    public TableColumn colPhoneNum;
    public TableColumn colCity;
    public TableColumn colAddress;
    public TableColumn colBirthDate;

    public KlasaDAO dao;
    private ObservableList<Patients> listPacijenti;

    public PatientsController() {
        dao = KlasaDAO.getInstance();
        listPacijenti = FXCollections.observableArrayList(dao.pacijenti());
    }
}
