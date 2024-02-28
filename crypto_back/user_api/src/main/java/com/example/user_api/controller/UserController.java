package com.example.user_api.controller;

import com.example.user_api.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/websession")
public class UserController {


    @GetMapping("/test")
    public Mono<User> getSession(@RequestParam("email") String email, @RequestParam("password") String password, WebSession session){
        session.getAttributes().putIfAbsent("email", email);
        session.getAttributes().putIfAbsent("password", password);
        User user = new User();
        user.setEmail((String) session.getAttributes().get("email"));
        user.setPassword((String) session.getAttributes().get("password"));
        return  Mono.just(user);
    }
}
