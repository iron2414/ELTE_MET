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

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String username;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "last_login", columnDefinition = "DATETIME")
    private Date lastLogin;

    @Column(name = "is_enabled", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_super_admin", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean isSuperAdmin;

    @Column(name = "deleted_at", columnDefinition = "DATETIME")
    private Date deletedAt;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "security_user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groups = new HashSet<>();


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

}