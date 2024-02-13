package mysqlpoint.realmysqlpoint.restaurant;
import jakarta.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.entity.*;
import mysqlpoint.realmysqlpoint.entity.Location;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;

import mysqlpoint.realmysqlpoint.service.RestaurantService;
import mysqlpoint.realmysqlpoint.util.DayInfo;
import mysqlpoint.realmysqlpoint.util.Direction;
import mysqlpoint.realmysqlpoint.util.Provision;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;


@Slf4j
@SpringBootTest
@Transactional
public class RestaurantTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private JpaRestaurantRepository repository;

    final GeometryFactory geometryFactory = new GeometryFactory();

    private static final Double EARTH_RADIUS = 6378.1370;

    @Test
    @Commit
    public void store() {
        Coordinate[] coordinates = {
                new Coordinate(128.66567603118165, 35.8502141447768),
                new Coordinate(129.70067603118165, 40.8502141447768),
                new Coordinate(124.35067603118165, 50.8502141447768),
                new Coordinate(121.45677603118165, 78.8502141447768),
                new Coordinate(135.66567603118165, 32.8502141447768),
                new Coordinate(145.66567603118165, 51.8502141447768),
                new Coordinate(180.78767603118165, 21.8502141447768),
                new Coordinate(127.66567603118165, 34.8502141447768),
                new Coordinate(125.63007603118165, 32.8502141447768),
                new Coordinate(130.80007603118165, 32.8502141447768),
        };

        // 레스토랑을 생성하고 저장합니다.
        for (int i = 0; i < coordinates.length; i++) {
            Point point = geometryFactory.createPoint(coordinates[i]);

            Restaurant restaurant = Restaurant.builder()
                    .category("한식")
                    .name("맛있는 한식당 " + (i + 1))  // 레스토랑 이름을 유니크하게 만듭니다.
                    .address("서울시 강남구" + (i + 1))  // 레스토랑 이름을 유니크하게 만듭니다.
                    .location(point)
                    .contact(1012345678L)
                    .build();

            
            repository.save(restaurant);
        }
    }


    @Test
    public void polygon_double() {
        double latitude = 35.8430710;//위도
        double longitude = 128.695523; //경도

        //radius : 0.0 ~ 0.9 m | 10.0 ~ 99.0 km
        double radius = 5000; // 반

        Location northEast = calculateDestination(latitude, longitude, radius, Direction.NORTHEAST.getBearing());

        log.info("북서쪽 = {} " , northEast);

        Location southWest = calculateDestination(latitude, longitude, radius, Direction.SOUTHWEST.getBearing());

        log.info("남동쪽 = {}" , southWest);

        double x1 = northEast.getLatitude(); //북동쪽 위도
        double y1 = northEast.getLongitude();//남동쪽 경도

        double x2 = southWest.getLatitude(); //남서쪽 위도
        double y2 = southWest.getLongitude(); //남서쪽 경도

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);

        //Query restaurant = queryService.findRestaurant(pointFormat);
        System.out.println(pointFormat);


    }


    //todo point x : 경도  , y : 위도
    @Test
    public void 가게_찾기() throws ParseException {

        //37.4901548250937, 127.030767490957
//        double latitude = 35.8393357; //위도
//        double longitude = 128.7207385; //경도
        double latitude = 35.84082040579553; //위도
        double longitude = 128.70514786316414; //경도

        double radius = 1.0; // 반지름 -> 거리 2km , 200 -> 200m

        final Coordinate coordinate1 = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coordinate1);

        Location northEast = calculateDestination(latitude, longitude, radius, Direction.NORTHEAST.getBearing());

        Location southWest = calculateDestination(latitude, longitude, radius, Direction.SOUTHWEST.getBearing());


        double northEastLatitude = northEast.getLatitude(); //북동쪽 위도
        double northEastLongitude = northEast.getLongitude();//북동쪽 경도
        double southWestLatitude = southWest.getLatitude(); //남서쪽 위도
        double southWestLongitude = southWest.getLongitude(); //남서쪽 경도

        //new Coordinate(경도, 위도)
        final GeometryFactory geometryFactory = new GeometryFactory();
        final Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(northEastLongitude, northEastLatitude),
                new Coordinate(northEastLatitude, southWestLongitude),
                new Coordinate(southWestLatitude, southWestLongitude),
                new Coordinate(southWestLatitude, northEastLongitude),
                new Coordinate(northEastLongitude, northEastLatitude),
        };
        String pointFormat = String.format("LINESTRING(%f %f, %f %f)", northEastLatitude, northEastLongitude, southWestLatitude, southWestLongitude);

        System.out.println(northEastLatitude);
        System.out.println(northEastLongitude);
        log.info("Point 폴리곤 = {}" , pointFormat);

        String polygonFormat = String.format("'POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f, %f %f))'",
                northEastLatitude, northEastLongitude, northEastLatitude, southWestLongitude, southWestLatitude, southWestLongitude, southWestLatitude, northEastLongitude, northEastLatitude, northEastLongitude, northEastLatitude, northEastLongitude); // 마지막 좌표 반복

        log.info("polygonFormat = {}", polygonFormat);

        String pointFormats = String.format("POLYGON((" +
                        "%f %f, %f %f, %f %f, %f %f, %f %f" +
                        "))",
                longitude, latitude - radius,

                longitude + radius, latitude - radius,

                longitude + radius, latitude + radius,

                longitude, latitude + radius,

                longitude, latitude - radius);

        log.info("경도 = {} , 위도 = {}", longitude, latitude - radius);
        log.info("경도 = {} , 위도 = {}", longitude + radius, latitude - radius);
        log.info("경도 = {} , 위도 = {}", longitude + radius, latitude + radius);
        log.info("경도 = {} , 위도 = {}", longitude, latitude + radius);
        log.info("경도 = {} , 위도 = {}", longitude, latitude - radius);


