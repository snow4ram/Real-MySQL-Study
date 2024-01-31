package mysqlpoint.realmysqlpoint.map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HaversineFormulaSolutionService {
        double latitude = 35.8389726;
        double longitude = 128.7210818;
        double radius = 10.0; // 반경 값 설정 (예: 5.0 km) mbr 반 지름 설정

//        double latitude = 37.4901548250937;
//        double longitude = 127.030767490957;
//        double radius = 30.0; // 반경 값 설정 (예: 5.0 km) mbr

        //37.55371926075816y1 127.11098061003622x2 37.42653625138507y2 126.95069083279373//111.321 : [128.6656765288934, 35.88388775527169, 128.7764870711066, 35.79405744472831]

        //111.32 : [128.66567603118165, 35.88388815874955, 128.77648756881837, 35.79405704125045]

        //LINESTRING(128.73494891328122 35.8502141447768,128.7072146867188 35.8277310552232)

    //todo  latitude : 위도 , longitude : 경도 , radius:  반지름

    //todo 경도 1 도 :  111km,

    //todo 위도 1 도 : 88km

    public Location calculateDestination(double startLatitude, double startLongitude, double travelDistance, double travelBearing) {

        double travelBearingRadians = Math.toRadians(travelBearing);//반 지름 설정
        double startLatitudeRadians = Math.toRadians(startLatitude);//위도
        double startLongitudeRadians = Math.toRadians(startLongitude);//경도

        // 지구 반지름
        double angularDistance = travelDistance / 6378.1370;

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
