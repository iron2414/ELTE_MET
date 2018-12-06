package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class GroupRepositoryImpl {
    @Autowired
    static GroupRepository groupRepository;

    public static Group findGroup(String id) {
        return groupRepository.findById(new Integer(id));
    }
}
