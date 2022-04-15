package entity;

import java.util.Date;

public class Invoice {

    private Date inoviceDate;
    private String contactInfo;
    private String appointment;

    private float patientChange;
    private float insuranceCharge;
    private float discount;
    private float penalty;
    private float total;

    private Integer patientInsurance;

    public Date getInoviceDate() {
        return inoviceDate;
    }

    public void setInoviceDate(Date inoviceDate) {
        this.inoviceDate = inoviceDate;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public float getPatientChange() {
        return patientChange;
    }

    public void setPatientChange(float patientChange) {
        this.patientChange = patientChange;
    }

    public float getInsuranceCharge() {
        return insuranceCharge;
    }

    public void setInsuranceCharge(float insuranceCharge) {
        this.insuranceCharge = insuranceCharge;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getPenalty() {
        return penalty;
    }

    public void setPenalty(float penalty) {
        this.penalty = penalty;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Integer getPatientInsurance() {
        return patientInsurance;
    }

    public void setPatientInsurance(Integer patientInsurance) {
        this.patientInsurance = patientInsurance;
    }
}
