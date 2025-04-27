package UserDomain.controller;

import UserDomain.dto.AuthenticateDTO.AuthenticateDTO;
import UserDomain.dto.AuthenticateDTO.IntrospectDTO;
import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.service.interf.IAuthenticateService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticateController {
    private final IAuthenticateService authenticateService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAPI<Void>> authenticate(@RequestBody AuthenticateDTO request) {
        ResponseAPI<Void> response = authenticateService.authenticate(request, false);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/introspect")
    public ResponseEntity<ResponseAPI<Void>> introspect(@RequestBody IntrospectDTO request) throws ParseException, JOSEException {
        ResponseAPI<Void> response = authenticateService.introspect(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/login-google")
    public ResponseEntity<ResponseAPI<String>> googleLogin(HttpServletRequest request) {
        ResponseAPI<String> response = authenticateService.generateAuthUrl(request, "login");
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/callback-google")
    public RedirectView googleCallback(
            @RequestParam String code,
            @RequestParam String state) {
        return authenticateService.getAccessToken(code, state);
    }

    @PostMapping("/register")
    ResponseEntity<ResponseAPI<Void>> registerUser(@RequestBody UserDTO request) {
        ResponseAPI<Void> response = authenticateService.registerUser(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/register-google")
    ResponseEntity<ResponseAPI<String>> googleRegister(HttpServletRequest request) {
        ResponseAPI<String> response = authenticateService.generateAuthUrl(request, "register");
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
