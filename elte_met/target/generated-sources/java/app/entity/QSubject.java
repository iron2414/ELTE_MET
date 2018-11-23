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

    public QSubject(String variable) {
        super(Subject.class, forVariable(variable));
    }

    public QSubject(Path<? extends Subject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubject(PathMetadata metadata) {
        super(Subject.class, metadata);
    }

}

