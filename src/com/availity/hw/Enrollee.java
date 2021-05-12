package com.availity.hw;

import com.opencsv.bean.CsvBindByName;

//user_id,first_name,last_name,version,insurance_company
public class Enrollee {

    public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }

    public Enrollee() {
    }

    @CsvBindByName(column = "user_id")
    private String userId;

    @CsvBindByName(column = "first_name")
    private String firstName;

    @CsvBindByName(column = "last_name")
    private String lastName;

    @CsvBindByName(column = "version")
    private int version;

    @CsvBindByName(column = "insurance_company")
    private String insuranceCompany;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public String toString() {
        return "Enrollee{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", version='" + version + '\'' +
                ", insuranceCompany='" + insuranceCompany + '\'' +
                '}';
    }

//    @Override
//    public int compareTo(Enrollee other) {
//        return Comparator.comparing(Enrollee::getInsuranceCompany)
//                .thenComparing(Enrollee::getFirstName)
//                .thenComparing(Enrollee::getLastName)
//                .compare(this, other);
//    }
}