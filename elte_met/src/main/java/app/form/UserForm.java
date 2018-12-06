package app.form;

import app.entity.User;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserForm {
    @NotNull
    @SafeHtml
    @Size(min = 2, max = 30)
    private String name;

    @SafeHtml
    @NotNull
    private String username;

    @SafeHtml
    @NotNull
    private String phoneNumber;

    //@DateTimeFormat
    private Date dateOfBirth;

    @SafeHtml
    @NotNull
    private String nationality;

    @SafeHtml
    @NotNull
    private String bankAccountNumber;

    @SafeHtml
    @NotNull
    private String taxNumber;

    @SafeHtml
    @NotNull
    private String degree;

    @NotNull
    private Integer isSuperAdmin;

    @NotNull
    @Email
    @Size(min = 2, max = 30)
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Integer isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public User execute() {
        User user = new User();
        return execute(user);
    }

    public User execute(User user) {
        user.setName(name);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setWhichSemester(1);
        user.setTaxNumber(taxNumber);
        user.setNationality(nationality);
        user.setBankAccountNumber(bankAccountNumber);
        user.setDegree(degree);
        user.setDateOfBirth(dateOfBirth);
        user.setSuperAdmin(isSuperAdmin==1);
        user.setEnabled(true);
        return user;
    }
}
