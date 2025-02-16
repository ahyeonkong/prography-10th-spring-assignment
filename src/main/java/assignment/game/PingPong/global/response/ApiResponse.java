package assignment.game.PingPong.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private Integer code;
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
