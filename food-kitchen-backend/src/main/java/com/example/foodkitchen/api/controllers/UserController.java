package com.example.foodkitchen.api.controllers;

import com.example.foodkitchen.api.payroll.UserModelAssembler;
import com.example.foodkitchen.api.security.JwtProvider;
import com.example.foodkitchen.api.security.JwtResponse;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.binding.user.UserRegisterModel;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import com.example.foodkitchen.data.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userModelAssembler = userModelAssembler;
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<UserServiceModel>> create(@RequestBody UserRegisterModel userRegisterModel){

        UserServiceModel userServiceModel = modelMapper.map(userRegisterModel, UserServiceModel.class);
        EntityModel<UserServiceModel> entityModel = userModelAssembler.toModel(userServiceModel);

        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())){
            return ResponseEntity.badRequest()
                    .body(entityModel);
        }

        if (userService.existsByUsername(userRegisterModel.getUsername())){
            return ResponseEntity.badRequest()
                    .body(entityModel);
        }

        return ResponseEntity.ok(
                userModelAssembler.toModel(userService.register(userServiceModel)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);

            User userDetails = (User) authentication.getPrincipal();

            return ResponseEntity.ok(EntityModel.of(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getAuthorities(),
                    userDetails.getAvatarImageUrl())));
        }

        catch (AuthenticationException e){
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/verifyLogin")
    public ResponseEntity<EntityModel<Object>> verifyLogin(@RequestHeader String authorization){
        return ResponseEntity.ok(EntityModel.of(jwtProvider.getUserDetails(authorization)));
    }

    @PatchMapping("/edit/username")
    public ResponseEntity<EntityModel<UserServiceModel>> updateUsername(@RequestParam String updateUsername,
                                    @AuthenticationPrincipal User principal){

        UserServiceModel userServiceModel = userService.editUserUsername(principal.getUsername(), updateUsername);
        return ResponseEntity.ok(userModelAssembler.toModel(userServiceModel));
    }

    @PatchMapping("/edit/password")
    public ResponseEntity<EntityModel<UserServiceModel>> updatePassword(@RequestParam String updatePassword,
                                    @AuthenticationPrincipal User principal) {

        UserServiceModel userServiceModel = userService.editUserPassword(principal.getUsername(), updatePassword);
        return ResponseEntity.ok(userModelAssembler.toModel(userServiceModel));
    }

    @PatchMapping("/edit/profilePicture")
    public ResponseEntity<EntityModel<UserServiceModel>> updateProfilePicture(@RequestParam String updateAvatarImageUrl,
                                            @AuthenticationPrincipal User principal) {

        UserServiceModel userServiceModel = userService.editUserProfilePicture(principal.getUsername(), updateAvatarImageUrl);
        return ResponseEntity.ok(userModelAssembler.toModel(userServiceModel));
    }
}
