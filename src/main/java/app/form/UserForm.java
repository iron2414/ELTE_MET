package app.form;

import app.entity.User;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForm {
    @NotNull
    @SafeHtml
    @Size(min = 2, max = 30)
    private String name;

    @SafeHtml
    private String username;

    private String certKey;

    private String phoneNumber;

    @NotNull
    @Email
    @Size(min = 2, max = 30)
    private String email;

    private Boolean isEnabled;

    private String authMethod;

    @SafeHtml
    private String description;

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

    public String getCertKey() {
        return certKey;
    }

    public void setCertKey(String certKey) {
        this.certKey = certKey;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User execute() {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setCertKey(certKey);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setEnabled(true);
        user.setAuthMethod(authMethod);
        user.setDescription(description);
        return user;
    }
}
