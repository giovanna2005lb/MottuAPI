package fiap.com.br.MottuAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fiap.com.br.MottuAPI.model.Usuario;
import fiap.com.br.MottuAPI.service.TokenService.Token;


@RestController
public class AuthController {

    public record Credentials (String email, String password){}

    @Autowired
    private fiap.com.br.MottuAPI.service.TokenService tokenService;

    @Autowired
    AuthenticationManager authManager;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        var authentication = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
        var user = (Usuario) authManager.authenticate(authentication).getPrincipal();

        return tokenService.createToken(user);
    }
    
}