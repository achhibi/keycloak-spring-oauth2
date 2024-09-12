package org.sfeir.lab.keycloak.controller;

import jakarta.annotation.security.RolesAllowed;
import org.sfeir.lab.keycloak.service.HelloService;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("hello")
    public String sayHello(JwtAuthenticationToken auth) {
        return "Hello (%s)".formatted(helloService.getAuthenticatedName());
    }

    @RolesAllowed("admin")
    @GetMapping("admin")
    public String sayHelloToAdmin(JwtAuthenticationToken auth) {
        return "Hello %s (%s)".formatted(getUserName(auth), helloService.getAuthenticatedName());
    }

    @GetMapping("user")
    @RolesAllowed("user")
    public String sayHelloToUser(JwtAuthenticationToken auth) {
        return "Hello %s (%s)".formatted(getUserName(auth), helloService.getAuthenticatedName());
    }

    private static String getUserName(JwtAuthenticationToken auth) {
        return auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
    }

}