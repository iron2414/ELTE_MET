package app.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "practice")
public class Practice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(name = "credit_number", nullable = false)
    private Integer credit;

    @Column(name = "has_tasks", columnDefinition = "tinyint(1) default 0", nullable = false)
    private Boolean hasTasks;

    @Column(name = "how_many_task", nullable = false)
    private Integer howManyTask;

    @Column(name = "which_room", length = 15, nullable = false)
    private String whichRoom;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "practice_user",
            joinColumns = { @JoinColumn(name = "practice_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Boolean getHasTasks() {
        return hasTasks;
    }

    public void setHasTasks(Boolean hasTasks) {
        this.hasTasks = hasTasks;
    }

    public Integer getHowManyTask() {
        return howManyTask;
    }

    public void setHowManyTask(Integer howManyTask) {
        this.howManyTask = howManyTask;
    }

    public String getWhichRoom() {
        return whichRoom;
    }

    public void setWhichRoom(String whichRoom) {
        this.whichRoom = whichRoom;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public void addStudent(User user) {
        this.getStudents().add(user);
    }

    public void removeStudent(User user) {
        this.getStudents().remove(user);
    }
}
