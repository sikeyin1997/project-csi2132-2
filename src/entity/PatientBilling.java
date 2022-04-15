package entity;

import java.util.Date;

public class PatientBilling {

    private Integer employeeNumber;
    private Date date;
    private float patientPortion;
    private float insurancePortion;
    private float total;
    private String paymentMethod;

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPatientPortion() {
        return patientPortion;
    }

    public void setPatientPortion(float patientPortion) {
        this.patientPortion = patientPortion;
    }

    public float getInsurancePortion() {
        return insurancePortion;
    }

    public void setInsurancePortion(float insurancePortion) {
        this.insurancePortion = insurancePortion;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
