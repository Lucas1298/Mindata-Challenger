package com.project.challenge.infrastructure.rest.controller.login;


import com.project.challenge.application.dto.UserDto;
import com.project.challenge.application.dto.ApiErrorResponse;
import com.project.challenge.application.dto.AuthRequestDto;
import com.project.challenge.application.services.JwtService;
import com.project.challenge.application.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authenticator")
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public void addNewUser(@RequestBody UserDto userInfo) {
        service.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token generado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    public String authenticateAndGetToken(@RequestBody AuthRequestDto authRequest) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}