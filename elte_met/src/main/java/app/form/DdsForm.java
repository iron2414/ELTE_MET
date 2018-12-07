package app.form;

import app.entity.Dds;
import app.entity.Practice;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DdsForm {

    @NotNull
    private Integer durability;

    @NotNull
    private Integer seatNumber;

    @NotNull
    private Date date;

    private Long subject;

    private Long practice;

    private Long exam;

    public Integer getDurability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getPractice() {
        return practice;
    }

    public void setPractice(Long practice) {
        this.practice = practice;
    }

    public Long getExam() {
        return exam;
    }

    public void setExam(Long exam) {
        this.exam = exam;
    }

    public Dds execute() {
        Dds dds = new Dds();
        return execute(dds);
    }

    public Dds execute(Dds dds) {
        dds.setSeatNumber(getSeatNumber());
        dds.setDate(getDate());
        dds.setDurability(getDurability());
        return dds;
    }
}
