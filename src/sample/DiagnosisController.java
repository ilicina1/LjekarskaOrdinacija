package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DiagnosisController {
    public TableView tableViewDiagnosis;
    public TableColumn colId;
    public TableColumn colDiagnosis;

    public KlasaDAO dao;
    private ObservableList<Diagnosis> listDiagnoses;

    public DiagnosisController() {
        dao = KlasaDAO.getInstance();
    }
}
