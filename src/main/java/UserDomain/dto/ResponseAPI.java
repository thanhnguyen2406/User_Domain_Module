package UserDomain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.xml.transform.Templates;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseAPI<T> {
    int code;
    String message;
    String token;
    Date expiration;

    Integer currentPage;
    Integer totalPages;
    Integer totalElements;

    T data;
}
