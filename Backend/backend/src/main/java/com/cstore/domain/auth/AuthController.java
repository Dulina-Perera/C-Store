package com.cstore.domain.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
    name = "Sign in & Sign up",
    description = "Provides controller methods for signing in & signing up."
)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
        method = "register",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Jwt.class)
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
    public ResponseEntity<Jwt> register(@RequestBody(required = true) RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
        method = "authenticate",
        responses = {
            @ApiResponse(
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Jwt.class)
                ),
                description = "Success",
                responseCode = "200"
            )
        },
        summary = "Signs in a user to the system."
    )
    @RequestMapping(
        method = RequestMethod.POST,
        path = "/authenticate"
    )
    public ResponseEntity<Jwt> authenticate(
        @RequestBody(required = true)
        AuthRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
