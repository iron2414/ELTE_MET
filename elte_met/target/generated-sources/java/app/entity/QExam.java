package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExam is a Querydsl query type for Exam
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QExam extends EntityPathBase<Exam> {

    private static final long serialVersionUID = -502825187L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExam exam = new QExam("exam");

    public final QUser createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath whichRoom = createString("whichRoom");

    public QExam(String variable) {
        this(Exam.class, forVariable(variable), INITS);
    }

    public QExam(Path<? extends Exam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExam(PathMetadata metadata, PathInits inits) {
        this(Exam.class, metadata, inits);
    }

    public QExam(Class<? extends Exam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy = inits.isInitialized("createdBy") ? new QUser(forProperty("createdBy")) : null;
    }

}

