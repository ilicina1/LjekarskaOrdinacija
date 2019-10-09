package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.scene.layout.Region.USE_PREF_SIZE;


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

    @FXML
    public void initialize() {
        tableViewPacijenti.setItems(listPacijenti);
        colMedicalRN.setCellValueFactory(new PropertyValueFactory("medicalRN"));
        colFullName.setCellValueFactory(new PropertyValueFactory("fullName"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory("phoneNum"));
        colCity.setCellValueFactory(new PropertyValueFactory("city"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
    }

    public void actionNew(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajPacijenta.fxml"));
            dodajPacijentaController ctrl = new dodajPacijentaController();
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Patients");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients pacijent = ctrl.getPacijent();
                if (pacijent != null) {
                    dao.dodajPacijenta(pacijent);
                    listPacijenti.setAll(dao.pacijenti());
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
