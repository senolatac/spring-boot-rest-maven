package com.sha.springbootrest.controller;

import com.sha.springbootrest.dto.UserDto;
import com.sha.springbootrest.model.Role;
import com.sha.springbootrest.model.User;
import com.sha.springbootrest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * @author sa
 * @date 2020-04-11
 * @time 15:59
 */
@RestController
//So to reach it endpoints can be api/user/**
@RequestMapping("api/user")
public class UserController
{
    @Autowired
    private IUserService userService;

    //Example: POST http://localhost:8080/api/user -data {user form}
    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid UserDto user) //@Valid provides validation check
    {
        if (userService.findByUsername(user.getUsername()) != null)
        {
            //Username should be unique.
            return new ResponseEntity<>(HttpStatus.CONFLICT);//409
        }
        //Convert dto to model object then save with service.
        userService.saveUser(user.convertToUser());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //GET http://localhost:8080/api/user/login -> It should be same with security login path like described before.
    //This is used for logout path also. After logout -> Spring will redirect it to login path.
    @GetMapping("login")
    public ResponseEntity<?> login(HttpServletRequest request) //We can take it like also; Principal principal
    {
        //Authentication info will be stored on request by Spring Security.
        Principal principal = request.getUserPrincipal();
        if (principal == null || principal.getName() == null)
        {
            //Here is will be logout redirection also so consider it as OK. httpStatus -> No error.
            return new ResponseEntity<>(HttpStatus.OK);
        }
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //PUT http://localhost:8080/api/user/{username}/change/{role}
    @PutMapping("{username}/change/{role}") // This can be also POST, PATCH
    public ResponseEntity<?> changeRole(@PathVariable String username, @PathVariable Role role)
    {
        User user = userService.changeRole(role, username);
        return ResponseEntity.ok(user);
    }
}
