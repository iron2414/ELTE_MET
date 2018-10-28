package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class SubjectRepositoryImpl {
    @Autowired
    static SubjectRepository subjectRepository;

    public static Subject findSubject(String id) {
        return subjectRepository.findById(new Integer(id));
    }
}
