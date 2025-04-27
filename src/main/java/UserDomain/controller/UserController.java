package UserDomain.controller;

import UserDomain.dto.ResponseAPI;
import UserDomain.dto.UserDTO.UserDTO;
import UserDomain.enums.UserType;
import UserDomain.service.interf.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    @GetMapping("/my-info")
    public ResponseEntity<ResponseAPI<UserDTO>> getMyInfo() {
        ResponseAPI<UserDTO> response = userService.getMyInfo();
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    @PutMapping("/update")
    public ResponseEntity<ResponseAPI<Void>> updateUser(@RequestBody UserDTO userDTO) {
        ResponseAPI<Void> response = userService.updateUser(userDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseAPI<Void>> deleteUser(@PathVariable Long id) {
        ResponseAPI<Void> response = userService.deleteUser(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-doctor")
    public ResponseEntity<ResponseAPI<Void>> createDoctor(@RequestBody UserDTO userDTO) {
        ResponseAPI<Void> response = userService.createDoctor(userDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<ResponseAPI<List<UserDTO>>> getAllUsers(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseAPI<List<UserDTO>> response = userService.getAllUsers(pageable);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-patients")
    public ResponseEntity<ResponseAPI<List<UserDTO>>> getAllPatients(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseAPI<List<UserDTO>> response = userService.getAllPatients(pageable);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-doctors")
    public ResponseEntity<ResponseAPI<List<UserDTO>>> getAllDoctors(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        ResponseAPI<List<UserDTO>> response = userService.getAllDoctors(pageable);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
