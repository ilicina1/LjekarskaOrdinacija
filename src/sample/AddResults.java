package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class AddResults {

    public KlasaDAO dao;
    public Results rezultat;
    private ObservableList<Results> listResults;


    public AddResults(ArrayList<Results> rezultati) {
        listResults = FXCollections.observableArrayList(rezultati);
        dao = KlasaDAO.getInstance();
    }

    public Results getRezultat() {
        return rezultat;
    }
}
