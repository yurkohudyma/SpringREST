package ua.hudyma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.hudyma.entity.User;
import ua.hudyma.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("rawtypes")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers (){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public ResponseEntity addUser (@RequestBody User user){
        user = userService.addUser(user);
        return ResponseEntity.created(URI.create("/" + user.getId())).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser (@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();

    }





}
