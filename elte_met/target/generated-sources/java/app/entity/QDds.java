package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDds is a Querydsl query type for Dds
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDds extends EntityPathBase<Dds> {

    private static final long serialVersionUID = -1263147723L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDds dds = new QDds("dds");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Integer> durability = createNumber("durability", Integer.class);

    public final QExam exam;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPractice practice;

    public final NumberPath<Integer> seatNumber = createNumber("seatNumber", Integer.class);

    public final QSubject subject;

    public QDds(String variable) {
        this(Dds.class, forVariable(variable), INITS);
    }

    public QDds(Path<? extends Dds> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDds(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDds(PathMetadata metadata, PathInits inits) {
        this(Dds.class, metadata, inits);
    }

    public QDds(Class<? extends Dds> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exam = inits.isInitialized("exam") ? new QExam(forProperty("exam"), inits.get("exam")) : null;
        this.practice = inits.isInitialized("practice") ? new QPractice(forProperty("practice"), inits.get("practice")) : null;
        this.subject = inits.isInitialized("subject") ? new QSubject(forProperty("subject")) : null;
    }

}

