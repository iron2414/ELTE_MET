package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class PracticeRepositoryImpl {
    @Autowired
    static PracticeRepository practiceRepository;

    public static Practice findDocument(String id) {
        return practiceRepository.findById(new Integer(id));
    }
}
