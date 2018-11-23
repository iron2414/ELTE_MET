package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class ExamRepositoryImpl {
    @Autowired
    static ExamRepository examRepository;

    public static Exam findExam(String id) {
        return examRepository.findById(new Integer(id));
    }
}
