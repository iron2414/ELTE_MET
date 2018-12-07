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

    public final SetPath<Document, QDocument> documents = this.<Document, QDocument>createSet("documents", Document.class, QDocument.class, PathInits.DIRECT2);

    public final BooleanPath hasPractice = createBoolean("hasPractice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isNecessary = createBoolean("isNecessary");

    public final NumberPath<Integer> lecturesPerWeek = createNumber("lecturesPerWeek", Integer.class);

    public final StringPath name = createString("name");

    public final SetPath<Practice, QPractice> practices = this.<Practice, QPractice>createSet("practices", Practice.class, QPractice.class, PathInits.DIRECT2);

    public final NumberPath<Integer> recommendedSemester = createNumber("recommendedSemester", Integer.class);

    public final StringPath semester = createString("semester");

    public final SetPath<User, QUser> students = this.<User, QUser>createSet("students", User.class, QUser.class, PathInits.DIRECT2);

    public final QUser teacher;

    public final StringPath whichRoom = createString("whichRoom");

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
        this.teacher = inits.isInitialized("teacher") ? new QUser(forProperty("teacher")) : null;
    }

}

