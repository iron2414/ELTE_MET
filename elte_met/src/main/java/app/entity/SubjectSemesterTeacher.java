package app.entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "subject_semester_teacher")
public class SubjectSemesterTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "semester", length = 50, nullable = false)
    private String semester;

    //TODO oneToMany otherside
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;


    //TODO oneToMany otherside
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "institution_teacher", nullable = false)
    private User institutionTeacher;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getInstituionTeacher() {
        return institutionTeacher;
    }

    public void setInstituionTeacher(User institutionTeacher) {
        this.institutionTeacher = institutionTeacher;
    }
}
