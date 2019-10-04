package sample;

public class KlasaDAO {
    private static KlasaDAO instance;
    public static KlasaDAO getInstance() {
        if (instance == null) instance = new KlasaDAO();
        return instance;
    }
}
