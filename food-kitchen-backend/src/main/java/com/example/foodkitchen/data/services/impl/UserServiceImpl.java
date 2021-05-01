package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.Role;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import com.example.foodkitchen.data.repositories.UserRepository;
import com.example.foodkitchen.data.services.RoleService;
import com.example.foodkitchen.data.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {

        User user = modelMapper.map(userServiceModel, User.class);

        if (user.getUsername() == null || user.getPassword() == null ||
            user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty()){
            return userServiceModel;
        }

        if (userRepository.count() == 0){

            roleService.seedRolesInDB();

            Set<Role> userRoles = roleService.findAdminUserRoles()
                    .stream()
                    .map(role -> modelMapper.map(role, Role.class))
                    .collect(Collectors.toSet());

            user.setAuthorities(userRoles);
        }

        else {

            Set<Role> userRoles = roleService.findPlainUserRoles()
                    .stream()
                    .map(role -> modelMapper.map(role, Role.class))
                    .collect(Collectors.toSet());

            user.setAuthorities(userRoles);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel addRecipe(String userUsername, Recipe recipe) {

        User user = userRepository.findByUsername(userUsername);

        if (user == null){
            return null;
        }

        if (user.getRecipes() == null){
            user.setRecipes(new HashSet<>(Set.of(recipe)));
        }

        else {
            user.getRecipes().add(recipe);
        }

        return modelMapper.map(userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserServiceModel findByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null){
            return null;
        }

        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUser(String oldUsername,
                                     String updateUsername,
                                     String principalPass,
                                     String oldPass, String updatePass) {

        User user = userRepository.findByUsername(oldUsername);

        if (user == null){
            return null;
        }

        boolean matches = passwordEncoder.matches(oldPass, principalPass);

        if (matches){
            user.setUsername(updateUsername);
            user.setPassword(passwordEncoder.encode(updatePass));
        }

        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserUsername(String oldUsername, String updateUsername) {

        User user = userRepository.findByUsername(oldUsername);

        if (user == null){
            return null;
        }

        user.setUsername(updateUsername);
        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserPassword(String username, String updatePassword) {

        User user = userRepository.findByUsername(username);

        if (user == null){
            return null;
        }

        user.setPassword(passwordEncoder.encode(updatePassword));
        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel editUserProfilePicture(String username, String updateAvatarImageUrl) {

        User user = userRepository.findByUsername(username);

        if (user == null){
            return null;
        }

        user.setAvatarImageUrl(updateAvatarImageUrl);
        return modelMapper.map(userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
