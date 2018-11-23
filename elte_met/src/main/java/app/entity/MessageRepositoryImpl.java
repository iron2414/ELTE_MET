package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class MessageRepositoryImpl {
    @Autowired
    static MessageRepository messageRepository;

    public static Message findMessage(String id) {
        return messageRepository.findById(new Integer(id));
    }
}