//        첫 번째 좌표: 경도 = 128.70514786316414, 위도 = 34.84082040579553 (남서쪽 꼭짓점)
//        두 번째 좌표: 경도 = 129.70514786316414, 위도 = 34.84082040579553 (남동쪽 꼭짓점)
//        세 번째 좌표: 경도 = 129.70514786316414, 위도 = 36.84082040579553 (북동쪽 꼭짓점)
//        네 번째 좌표: 경도 = 128.70514786316414, 위도 = 36.84082040579553 (북서쪽 꼭짓점)
        double mbd = 1; //5km

//        List<Restaurant> restaurantsWithinPolygon = repository.findAllWithinRadius(pointFormats , point , mbd);
//
//
//        restaurantsWithinPolygon.forEach(x3 -> log.info("찾은값 = {}", x3));

    }


    @Test
    public void R_Tree_TestCode() {

        double latitude = 35.8393357; //위도
        double longitude = 128.7210818; //경도

        //radius : 0.0 ~ 0.9 m | 10.0 ~ 99.0 km
        double radius = 5000; // 반
        final GeometryFactory geometryFactory = new GeometryFactory();

        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));


        double halfSideLength = radius / (111.32 * Math.cos(Math.toRadians(latitude)));

        Coordinate[] coordinates = new Coordinate[5];

        coordinates[0] = new Coordinate(longitude - halfSideLength, latitude - halfSideLength);

        coordinates[1] = new Coordinate(longitude + halfSideLength, latitude - halfSideLength);

        coordinates[2] = new Coordinate(longitude + halfSideLength, latitude + halfSideLength);

        coordinates[3] = new Coordinate(longitude - halfSideLength, latitude + halfSideLength);

        coordinates[4] = coordinates[0];  // Close the ring

        String pointFormats = String.format("POLYGON((" +
                        "%f %f, %f %f, %f %f, %f %f, %f %f" +
                        "))",
                longitude - halfSideLength, latitude - halfSideLength,

                longitude + halfSideLength, latitude - halfSideLength,

                longitude + halfSideLength, latitude + halfSideLength,

                longitude - halfSideLength, latitude + halfSideLength,

                longitude - halfSideLength, latitude - halfSideLength);


        Polygon polygon = geometryFactory.createPolygon(coordinates);

        log.info("String 폴리곤 라인 = {} " , pointFormats);

        System.out.println(polygon);

        /* todo POLYGON ((128.71076541596418 35.82936261596415,
                    128.73071158403584 35.82936261596415,
                    128.73071158403584 35.84930878403585,
                    128.71076541596418 35.84930878403585,
                    128.71076541596418 35.82936261596415
                    )) */


    }

    public Location calculateDestination(double startLatitude, double startLongitude, double travelDistance, double travelBearing) {

        double travelBearingRadians = Math.toRadians(travelBearing);//반 지름 설정
        double startLatitudeRadians = Math.toRadians(startLatitude);//위도
        double startLongitudeRadians = Math.toRadians(startLongitude);//경도
        double angularDistance = travelDistance / EARTH_RADIUS;

        log.info("방위각 = {} " , travelBearingRadians);

        // 구면 삼각법 공식을 사용하여 목적지 위도를 계산
        double destinationLatitudeRadians = Math.asin(
                Math.sin(startLatitudeRadians) * Math.cos(angularDistance) +
                        Math.cos(startLatitudeRadians) * Math.sin(angularDistance) *
                                Math.cos(travelBearingRadians)
        );

        // 구면 삼각법 공식을 사용하여 목적지 경도를 계산
        double destinationLongitudeRadians = startLongitudeRadians +
                Math.atan2(Math.sin(travelBearingRadians) * Math.sin(angularDistance) * Math.cos(startLatitudeRadians),
                        Math.cos(angularDistance) - Math.sin(startLatitudeRadians) * Math.sin(destinationLatitudeRadians)
                );


        //각도 = 라디안 * 180 / π
        return new Location(Math.toDegrees(destinationLatitudeRadians), Math.toDegrees(destinationLongitudeRadians));

    }



}
