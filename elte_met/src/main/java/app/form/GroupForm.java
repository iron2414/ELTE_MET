package app.form;

import app.entity.Group;
import app.entity.User;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class GroupForm {
    @NotNull
    @SafeHtml
    @Size(min = 2, max = 30)
    private String name;

    @SafeHtml
    @NotNull
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Group execute() {
        Group group = new Group();
        return execute(group);
    }

    public Group execute(Group group) {
        group.setName(name);
        group.setDescription(description);
        return group;
    }
}
