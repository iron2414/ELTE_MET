package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubjectSemesterTeacher is a Querydsl query type for SubjectSemesterTeacher
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubjectSemesterTeacher extends EntityPathBase<SubjectSemesterTeacher> {

    private static final long serialVersionUID = -1026937796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubjectSemesterTeacher subjectSemesterTeacher = new QSubjectSemesterTeacher("subjectSemesterTeacher");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser institutionTeacher;

    public final StringPath semester = createString("semester");

    public final QSubject subject;

    public QSubjectSemesterTeacher(String variable) {
        this(SubjectSemesterTeacher.class, forVariable(variable), INITS);
    }

    public QSubjectSemesterTeacher(Path<? extends SubjectSemesterTeacher> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubjectSemesterTeacher(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubjectSemesterTeacher(PathMetadata metadata, PathInits inits) {
        this(SubjectSemesterTeacher.class, metadata, inits);
    }

    public QSubjectSemesterTeacher(Class<? extends SubjectSemesterTeacher> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.institutionTeacher = inits.isInitialized("institutionTeacher") ? new QUser(forProperty("institutionTeacher")) : null;
        this.subject = inits.isInitialized("subject") ? new QSubject(forProperty("subject")) : null;
    }

}

