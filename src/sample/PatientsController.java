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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
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

    public KlasaDAO dao;
    private ObservableList<Patients> listPacijenti;

    public PatientsController() {
        dao = KlasaDAO.getInstance();
        listPacijenti = FXCollections.observableArrayList(dao.pacijenti());
    }

    @FXML
    public void initialize() {
        tableViewPacijenti.setItems(listPacijenti);
        colMedicalRN.setCellValueFactory(new PropertyValueFactory("medicalRecordNumber"));
        colFullName.setCellValueFactory(new PropertyValueFactory("fullName"));
        colPhoneNum.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colCity.setCellValueFactory(new PropertyValueFactory("city"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory("birthDate"));
    }

    public void actionNew(ActionEvent actionEvent) throws IOException, SQLException {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dodajPacijenta.fxml"));
            dodajPacijentaController ctrl = new dodajPacijentaController(null, dao.pacijenti());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("New patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.dodajPacijenta(noviPacijent);
                    listPacijenti.setAll(dao.pacijenti());
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
            dodajPacijentaController ctrl = new dodajPacijentaController(pacijent, dao.pacijenti());
            loader.setController(ctrl);
            Parent root = loader.load();
            stage.setTitle("Edit patient");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding( event -> {
                Patients noviPacijent = ctrl.getPacijent();
                if (noviPacijent != null) {
                    dao.izmijeniPacijenta(noviPacijent);
                    listPacijenti.setAll(dao.pacijenti());
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
            dao.obrisiPacijenta(pacijent);
            listPacijenti.setAll(dao.pacijenti());
        }
    }
}
