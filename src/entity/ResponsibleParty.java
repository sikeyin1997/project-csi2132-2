package entity;

public class ResponsibleParty {

    private String relationshipToPatient;
    private Long InsuranceNumber;
    private Integer id;

    public String getRelationshipToPatient() {
        return relationshipToPatient;
    }

    public void setRelationshipToPatient(String relationshipToPatient) {
        this.relationshipToPatient = relationshipToPatient;
    }

    public Long getInsuranceNumber() {
        return InsuranceNumber;
    }

    public void setInsuranceNumber(Long insuranceNumber) {
        InsuranceNumber = insuranceNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
