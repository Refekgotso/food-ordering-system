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

    /**
     * The HTTP-style status code for this response (e.g. 200, 404, 400).
     */
    private int statusCode;

    /**
     * A human-readable message describing the result of the request.
     */
    private String message;

    /**
     * The actual data payload returned by the request, if any.
     */
    private T data;

    /**
     * The exact date and time this response was generated.
     */
    private LocalDateTime timestamp;
}