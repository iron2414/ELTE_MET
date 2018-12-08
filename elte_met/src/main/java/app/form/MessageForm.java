package app.form;

import app.entity.Dds;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class MessageForm {

    private Long practice;

    private Long subject;

    @NotNull
    @SafeHtml
    private String content;

    public Long getPractice() {
        return practice;
    }

    public void setPractice(Long practice) {
        this.practice = practice;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
