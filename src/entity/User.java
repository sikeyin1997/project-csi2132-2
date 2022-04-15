package entity;

import java.util.Date;


public class User {

        private Integer id;
        private String email;

        private String accountType;
        private String password;

        private Integer ssn;
        private Integer phoneNO;

        private String firstName;
        private String midName;
        private String lastName;

        private Integer houseNumber;
        private String streetName;
        private String city;
        private String province;
        private Date dateOfBirth;
        private Integer age;

        public Integer getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getSsn() {
            return ssn;
        }

        public void setSsn(int ssn) {
            this.ssn = ssn;
        }

        public long getPhoneNO() {
            return phoneNO;
        }

        public void setPhoneNO(int phoneNO) {
            this.phoneNO = phoneNO;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMidName() {
            return midName;
        }

        public void setMidName(String midName) {
            this.midName = midName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Date getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public int getAge() {

            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
}
