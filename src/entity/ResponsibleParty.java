package entity;

public class ResponsibleParty {

    private String relationshipToPatient;
    private Integer InsuranceNumber;
    private Integer id;

    public String getRelationshipToPatient() {
        return relationshipToPatient;
    }

    public void setRelationshipToPatient(String relationshipToPatient) {
        this.relationshipToPatient = relationshipToPatient;
    }

    public Integer getInsuranceNumber() {
        return InsuranceNumber;
    }

    public void setInsuranceNumber(Integer insuranceNumber) {
        InsuranceNumber = insuranceNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
