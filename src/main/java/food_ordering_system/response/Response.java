package food_ordering_system.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response<T> is a generic wrapper used to standardize the shape of
 * every API response. Instead of each endpoint returning raw data in
 * a different structure, every response will consistently include a
 * statusCode, message, the actual data payload, and a timestamp.
 *
 * @JsonInclude(NON_NULL) hides any field that is null when converted
 * to JSON - this keeps error responses clean since they don't include
 * a data field.
 *
 * @param <T> the type of the data payload (e.g. CategoryDto, List<CategoryDto>)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> Response<T> success(String message, T data) {
        return Response.<T>builder()
                .statusCode(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> Response<T> error(int code, String message) {
        return Response.<T>builder()
                .statusCode(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}