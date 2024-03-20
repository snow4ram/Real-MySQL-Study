package mysqlpoint.realmysqlpoint.exception;

import lombok.*;
import mysqlpoint.realmysqlpoint.common.response.HttpResponse;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessException extends RuntimeException {
    private HttpStatus status;
    private String message;

    @Builder
    public BusinessException(HttpResponse.Fail response) {
        super(response.getMessage());
        this.status = response.getStatus();
        this.message = response.getMessage();
    }
}
