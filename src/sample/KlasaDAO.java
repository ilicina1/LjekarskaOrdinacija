package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class KlasaDAO {
    private static KlasaDAO instance;
    private Connection conn;

    private PreparedStatement dajUserNameUpit, dajEmailUpit;

    public static KlasaDAO getInstance() {
        if (instance == null) instance = new KlasaDAO();
        return instance;
    }

    private KlasaDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Doktori.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            dajUserNameUpit = conn.prepareStatement("SELECT doktorUser.user_name, FROM doktorUser WHERE user_name=?");
//        } catch (SQLException e) {
//            regenerisiBazu();
//            try {
//                dajUserNameUpit = conn.prepareStatement("SELECT doktorUser.user_name, FROM doktorUser WHERE user_name=?");
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//        }

        try {
            dajEmailUpit = conn.prepareStatement("SELECT e_mail FROM doktorUser WHERE e_mail=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("doktorUser.sql"));
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

    public boolean validateUserName(String userName) throws SQLException {
        dajUserNameUpit.setString(1,userName);
        ResultSet rs = dajUserNameUpit.executeQuery();
        if(!rs.next()) return true;
        return false;
    }
    public boolean validateEmail(String eMail) throws SQLException {
        dajEmailUpit.setString(1,eMail);
        ResultSet rs = dajEmailUpit.executeQuery();
        if(!rs.next()) return true;
        return false;
    }
}
