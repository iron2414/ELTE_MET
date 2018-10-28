package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class SubjectRepositoryImpl {
    @Autowired
    static UserRepository userRepository;

    public static User findUser(String id) {
        return userRepository.findById(new Integer(id));
    }
}
