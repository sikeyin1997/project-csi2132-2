package entity;

public class Treatment {

    private String appointmentType;
    private Integer treatmentType;
    private String medication;
    private String symtoms;
    private String tooth;
    private String comments;

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Integer getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(Integer treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getSymtoms() {
        return symtoms;
    }

    public void setSymtoms(String symtoms) {
        this.symtoms = symtoms;
    }

    public String getTooth() {
        return tooth;
    }

    public void setTooth(String tooth) {
        this.tooth = tooth;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
