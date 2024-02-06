package mysqlpoint.realmysqlpoint.map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HaversineFormulaSolutionService {

    //todo  latitude : 위도 , longitude : 경도 , radius:  반지름

    //todo 경도 1 도 :  111km,

    //todo 위도 1 도 : 88km

    public Location calculateDestination(double startLatitude, double startLongitude, double travelDistance, double travelBearing) {

        double travelBearingRadians = Math.toRadians(travelBearing);//반 지름 설정
        double startLatitudeRadians = Math.toRadians(startLatitude);//위도
        double startLongitudeRadians = Math.toRadians(startLongitude);//경도

        // 지구 반지름
        double angularDistance = travelDistance / 6378.1370;

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
