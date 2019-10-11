package sample;

public class DiagnosisController {
    public KlasaDAO dao;

    public DiagnosisController() {
        dao = KlasaDAO.getInstance();
    }
}
