package sample;

import javafx.scene.control.Button;

public class PacijentiController {

    public KlasaDAO dao;

    public PacijentiController() {
        dao = KlasaDAO.getInstance();
    }
}
