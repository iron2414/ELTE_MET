package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author norbert.varjasi
 */
@Entity
@Table(name = "security_permission")
public class Permission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

	@Column(length = 50)
	private String module;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			},
			mappedBy = "permissions")
	@JsonIgnore
	private Set<Group> groups = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String getAuthority() {
		return this.getName();
	}
}