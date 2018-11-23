package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * @author norbert.varjasi
 */
@Entity
@Table(name = "security_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @JsonIgnore
    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String username;
    @JsonIgnore
    @Column(name = "phone_number", length = 100)
    private String phoneNumber;
    @JsonIgnore
    @Column(unique = true, length = 100, nullable = false)
    private String email;


    @Column(name = "password", length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @JsonIgnore
    @Column(name = "last_login", columnDefinition = "DATETIME")
    private Date lastLogin;

    @Column(name = "date_of_birth", columnDefinition = "DATETIME", nullable = false)
    private Date dateOfBirth;

    @Column(name = "which_semester", nullable = false)
    private Integer whichSemester;

    @Column(name = "nationality", length = 25, nullable = false)
    private String nationality;

    @Column(name = "bank_account_number", length = 20, nullable = false, unique = true)
    private String bankAccountNumber;

    @Column(name = "tax_number", length = 20, nullable = false, unique = true)
    private String taxNumber;

    @Column(name = "degree", length = 4, nullable = false)
    private String degree;

    @JsonIgnore
    @Column(name = "is_enabled", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean isEnabled;

    @JsonIgnore
    @Column(name = "is_super_admin", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean isSuperAdmin;

    @JsonIgnore
    @Column(name = "deleted_at", columnDefinition = "DATETIME")
    private Date deletedAt;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "security_user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groups = new HashSet<>();


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "uploader")
    private Set<Document> documents = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "teacher")
    private Set<Practice> practices = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        this.getGroups().add(group);
    }

    public void removeGroup(Group group) {
        this.getGroups().remove(group);
    }

    public Set<Permission> getPermissions() {
        Set<Permission> result = new HashSet<>();
        Set<Group> groups = getGroups();

        for (Group group : groups) {
            Set<Permission> permissions = group.getPermissions();
            for(Permission permission : permissions) {
                if(!result.contains(permission)) {
                    result.add(permission);
                }
            }
        }
        return result;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void addDocument(Document document) {
        this.getDocuments().add(document);
    }

    public void removeDocument(Document document) {
        this.getDocuments().remove(document);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getPermissions();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getWhichSemester() {
        return whichSemester;
    }

    public void setWhichSemester(Integer whichSemester) {
        this.whichSemester = whichSemester;
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

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Practice> getPractices() {
        return practices;
    }

    public void addPractice(Practice practice) {
        this.getPractices().add(practice);
    }

    public void removePractice(Practice practice) {
        this.getPractices().remove(practice);
    }
}