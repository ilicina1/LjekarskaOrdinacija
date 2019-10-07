package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class KlasaDAO {
    private static KlasaDAO instance;
    private Connection conn;

    private PreparedStatement dajUserNameUpit, dajEmailUpit, dodajDoktoraUpit, dajPasswordUpit;

    public static KlasaDAO getInstance() {
        if (instance == null) instance = new KlasaDAO();
        return instance;
    }

    KlasaDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Doktori.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        try {
            dajEmailUpit = conn.prepareStatement("SELECT e_mail FROM Doktori WHERE e_mail=?");
            dodajDoktoraUpit = conn.prepareStatement("INSERT INTO Doktori VALUES(?,?,?,?,?)");
            dajPasswordUpit = conn.prepareStatement("SELECT password FROM Doktori WHERE user_name=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
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
}
