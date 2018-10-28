package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubject is a Querydsl query type for Subject
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubject extends EntityPathBase<Subject> {

    private static final long serialVersionUID = 635979118L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubject subject = new QSubject("subject");

    public final NumberPath<Integer> credit = createNumber("credit", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser lecturer;

    public final StringPath name = createString("name");

    public QSubject(String variable) {
        this(Subject.class, forVariable(variable), INITS);
    }

    public QSubject(Path<? extends Subject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubject(PathMetadata metadata, PathInits inits) {
        this(Subject.class, metadata, inits);
    }

    public QSubject(Class<? extends Subject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lecturer = inits.isInitialized("lecturer") ? new QUser(forProperty("lecturer")) : null;
    }

}

