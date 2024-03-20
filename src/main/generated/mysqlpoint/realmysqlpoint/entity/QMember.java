package mysqlpoint.realmysqlpoint.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1308064451L;

    public static final QMember member = new QMember("member1");

    public final mysqlpoint.realmysqlpoint.util.QBaseEntity _super = new mysqlpoint.realmysqlpoint.util.QBaseEntity(this);

    public final BooleanPath agreedToServicePolicy = createBoolean("agreedToServicePolicy");

    public final BooleanPath agreedToServicePolicyUse = createBoolean("agreedToServicePolicyUse");

    public final BooleanPath agreedToServiceUse = createBoolean("agreedToServiceUse");

    public final DatePath<java.time.LocalDate> certifyAt = createDate("certifyAt", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> phone = createNumber("phone", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

