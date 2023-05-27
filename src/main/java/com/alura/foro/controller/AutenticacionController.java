package com.alura.foro.controller;

import com.alura.foro.dto.AutentificacionUsuarioDto;
import com.alura.foro.dto.DatosJWTtokenDto;
import com.alura.foro.modelo.Usuario;
import com.alura.foro.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public AutenticacionController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticarUsuario (@RequestBody @Valid AutentificacionUsuarioDto autentificacionUsuarioDto) {

        Authentication tokenAuth = new UsernamePasswordAuthenticationToken(autentificacionUsuarioDto.nombreUsuario(),
                autentificacionUsuarioDto.clave());
        Authentication usuarioAutenticado = authenticationManager.authenticate(tokenAuth);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtokenDto(JWTtoken));
    }
}
