package apap.tutorial.bacabaca.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import apap.tutorial.bacabaca.DTO.UserMapper;
import apap.tutorial.bacabaca.DTO.request.LoginJwtRequestDTO;
import apap.tutorial.bacabaca.DTO.response.ErrorResponseDTO;
import apap.tutorial.bacabaca.DTO.response.LoginJwtResponseDTO;
import apap.tutorial.bacabaca.model.UserModel;
import apap.tutorial.bacabaca.repository.UserDb;
import apap.tutorial.bacabaca.security.jwt.JwtUtils;
import apap.tutorial.bacabaca.service.RoleService;
import apap.tutorial.bacabaca.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthRestController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDb userDb;

    @PostMapping("/auth/login-jwt-webadmin")
    public ResponseEntity<?> loginJwtAdmin (@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) {
        try {
            String jwtToken = userService.loginJwtAdmin(loginJwtRequestDTO);
            return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginJwtRequestDTO loginRequestDTO) {
        try {
            // Ambil data pengguna dari database berdasarkan username
            UserModel user = userDb.findByUsername(loginRequestDTO.getUsername());

            if (user != null) {
                // Verifikasi password dengan BCryptPasswordEncoder
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
                    // Jika password sesuai, generate token
                    String jwtToken = jwtUtils.generateJwtToken(loginRequestDTO.getUsername());

                    // token dalam response success
                    System.out.println(jwtToken);
                    return ResponseEntity.ok(new LoginJwtResponseDTO(jwtToken));
                }
            }

            // Jika autentikasi gagal
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponseDTO(false, "Username atau password salah"));
        } catch (Exception e) {
            // exception jika terjadi kesalahan
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO(false, "Terjadi kesalahan pada server"));
        }
    }

}
