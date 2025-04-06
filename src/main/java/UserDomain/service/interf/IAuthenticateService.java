package UserDomain.service.interf;

import UserDomain.dto.AuthenticateDTO.AuthenticateDTO;
import UserDomain.dto.AuthenticateDTO.IntrospectDTO;
import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface IAuthenticateService {
    ResponseAPI<Void> authenticate(AuthenticateDTO request, Boolean isGoogleLogin);
    ResponseAPI<Void> introspect(IntrospectDTO request) throws ParseException, JOSEException;
    ResponseAPI<String> generateAuthUrl(HttpServletRequest request, String login);
    ResponseAPI<Void> getAccessToken(String code, String state);
    ResponseAPI<Void> registerUser(UserDTO request);
}
