package app.form;

import app.entity.Subject;
import app.entity.User;
import app.entity.UserRepository;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

//TODO a repositoryt nem találja, mert az autowire valamiért nullt-ad, egyelőre controllerben a cucc
public class SubjectForm {

    @Autowired
    private UserRepository userRepository;

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

    public Subject execute() throws Exception {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setCredit(credit);
        return subject;
    }
}
