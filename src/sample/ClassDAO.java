package sample;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ClassDAO {
    private static ClassDAO instance;
    private Connection conn;
    private Connection conn2;
    private PreparedStatement addUserNameQuery, addEmailQuery, addDoctorQuery, addPasswordQuery, addPatientsQuery, addPatientQuery, updatePatientQuery,
            deletePatientQuery, getDiagnosisByPatientQuery, addDiagnosisQuery, updateDiagnosisQuery, deleteDiagnosisQuery, getDiagnosisById, giveHistoriesQuery, addHistoryQuery, getMaxIdQuery,
            deleteHistoryQuery, getMedicalReportsQuery, addMedicalReportQuery, getMaxId2Query, deleteMedicalReportQuery, getResultsQuery, addResultQuery, getMaxId3Query, deleteResultQuery,
            deleteAllResultsQuery, updateResultQuery, deleteAllDiagnosisQuery, deleteAllHistoriesQuery, deleteAllMedicalReportsQuery, deleteAllResultsByPatientQuery, getAppointmentsQuery,
            deleteAppointmentQuery, getMaxId4Query, addAppointmentQuery, getXraysQuery, addXrayQuery, getMaxId5Query, getXrayQuery, deleteXrayQuery;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static ClassDAO getInstance() {
        if (instance == null) instance = new ClassDAO();
        return instance;
    }

    ClassDAO(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:bazz.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            addUserNameQuery = conn.prepareStatement("SELECT * FROM Doctors WHERE user_name=?");
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                addUserNameQuery = conn.prepareStatement("SELECT * FROM Doctors WHERE user_name=?");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        try {
            addPatientsQuery = conn.prepareStatement(("SELECT * FROM Patients"));
            addEmailQuery = conn.prepareStatement("SELECT e_mail FROM Doctors WHERE e_mail=?");
            addDoctorQuery = conn.prepareStatement("INSERT INTO Doctors VALUES(?,?,?,?,?)");
            addPasswordQuery = conn.prepareStatement("SELECT password FROM Doctors WHERE user_name=?");
            updatePatientQuery = conn.prepareStatement("UPDATE Patients SET full_name=?, phone_number=?, city=?, address=?, birth_date=? WHERE medical_record_number=?");
            addPatientQuery = conn.prepareStatement("INSERT INTO Patients VALUES(?,?,?,?,?,?)");
            addDiagnosisQuery = conn.prepareStatement("INSERT INTO Diagnosis VALUES(?,?,?)");
            deletePatientQuery = conn.prepareStatement("DELETE FROM Patients WHERE medical_record_number=?");
            getDiagnosisByPatientQuery = conn.prepareStatement(("SELECT * FROM Diagnosis WHERE patient=?"));
            updateDiagnosisQuery = conn.prepareStatement("UPDATE Diagnosis SET text=? WHERE id=?");
            deleteDiagnosisQuery = conn.prepareStatement("DELETE FROM Diagnosis WHERE id=?");
            getDiagnosisById = conn.prepareStatement("SELECT * FROM Diagnosis WHERE id=?");
            giveHistoriesQuery = conn.prepareStatement("SELECT * FROM History WHERE patient=?");
            addHistoryQuery = conn.prepareStatement("INSERT INTO History VALUES(?,?,?,?,?,?)");
            getMaxIdQuery = conn.prepareStatement("SELECT id FROM History WHERE id = (SELECT MAX(id) FROM History)");
            deleteHistoryQuery = conn.prepareStatement("DELETE FROM History WHERE id=?");
            getMedicalReportsQuery = conn.prepareStatement("SELECT * FROM Reports WHERE patient=?");
            addMedicalReportQuery = conn.prepareStatement("INSERT INTO Reports VALUES(?,?,?)");
            getMaxId2Query = conn.prepareStatement("SELECT id FROM Reports WHERE id = (SELECT MAX(id) FROM Reports)");
            deleteMedicalReportQuery = conn.prepareStatement("DELETE FROM Reports WHERE id=?");
            getResultsQuery = conn.prepareStatement("SELECT * FROM Results WHERE report=?");
            addResultQuery = conn.prepareStatement("INSERT INTO Results VALUES(?,?,?,?,?,?)");
            getMaxId3Query = conn.prepareStatement("SELECT id FROM Results WHERE id = (SELECT MAX(id) FROM Results)");
            deleteResultQuery = conn.prepareStatement("DELETE FROM Results WHERE id=?");
            deleteAllResultsQuery = conn.prepareStatement("DELETE FROM Results WHERE report=?");
            updateResultQuery = conn.prepareStatement("UPDATE Results SET sample=?, type_of_analysis=?, result=?, normal_value=? WHERE id=?");
            deleteAllDiagnosisQuery = conn.prepareStatement("DELETE FROM Diagnosis WHERE patient=?");
            deleteAllHistoriesQuery = conn.prepareStatement("DELETE FROM History WHERE patient=?");
            deleteAllMedicalReportsQuery = conn.prepareStatement("DELETE FROM Reports WHERE patient=?");
            deleteAllResultsByPatientQuery = conn.prepareStatement("DELETE FROM Results WHERE report = (SELECT id FROM Reports WHERE patient=?)");
            getAppointmentsQuery = conn.prepareStatement(("SELECT * FROM Appointments"));
            deleteAppointmentQuery = conn.prepareStatement("DELETE FROM Appointments WHERE id=?");
            getMaxId4Query = conn.prepareStatement("SELECT id FROM Appointments WHERE id = (SELECT MAX(id) FROM Appointments)");
            addAppointmentQuery = conn.prepareStatement("INSERT INTO Appointments VALUES(?,?,?,?,?)");
            getXraysQuery = conn.prepareStatement("SELECT * FROM xray WHERE patient=?");
            addXrayQuery = conn.prepareStatement("INSERT INTO xray VALUES(?,?,?,?,?)");
            getMaxId5Query = conn.prepareStatement("SELECT id FROM xray WHERE id = (SELECT MAX(id) FROM xray)");
            getXrayQuery = conn.prepareStatement("SELECT image FROM xray WHERE id=?");
            deleteXrayQuery = conn.prepareStatement("DELETE FROM xray WHERE id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("bazz.db.sql"));
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
        addDoctorQuery.setString(1, firstName);
        addDoctorQuery.setString(2, lastName);
        addDoctorQuery.setString(3, userName);
        addDoctorQuery.setString(4, password);
        addDoctorQuery.setString(5, eMail);
        addDoctorQuery.executeUpdate();
    }

    public boolean validateUserName(String userName) throws SQLException {
        addUserNameQuery.setString(1,userName);
        ResultSet rs = addUserNameQuery.executeQuery();
        if(!rs.next()) return false;
        return true;
    }
    public boolean validateEmail(String eMail) throws SQLException {
        addEmailQuery.setString(1,eMail);
        ResultSet rs = addEmailQuery.executeQuery();
        if(!rs.next()) return false;
        return true;
    }
    public String returnPassword(String userName) throws SQLException {
        addPasswordQuery.setString(1,userName);
        ResultSet rs = addPasswordQuery.executeQuery();
        if(!rs.next()) return null;
        return rs.getString(1);
    }

    public boolean validateDiagnosis(int id) throws SQLException {
        getDiagnosisById.setInt(1,id);
        ResultSet rs = getDiagnosisById.executeQuery();
        if(!rs.next()) return false;
        return true;
    }

    private Patients getPatientFromRs(ResultSet rs) throws SQLException {
        Patients patient = new Patients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), LocalDate.parse(rs.getString(6), formatter));
        return patient;
    }

    private Diagnosis getDiagnosisFromRs(ResultSet rs) throws SQLException {
        Diagnosis diagnosis = new Diagnosis(rs.getInt(1), rs.getString(2), null);
        return diagnosis;
    }

    private MedicalHistory getHistoryFromRs(ResultSet rs) throws SQLException {
        MedicalHistory history = new MedicalHistory(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), null);
        return history;
    }

    private MedicalReports getReportFromRs(ResultSet rs) throws SQLException {
        MedicalReports report = new MedicalReports(rs.getInt(1),  LocalDate.parse(rs.getString(2), formatter), null);
        return report;
    }

    private Results getResultFromRs(ResultSet rs) throws SQLException {
        Results result = new Results(rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), null);
        return result;
    }


    private Appointments getApointmentFromRs(ResultSet rs) throws SQLException {
        Appointments appointment = new Appointments(rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getString(4), LocalDate.parse(rs.getString(5), formatter));
        return appointment;
    }

    private Xray getXrayFromRs(ResultSet rs) throws SQLException {
        Xray xray = new Xray(rs.getInt(1),  rs.getString(2), LocalDate.parse(rs.getString(3), formatter), null);
        return xray;
    }

    public ArrayList<Patients> patients() {
        ArrayList<Patients> result = new ArrayList();
        try {
            ResultSet rs = addPatientsQuery.executeQuery();
            while (rs.next()) {
                Patients pacijent = getPatientFromRs(rs);
                result.add(pacijent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Diagnosis> diagnosis(int id) throws SQLException {
        getDiagnosisByPatientQuery.setInt(1,id);
        ArrayList<Diagnosis> result = new ArrayList();
        try {
            ResultSet rs = getDiagnosisByPatientQuery.executeQuery();
            while (rs.next()) {
                Diagnosis dijagnoza = getDiagnosisFromRs(rs);
                result.add(dijagnoza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<MedicalHistory> history(int id) throws SQLException {
        giveHistoriesQuery.setInt(1,id);
        ArrayList<MedicalHistory> result = new ArrayList();
        try {
            ResultSet rs = giveHistoriesQuery.executeQuery();
            while (rs.next()) {
                MedicalHistory historija = getHistoryFromRs(rs);
                result.add(historija);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<MedicalReports> reports(int id) throws SQLException {
        getMedicalReportsQuery.setInt(1,id);
        ArrayList<MedicalReports> result = new ArrayList();
        try {
            ResultSet rs = getMedicalReportsQuery.executeQuery();
            while (rs.next()) {
                MedicalReports report = getReportFromRs(rs);
                result.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Results> results(int id) throws SQLException {
        getResultsQuery.setInt(1,id);
        ArrayList<Results> results = new ArrayList();
        try {
            ResultSet rs = getResultsQuery.executeQuery();
            while (rs.next()) {
                Results result = getResultFromRs(rs);
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ArrayList<Appointments> appointments() {
        ArrayList<Appointments> result = new ArrayList();
        try {
            ResultSet rs = getAppointmentsQuery.executeQuery();
            while (rs.next()) {
                Appointments appointment = getApointmentFromRs(rs);
                result.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Xray> xrays(int id) throws SQLException {
        getXraysQuery.setInt(1,id);
        ArrayList<Xray> result = new ArrayList();
        try {
            ResultSet rs = getXraysQuery.executeQuery();
            while (rs.next()) {
                    Xray xray = getXrayFromRs(rs);
                result.add(xray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void changePatient(Patients pacijent){
       // LocalDate id = LocalDate.parse(rs.getString(6), formatter);
        try {
            updatePatientQuery.setString(1, pacijent.getFullName());
            updatePatientQuery.setString(2, pacijent.getPhoneNumber());
            updatePatientQuery.setString(3, pacijent.getCity());
            updatePatientQuery.setString(4, pacijent.getAddress());
            updatePatientQuery.setString(5, pacijent.getBirthDate().toString());
            updatePatientQuery.setInt(6, pacijent.getMedicalRecordNumber());
            updatePatientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPatient(Patients pacijent) {
        try {
            addPatientQuery.setInt(1, pacijent.getMedicalRecordNumber());
            addPatientQuery.setString(2, pacijent.getFullName());
            addPatientQuery.setString(3, pacijent.getPhoneNumber());
            addPatientQuery.setString(4, pacijent.getCity());
            addPatientQuery.setString(5, pacijent.getAddress());
            addPatientQuery.setObject(6, pacijent.getBirthDate());
            addPatientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(Patients pacijent){
        try {
            deletePatientQuery.setInt(1, pacijent.getMedicalRecordNumber());
            instance.deleteAllDiagnosis(pacijent);
            instance.deleteAllHistories(pacijent);
            instance.deleteAllResultsByPatient(pacijent);
            instance.deleteAllReports(pacijent);
            deletePatientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDiagnosis(Diagnosis dijagnoza) {
        try {
            addDiagnosisQuery.setInt(1, dijagnoza.getId());
            addDiagnosisQuery.setString(2, dijagnoza.getText());
            addDiagnosisQuery.setInt(3, dijagnoza.getPatient().getMedicalRecordNumber());
            addDiagnosisQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeDiagnosis(Diagnosis dijagnoza){
        try {
            updateDiagnosisQuery.setString(1, dijagnoza.getText());
            updatePatientQuery.setInt(2, dijagnoza.getId());
            updatePatientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiagnosis(Diagnosis dijagnoza){
        try {
            deleteDiagnosisQuery.setInt(1, dijagnoza.getId());
            deleteDiagnosisQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addHistory(MedicalHistory historija) {
        try {
            addHistoryQuery.setInt(1, historija.getId());
            addHistoryQuery.setString(2, historija.getAllergies());
            addHistoryQuery.setString(3, historija.getFamilyMedicalIssues());
            addHistoryQuery.setString(4, historija.getAddictions());
            addHistoryQuery.setString(5, historija.getCurrentHealthIssues());
            addHistoryQuery.setInt(6, historija.getPacijent().getMedicalRecordNumber());
            addHistoryQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId() throws SQLException {
        ResultSet rs = null;
        try {
            rs = getMaxIdQuery.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs.getInt(1);
    }

    public int getMaxId2() throws SQLException {
        ResultSet rs = null;
        try {
            rs = getMaxId2Query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int z = 0;
        if(!rs.next()) return z;
        return rs.getInt(1);
    }

    public int getMaxId3() throws SQLException {
        ResultSet rs = null;
        try {
            rs = getMaxId3Query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int z = 0;
        if(!rs.next()) return z;
        return rs.getInt(1);
    }

    public int getMaxId4() throws SQLException {
        ResultSet rs = null;
        try {
            rs = getMaxId4Query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int z = 0;
        if(!rs.next()) return z;
        return rs.getInt(1);
    }

    public int getMaxId5() throws SQLException {
        ResultSet rs = null;
        try {
            rs = getMaxId5Query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int z = 0;
        if(!rs.next()) return z;
        return rs.getInt(1);
    }


    public void deleteHistory(MedicalHistory historija){
        try {
            deleteHistoryQuery.setInt(1, historija.getId());
            deleteHistoryQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReport(MedicalReports medicalReport) {
        try {
            addMedicalReportQuery.setInt(1, medicalReport.getId());
            addMedicalReportQuery.setObject(2, medicalReport.getDate());
            addMedicalReportQuery.setInt(3, medicalReport.getPatient().getMedicalRecordNumber());
            addMedicalReportQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReport(MedicalReports nalaz){
        try {
            deleteMedicalReportQuery.setInt(1, nalaz.getId());
            deleteMedicalReportQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addResult(Results rezultat) {
        try {
            addResultQuery.setInt(1, rezultat.getId());
            addResultQuery.setString(2, rezultat.getSample());
            addResultQuery.setString(3, rezultat.getTypeOfAnalysis());
            addResultQuery.setDouble(4, rezultat.getResult());
            addResultQuery.setString(5, rezultat.getNormalValue());
            addResultQuery.setInt(6, rezultat.getReport().getId());
            addResultQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResult(Results rezultat){
        try {
            deleteResultQuery.setInt(1, rezultat.getId());
            deleteResultQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResults(MedicalReports nalaz){
        try {
            deleteAllResultsQuery.setInt(1, nalaz.getId());
            deleteAllResultsQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeResult(Results rezultat){
        try {
            updateResultQuery.setString(1, rezultat.getSample());
            updateResultQuery.setString(2, rezultat.getTypeOfAnalysis());
            updateResultQuery.setDouble(3, rezultat.getResult());
            updateResultQuery.setString(4, rezultat.getNormalValue());
            updateResultQuery.setInt(5, rezultat.getId());
            updateResultQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllDiagnosis(Patients pacijent){
        try {
            deleteAllDiagnosisQuery.setInt(1, pacijent.getMedicalRecordNumber());
            deleteAllDiagnosisQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllHistories(Patients pacijent){
        try {
            deleteAllHistoriesQuery.setInt(1, pacijent.getMedicalRecordNumber());
            deleteAllHistoriesQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllReports(Patients pacijent){
        try {
            deleteAllMedicalReportsQuery.setInt(1, pacijent.getMedicalRecordNumber());
            deleteAllMedicalReportsQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllResultsByPatient(Patients pacijent){
        try {
            deleteAllResultsByPatientQuery.setInt(1, pacijent.getMedicalRecordNumber());
            deleteAllResultsByPatientQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAppointment(Appointments appointment){
        try {
            deleteAppointmentQuery.setInt(1, appointment.getId());
            deleteAppointmentQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(Appointments appointment) {
        try {
            addAppointmentQuery.setInt(1, appointment.getId());
            addAppointmentQuery.setString(2, appointment.getNameAndSurname());
            addAppointmentQuery.setString(3, appointment.getTime());
            addAppointmentQuery.setString(4, appointment.getReason());
            addAppointmentQuery.setObject(5, appointment.getDate());
            addAppointmentQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addXray(Xray xray, FileInputStream fis, int len) {
        try {
            addXrayQuery.setInt(1, xray.getId());
            addXrayQuery.setString(2, xray.getWhatsOnRay());
            addXrayQuery.setObject(3, xray.getDate());
            addXrayQuery.setInt(4, xray.getPatient().getMedicalRecordNumber());
            addXrayQuery.setBinaryStream(5, fis, len);
            addXrayQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public Image getImage(int id) throws SQLException, IOException {
            Image image = null;
            getXrayQuery.setInt(1, id);
            ResultSet rs = getXrayQuery.executeQuery();
            while(rs.next())
            {
                InputStream is = rs.getBinaryStream("image");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                byte[]content = new byte[1024];
                int size = 0;
                while((size=is.read(content))!= -1)
                {
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image imagex = new Image("file:photo.jpg",250,250,true,true);
                image = imagex;
            }
            return image;
    }

    public void deleteXray(Xray xray){
        try {
            deleteXrayQuery.setInt(1, xray.getId());
            deleteXrayQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
