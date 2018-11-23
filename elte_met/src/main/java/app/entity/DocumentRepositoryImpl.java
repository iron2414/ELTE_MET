package app.entity;

import org.springframework.beans.factory.annotation.Autowired;

public class DocumentRepositoryImpl {
    @Autowired
    static DocumentRepository documentRepository;

    public static Document findDocument(String id) {
        return documentRepository.findById(new Integer(id));
    }
}
