package UserDomain.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    //200: Not error but not meet requirements
    NO_USERS_FOUND(200, "No users found"),

    //401: Unauthenticated errors
    UNAUTHENTICATED(401, "Unauthenticated"),
    UNAUTHENTICATED_USERNAME_PASSWORD(401, "Please check username or password again"),
    UNAUTHENTICATED_USERNAME(401, "Please check your username again"),
    UNAUTHENTICATED_USERNAME_DOMAIN(401, "Please enter email ends with @gmail.com"),

    //400: Invalid encoded device
    ENCODED_DEVICE_INVALID(400, "Encoded device string is invalid"),

    //Google Access Token INVALID
    TOKEN_FETCHED_FAIL(400, "Fail to fetch access token"),
    USERINFO_FETCHED_FAIL(400, "Fail to fetch user info"),
    UNAUTHENTICATED_LOGIN(401, "This mail must be logan through google service"),

    //404: Resource not found errors
    USER_NOT_FOUND(404, "User not found"),

    //409: Resource existed errors
    USER_EXISTED(409, "User already existed");

    int code;
    String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
