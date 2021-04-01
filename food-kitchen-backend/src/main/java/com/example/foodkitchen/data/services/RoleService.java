package com.example.foodkitchen.data.services;

import com.example.foodkitchen.data.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    Set<RoleServiceModel> findPlainUserRoles();

    Set<RoleServiceModel> findAdminUserRoles();

    void seedRolesInDB();
}
