package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class KlasaDAO {
    private static KlasaDAO instance;
    private Connection conn;
    private Connection conn2;

    private PreparedStatement dajUserNameUpit, dajEmailUpit, dodajDoktoraUpit, dajPasswordUpit, dajPacijenteUpit, dodajPacijentaUpit, promjeniPacijentaUpit, obrisiPacijentaUpit;

    public static KlasaDAO getInstance() {
        if (instance == null) instance = new KlasaDAO();
        return instance;
    }

    KlasaDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Doktori.db");
            conn2 = DriverManager.getConnection("jdbc:sqlite:Pacijenti.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // za bazu doktori
        try {
            dajUserNameUpit = conn.prepareStatement("SELECT * FROM Doktori WHERE user_name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                dajUserNameUpit = conn.prepareStatement("SELECT * FROM Doktori WHERE user_name=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        //za bazu pacijenti
        try {
            dajPacijenteUpit = conn.prepareStatement(("SELECT * FROM Pacijenti"));
        } catch (SQLException e) {
            regenerisiBazu2();
            try {
                dajPacijenteUpit = conn.prepareStatement(("SELECT * FROM Pacijenti"));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            dajEmailUpit = conn.prepareStatement("SELECT e_mail FROM Doktori WHERE e_mail=?");
            dodajDoktoraUpit = conn.prepareStatement("INSERT INTO Doktori VALUES(?,?,?,?,?)");
            dajPasswordUpit = conn.prepareStatement("SELECT password FROM Doktori WHERE user_name=?");
            promjeniPacijentaUpit = conn.prepareStatement("UPDATE Pacijenti SET full_name=?, phone_number=?, city=?, address=?, birth_date=? WHERE medical_record_number=?");
            dodajPacijentaUpit = conn.prepareStatement("INSERT INTO Pacijenti VALUES(?,?,?,?,?,?)");
            obrisiPacijentaUpit = conn.prepareStatement("DELETE FROM Pacijenti WHERE medical_record_number=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close2();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close2() {
        try {
            conn2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("Doktori.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    System.out.println("Izvrsavam upit: "+sqlUpit);
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu2() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("Pacijenti.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    System.out.println("Izvrsavam upit: "+sqlUpit);
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registerDoctor(String firstName, String lastName, String userName, String password, String eMail) throws SQLException {
        dodajDoktoraUpit.setString(1, firstName);
        dodajDoktoraUpit.setString(2, lastName);
        dodajDoktoraUpit.setString(3, userName);
        dodajDoktoraUpit.setString(4, password);
        dodajDoktoraUpit.setString(5, eMail);
        dodajDoktoraUpit.executeUpdate();
    }

    public boolean validateUserName(String userName) throws SQLException {
        dajUserNameUpit.setString(1,userName);
        ResultSet rs = dajUserNameUpit.executeQuery();
        if(!rs.next()) return false;
        return true;
    }
    public boolean validateEmail(String eMail) throws SQLException {
        dajEmailUpit.setString(1,eMail);
        ResultSet rs = dajEmailUpit.executeQuery();
        if(!rs.next()) return false;
        return true;
    }
    public String returnPassword(String userName) throws SQLException {
        dajPasswordUpit.setString(1,userName);
        ResultSet rs = dajPasswordUpit.executeQuery();
        if(!rs.next()) return null;
        return rs.getString(1);
    }

    private Patients dajPacijentaIzRs(ResultSet rs) throws SQLException {
        Patients pacijent = new Patients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
        return pacijent;
    }

    public ArrayList<Patients> pacijenti() {
        ArrayList<Patients> rezultat = new ArrayList();
        try {
            ResultSet rs = dajPacijenteUpit.executeQuery();
            while (rs.next()) {
                Patients pacijent = dajPacijentaIzRs(rs);
                rezultat.add(pacijent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public void izmijeniPacijenta(Patients pacijent){
        try {
            promjeniPacijentaUpit.setString(1, pacijent.getFullName());
            promjeniPacijentaUpit.setString(2, pacijent.getPhoneNumber());
            promjeniPacijentaUpit.setString(3, pacijent.getCity());
            promjeniPacijentaUpit.setString(4, pacijent.getAddress());
            promjeniPacijentaUpit.setString(5, pacijent.getBirthDate());
            promjeniPacijentaUpit.setInt(6, pacijent.getMedicalRecordNumber());
            promjeniPacijentaUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajPacijenta(Patients pacijent) {
        try {
            dodajPacijentaUpit.setInt(1, pacijent.getMedicalRecordNumber());
            dodajPacijentaUpit.setString(2, pacijent.getFullName());
            dodajPacijentaUpit.setString(3, pacijent.getPhoneNumber());
            dodajPacijentaUpit.setString(4, pacijent.getCity());
            dodajPacijentaUpit.setString(5, pacijent.getAddress());
            dodajPacijentaUpit.setString(6, pacijent.getBirthDate());
            dodajPacijentaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obrisiPacijenta(Patients pacijent){
        try {
            obrisiPacijentaUpit.setInt(1, pacijent.getMedicalRecordNumber());
            obrisiPacijentaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
