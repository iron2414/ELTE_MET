package app.form;

import app.entity.Subject;
import app.entity.User;
import app.entity.UserRepository;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @NotNull
    @Min(0)
    @Max(1)
    private Integer hasPractice;

    @NotNull
    @Min(0)
    @Max(1)
    private Integer isNecessary;

    @NotNull
    private Integer lecutresPerWeek;

    @NotNull
    private Integer recommendedSemester;

    @NotNull
    @SafeHtml
    private String semester;

    @NotNull
    @SafeHtml
    private String whichRoom;

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

    public Integer getHasPractice() {
        return hasPractice;
    }

    public void setHasPractice(Integer hasPractice) {
        this.hasPractice = hasPractice;
    }

    public Integer getIsNecessary() {
        return isNecessary;
    }

    public void setIsNecessary(Integer isNecessary) {
        this.isNecessary = isNecessary;
    }

    public Integer getLecutresPerWeek() {
        return lecutresPerWeek;
    }

    public void setLecutresPerWeek(Integer lecutresPerWeek) {
        this.lecutresPerWeek = lecutresPerWeek;
    }

    public Integer getRecommendedSemester() {
        return recommendedSemester;
    }

    public void setRecommendedSemester(Integer recommendedSemester) {
        this.recommendedSemester = recommendedSemester;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getWhichRoom() {
        return whichRoom;
    }

    public void setWhichRoom(String whichRoom) {
        this.whichRoom = whichRoom;
    }

    public Subject execute() {
        Subject subject = new Subject();
        return execute(subject);
    }

    public Subject execute(Subject subject) {
        subject.setName(name);
        subject.setCredit(credit);
        subject.setHasPractice(1==getHasPractice());
        subject.setLecturesPerWeek(getLecutresPerWeek());
        subject.setNecessary(1==getIsNecessary());
        subject.setRecommendedSemester(getRecommendedSemester());
        subject.setSemester(semester);
        subject.setWhichRoom(getWhichRoom());
        return subject;
    }
}
