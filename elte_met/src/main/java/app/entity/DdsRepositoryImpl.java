package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class DdsRepositoryImpl {
    @Autowired
    static DdsRepository ddsRepository;

    public static Dds findDds(String id) {
        return ddsRepository.findById(new Integer(id));
    }
}
