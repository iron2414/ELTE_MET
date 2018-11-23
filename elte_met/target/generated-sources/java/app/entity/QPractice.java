package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPractice is a Querydsl query type for Practice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPractice extends EntityPathBase<Practice> {

    private static final long serialVersionUID = -1095132871L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPractice practice = new QPractice("practice");

    public final NumberPath<Integer> credit = createNumber("credit", Integer.class);

    public final BooleanPath hasTasks = createBoolean("hasTasks");

    public final NumberPath<Integer> howManyTask = createNumber("howManyTask", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSubject subject;

    public final QUser teacher;

    public QPractice(String variable) {
        this(Practice.class, forVariable(variable), INITS);
    }

    public QPractice(Path<? extends Practice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPractice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPractice(PathMetadata metadata, PathInits inits) {
        this(Practice.class, metadata, inits);
    }

    public QPractice(Class<? extends Practice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subject = inits.isInitialized("subject") ? new QSubject(forProperty("subject")) : null;
        this.teacher = inits.isInitialized("teacher") ? new QUser(forProperty("teacher")) : null;
    }

}

