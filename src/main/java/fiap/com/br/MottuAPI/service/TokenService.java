package fiap.com.br.MottuAPI.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import fiap.com.br.MottuAPI.model.Usuario;
import fiap.com.br.MottuAPI.model.UserRole;

@Service
public class TokenService {

    Algorithm algorithm = Algorithm.HMAC256("secret");

    public record Token(String token, String email) {}

    public Token createToken(Usuario user) {
        Instant expiresAt = LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.ofHours(-3));
        var jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(jwt, user.getEmail());
    }

    public Usuario getUserFromToken(String token) {
        var verifiedToken = JWT.require(algorithm).build().verify(token);
        return Usuario.builder()
            .id(Long.valueOf(verifiedToken.getSubject()))
            .email(verifiedToken.getClaim("email").asString())
            .role(UserRole.valueOf(verifiedToken.getClaim("role").asString()))
            .build();
    }
}

