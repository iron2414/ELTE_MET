package app.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author norbert.varjasi
 */
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @Column(name = "lectures_per_week", nullable = false)
    private Integer lecturesPerWeek;

    @Column(name = "has_practice", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean hasPractice;

    @Column(name = "is_necessary", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean isNecessary;

    @Column(name = "recommended_semester", nullable = false)
    private Integer recommendedSemester;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "subject")
    private Set<Document> documents = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "subject")
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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getLecturesPerWeek() {
        return lecturesPerWeek;
    }

    public void setLecturesPerWeek(Integer lecturesPerWeek) {
        this.lecturesPerWeek = lecturesPerWeek;
    }

    public Boolean getHasPractice() {
        return hasPractice;
    }

    public void setHasPractice(Boolean hasPractice) {
        this.hasPractice = hasPractice;
    }

    public Boolean getNecessary() {
        return isNecessary;
    }

    public void setNecessary(Boolean necessary) {
        isNecessary = necessary;
    }

    public Integer getRecommendedSemester() {
        return recommendedSemester;
    }

    public void setRecommendedSemester(Integer recommendedSemester) {
        this.recommendedSemester = recommendedSemester;
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