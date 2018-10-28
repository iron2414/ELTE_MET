package app.form;

import app.entity.Subject;
import app.entity.User;
import app.entity.UserRepository;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

public class SubjectForm {
    @NotNull
    @SafeHtml
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    private Integer credit;

    private Long lecturer;

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

    public Long getLecturer() {
        return lecturer;
    }

    public void setLecturer(Long lecturer) {
        this.lecturer = lecturer;
    }

    public Subject execute() {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setCredit(credit);
        return subject;
    }
}
