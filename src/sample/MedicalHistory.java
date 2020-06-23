package sample;

public class MedicalHistory {
    private int id;
    private String allergies;
    private String familyMedicalIssues;
    private String addictions;
    private String currentHealthIssues;
    private  Patients pacijent;

    public MedicalHistory() {
    }

    public MedicalHistory(int id, String allergies, String familyMedicalIssues, String addictions, String currentHealthIssues, Patients pacijent) {
        this.id = id;
        this.allergies = allergies;
        this.familyMedicalIssues = familyMedicalIssues;
        this.addictions = addictions;
        this.currentHealthIssues = currentHealthIssues;
        this.pacijent = pacijent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getFamilyMedicalIssues() {
        return familyMedicalIssues;
    }

    public void setFamilyMedicalIssues(String familyMedicalIssues) {
        this.familyMedicalIssues = familyMedicalIssues;
    }

    public String getAddictions() {
        return addictions;
    }

    public void setAddictions(String addictions) {
        this.addictions = addictions;
    }

    public String getCurrentHealthIssues() {
        return currentHealthIssues;
    }

    public void setCurrentHealthIssues(String currentHealthIssues) {
        this.currentHealthIssues = currentHealthIssues;
    }

    public Patients getPacijent() {
        return pacijent;
    }

    public void setPacijent(Patients pacijent) {
        this.pacijent = pacijent;
    }
}
