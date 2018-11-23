package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class SubjectSemesterTeacherRepositoryImpl {
    @Autowired
    static SubjectSemesterTeacherRepository subjectSemesterTeacherRepository;

    public static SubjectSemesterTeacher findSubjectSemesterTeacher(String id) {
        return subjectSemesterTeacherRepository.findById(new Integer(id));
    }
}
