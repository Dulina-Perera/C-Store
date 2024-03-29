package com.cstore.domain.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(
    name = "Sign in & Sign up",
    description = "Provides controller methods for signing in & signing up."
)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(
        method = "Register",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Registers a user to the system."
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/register"
    )
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody(required = true)
        RegistrationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
        method = "Authenticate",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Signs in a user to the system."
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/login"
    )
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody(required = true)
        AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @RequestMapping(
        method = RequestMethod.POST,
        path = "/refresh-token"
    )
    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
