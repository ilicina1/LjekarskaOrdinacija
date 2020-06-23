package sample;

import java.time.LocalDate;

public class Patients {
    private int medicalRecordNumber;
    private String fullName;
    private String phoneNumber;
    private String city;
    private String address;
    private LocalDate birthDate;

    public Patients() {
    }

    public Patients(int medicalRecordNumber, String fullName, String phoneNumber, String city, String address, LocalDate birthDate) {
        this.medicalRecordNumber = medicalRecordNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.address = address;
        this.birthDate = birthDate;
    }

    public int getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(int medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
