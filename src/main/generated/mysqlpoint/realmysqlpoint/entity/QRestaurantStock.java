package mysqlpoint.realmysqlpoint.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRestaurantStock is a Querydsl query type for RestaurantStock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRestaurantStock extends EntityPathBase<RestaurantStock> {

    private static final long serialVersionUID = -531077168L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRestaurantStock restaurantStock = new QRestaurantStock("restaurantStock");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QItem item;

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    public final QRestaurant restaurant;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRestaurantStock(String variable) {
        this(RestaurantStock.class, forVariable(variable), INITS);
    }

    public QRestaurantStock(Path<? extends RestaurantStock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRestaurantStock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRestaurantStock(PathMetadata metadata, PathInits inits) {
        this(RestaurantStock.class, metadata, inits);
    }

    public QRestaurantStock(Class<? extends RestaurantStock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
        this.restaurant = inits.isInitialized("restaurant") ? new QRestaurant(forProperty("restaurant"), inits.get("restaurant")) : null;
    }

}

