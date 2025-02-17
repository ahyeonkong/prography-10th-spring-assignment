package assignment.game.PingPong.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({ "code", "message", "result" }) // 필드 순서 지정
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 제외
public class ApiResponse<T> {
    private int code;
    private String message;
    private T result;

    public ApiResponse(ResponseCode responseCode, T result) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.result = result;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(ResponseCode.SUCCESS, result);
    }

    public static <T> ApiResponse<T> invalidRequest() {
        return new ApiResponse<>(ResponseCode.INVALID_REQUEST, null);
    }

    public static <T> ApiResponse<T> serverError() {
        return new ApiResponse<>(ResponseCode.SERVER_ERROR, null);
    }

}
