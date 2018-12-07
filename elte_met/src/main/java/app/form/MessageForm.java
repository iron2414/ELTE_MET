package app.form;

import app.entity.Message;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class MessageForm {

    @NotNull
    private Integer durability;

    public Message execute() {
        Message message = new Message();
        return execute(message);
    }

    public Message execute(Message message) {
        return message;
    }
}
