package sample;

public class Results {
    public int id;
    public String sample;
    public String typeOfAnalysis;
    public double result;
    public String normalValue;
    public MedicalReports report;

    public Results() {
    }

    public Results(int id, String sample, String typeOfAnalysis, double result, String normalValue, MedicalReports report) {
        this.id = id;
        this.sample = sample;
        this.typeOfAnalysis = typeOfAnalysis;
        this.result = result;
        this.normalValue = normalValue;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getTypeOfAnalysis() {
        return typeOfAnalysis;
    }

    public void setTypeOfAnalysis(String typeOfAnalysis) {
        this.typeOfAnalysis = typeOfAnalysis;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getNormalValue() {
        return normalValue;
    }

    public void setNormalValue(String normalValue) {
        this.normalValue = normalValue;
    }

    public MedicalReports getReport() {
        return report;
    }

    public void setReport(MedicalReports report) {
        this.report = report;
    }
}
