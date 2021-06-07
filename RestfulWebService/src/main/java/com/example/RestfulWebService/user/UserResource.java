package com.example.RestfulWebService.user;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource
{
    @Autowired
    private UserDaoService service;
    @GetMapping("/users")
    public List<User> retriveAllUsers()
    {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retriveUser(@PathVariable int id)
    {
        User user= service.findOne(id);
        if(user==null)
    //runtime exception
            throw new UserNotFoundException("id: "+ id);
        return user;
    }

    //method that delete a user resource
    //if the user deleted successfully it returns status 200 OK otherwise 404 Not Found
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        User user= service.deleteById(id);
        if(user==null)
            //runtime exception
            throw new UserNotFoundException("id: "+ id);
    }

    //method that posts a new user detail and returns the status of HTTP and location of the user resource
    //method that posts a new user detail and returns the status of the user resource
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user)
    {
        User sevedUser=service.save(user);
        URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sevedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
