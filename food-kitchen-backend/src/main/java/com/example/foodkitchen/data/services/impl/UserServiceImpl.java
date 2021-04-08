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
        return modelMapper.map(userRepository.findByUsername(username), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
