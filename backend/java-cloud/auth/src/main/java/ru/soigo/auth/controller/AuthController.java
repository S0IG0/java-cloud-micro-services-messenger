package ru.soigo.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.soigo.auth.dto.request.CredentialRequest;
import ru.soigo.auth.dto.request.RefreshTokenRequest;
import ru.soigo.auth.dto.request.UserRequest;
import ru.soigo.auth.jwt.dto.PairToken;
import ru.soigo.auth.model.User;
import ru.soigo.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;
    final ModelMapper mapper;

    @PostMapping("register")
    ResponseEntity<PairToken> register(@RequestBody @Valid @NotNull UserRequest userRequest) {
        User user = mapper.map(userRequest, User.class);
        PairToken pairToken = authService.register(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pairToken);
    }

    @PostMapping("login")
    ResponseEntity<PairToken> login(@RequestBody @Valid @NotNull CredentialRequest credentialRequest) {
        PairToken pairToken = authService.login(credentialRequest.getUsername(), credentialRequest.getPassword());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pairToken);
    }

    @PostMapping("refresh-pair-token")
    ResponseEntity<PairToken> refreshPairToken(@RequestBody @Valid @NotNull RefreshTokenRequest refreshTokenRequest) {
        PairToken pairToken = authService.updatePairTokenByRefreshToken(refreshTokenRequest.getRefresh());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pairToken);
    }
}
