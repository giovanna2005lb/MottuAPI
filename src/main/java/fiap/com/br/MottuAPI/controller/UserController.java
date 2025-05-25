package fiap.com.br.MottuAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import fiap.com.br.MottuAPI.model.Usuario;
import fiap.com.br.MottuAPI.model.dto.UsuarioResponse;
import fiap.com.br.MottuAPI.repository.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse create(@RequestBody @Valid Usuario user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        var userSaved = repository.save(user);
        return new UsuarioResponse(
            userSaved.getId(),
            userSaved.getNome(),
            userSaved.getEmail(),
            userSaved.getRole());}
}
