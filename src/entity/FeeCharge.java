package entity;

public class FeeCharge {

    private Integer feeId;
    private Integer feeCode;
    private float charge;
    private String procedure;

    public Integer getFeeId() {
        return feeId;
    }

    public void setFeeId(Integer feeId) {
        this.feeId = feeId;
    }

    public Integer getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(Integer feeCode) {
        this.feeCode = feeCode;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }
}
