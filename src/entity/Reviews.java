package entity;

import java.util.Date;

public class Reviews {

    private Date reviewDate;
    private Long InsuranceNumber;
    private Integer employeeNumber;

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getInsuranceNumber() {
        return InsuranceNumber;
    }

    public void setInsuranceNumber(Long insuranceNumber) {
        InsuranceNumber = insuranceNumber;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
