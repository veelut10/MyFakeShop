package productos.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import productos.rest.modelo.LoginDTO;
import productos.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {

            // Generar token
            String token = jwtService.generarToken(loginDTO.getUsername(), "usuario");

            return ResponseEntity.ok(token);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Usuario o contraseña inválidos");
        }
    }
}