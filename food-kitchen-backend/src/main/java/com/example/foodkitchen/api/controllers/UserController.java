package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.security.JwtProvider;
import com.example.foodkitchen.api.security.JwtResponse;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.user.UserRegisterModel;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import com.example.foodkitchen.data.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public UserController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserRegisterModel userRegisterModel){

        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
            return ResponseEntity.badRequest()
                    .body("Passwords do not match!");
        }

        if (userService.existsByUsername(userRegisterModel.getUsername())){
            return ResponseEntity.badRequest()
                    .body("User with such username already exists!");
        }

        userService.register(modelMapper.map(userRegisterModel, UserServiceModel.class));
        return ResponseEntity.ok().body("User is registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User userDetails = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities(),
                userDetails.getAvatarImageUrl()));
    }

    @PostMapping("/verifyLogin")
    public Object verifyLogin(@RequestHeader String authorization){

        Object userDetails = jwtProvider.getUserDetails(authorization);
        return jwtProvider.getUserDetails(authorization);
    }

    @PatchMapping("/edit/username")
    public ResponseEntity<?> updateUsername(@RequestParam String updateUsername,
                                    @AuthenticationPrincipal User principal){

        UserServiceModel userServiceModel = userService.editUserUsername(principal.getUsername(), updateUsername);
        return ResponseEntity.ok().body(userServiceModel);
    }

    @PatchMapping("/edit/password")
    public ResponseEntity<?> updatePassword(@RequestParam String updatePassword,
                                    @AuthenticationPrincipal User principal) {

        UserServiceModel userServiceModel = userService.editUserPassword(principal.getUsername(), updatePassword);
        return ResponseEntity.ok().body(userServiceModel);
    }

    @PatchMapping("/edit/profilePicture")
    public ResponseEntity<?> updateProfilePicture(@RequestParam String updateAvatarImageUrl,
                                            @AuthenticationPrincipal User principal) {

        UserServiceModel userServiceModel = userService.editUserProfilePicture(principal.getUsername(), updateAvatarImageUrl);
        return ResponseEntity.ok().body(userServiceModel);
    }
}
