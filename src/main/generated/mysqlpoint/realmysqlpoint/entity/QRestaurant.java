package mysqlpoint.realmysqlpoint.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurant is a Querydsl query type for Restaurant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurant extends EntityPathBase<Restaurant> {

    private static final long serialVersionUID = 584646374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurant restaurant = new QRestaurant("restaurant");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath category = createString("category");

    public final NumberPath<Long> contact = createNumber("contact", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ComparablePath<org.locationtech.jts.geom.Point> location = createComparable("location", org.locationtech.jts.geom.Point.class);

    public final QMember members;

    public final MapPath<String, Object, SimplePath<Object>> menu = this.<String, Object, SimplePath<Object>>createMap("menu", String.class, Object.class, SimplePath.class);

    public final StringPath name = createString("name");

    public final MapPath<String, Object, SimplePath<Object>> provision = this.<String, Object, SimplePath<Object>>createMap("provision", String.class, Object.class, SimplePath.class);

    public final ListPath<RestaurantStock, QRestaurantStock> restaurantStocks = this.<RestaurantStock, QRestaurantStock>createList("restaurantStocks", RestaurantStock.class, QRestaurantStock.class, PathInits.DIRECT2);

    public final MapPath<String, Object, SimplePath<Object>> time = this.<String, Object, SimplePath<Object>>createMap("time", String.class, Object.class, SimplePath.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRestaurant(String variable) {
        this(Restaurant.class, forVariable(variable), INITS);
    }

    public QRestaurant(Path<? extends Restaurant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurant(PathMetadata metadata, PathInits inits) {
        this(Restaurant.class, metadata, inits);
    }

    public QRestaurant(Class<? extends Restaurant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMember(forProperty("members")) : null;
    }

}

