package app.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -502353207L;

    public static final QUser user = new QUser("user");

    public final StringPath bankAccountNumber = createString("bankAccountNumber");

    public final DateTimePath<java.util.Date> dateOfBirth = createDateTime("dateOfBirth", java.util.Date.class);

    public final StringPath degree = createString("degree");

    public final DateTimePath<java.util.Date> deletedAt = createDateTime("deletedAt", java.util.Date.class);

    public final SetPath<Document, QDocument> documents = this.<Document, QDocument>createSet("documents", Document.class, QDocument.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final SetPath<Group, QGroup> groups = this.<Group, QGroup>createSet("groups", Group.class, QGroup.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isEnabled = createBoolean("isEnabled");

    public final BooleanPath isSuperAdmin = createBoolean("isSuperAdmin");

    public final DateTimePath<java.util.Date> lastLogin = createDateTime("lastLogin", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath nationality = createString("nationality");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final SetPath<Practice, QPractice> practices = this.<Practice, QPractice>createSet("practices", Practice.class, QPractice.class, PathInits.DIRECT2);

    public final StringPath taxNumber = createString("taxNumber");

    public final StringPath username = createString("username");

    public final NumberPath<Integer> whichSemester = createNumber("whichSemester", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

