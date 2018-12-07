package app.form;

import app.entity.Practice;
import app.entity.Subject;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PracticeForm {

    @NotNull
    private Long subject;

    @NotNull
    private Integer credit;

    @NotNull
    @Min(0)
    @Max(1)
    private Integer hasTasks;

    @NotNull
    private Long teacher;

    @NotNull
    private Integer howManyTasks;

    @NotNull
    @SafeHtml
    private String whichRoom;

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getHasTasks() {
        return hasTasks;
    }

    public void setHasTasks(Integer hasTasks) {
        this.hasTasks = hasTasks;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Integer getHowManyTasks() {
        return howManyTasks;
    }

    public void setHowManyTasks(Integer howManyTasks) {
        this.howManyTasks = howManyTasks;
    }

    public String getWhichRoom() {
        return whichRoom;
    }

    public void setWhichRoom(String whichRoom) {
        this.whichRoom = whichRoom;
    }

    public Practice execute() {
        Practice practice = new Practice();
        return execute(practice);
    }

    public Practice execute(Practice practice) {

        practice.setCredit(credit);
        practice.setHasTasks(1==getHasTasks());
        practice.setHowManyTask(getHowManyTasks());
        practice.setWhichRoom(getWhichRoom());
        return practice;
    }
}
