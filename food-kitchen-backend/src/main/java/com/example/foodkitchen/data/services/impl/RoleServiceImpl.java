package com.example.foodkitchen.data.services.impl;

import com.example.foodkitchen.data.entities.Role;
import com.example.foodkitchen.data.models.service.RoleServiceModel;
import com.example.foodkitchen.data.repositories.RoleRepository;
import com.example.foodkitchen.data.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<RoleServiceModel> findPlainUserRoles() {

        RoleServiceModel userRole = modelMapper.map(roleRepository.findByAuthority("USER"), RoleServiceModel.class);
        return Set.of(userRole);
    }

    @Override
    public Set<RoleServiceModel> findAdminUserRoles() {

        RoleServiceModel userRole = modelMapper.map(roleRepository.findByAuthority("USER"), RoleServiceModel.class);
        RoleServiceModel adminRole = modelMapper.map(roleRepository.findByAuthority("ADMIN"), RoleServiceModel.class);

        return Set.of(userRole, adminRole);
    }

    @Override
    public void seedRolesInDB() {

        roleRepository.saveAndFlush(new Role("USER"));
        roleRepository.saveAndFlush(new Role("ADMIN"));
    }
}
