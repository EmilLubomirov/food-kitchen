package com.example.foodkitchen.unit;

import com.example.foodkitchen.data.entities.Recipe;
import com.example.foodkitchen.data.entities.User;
import com.example.foodkitchen.data.models.service.RoleServiceModel;
import com.example.foodkitchen.data.models.service.UserServiceModel;
import com.example.foodkitchen.data.repositories.UserRepository;
import com.example.foodkitchen.data.services.RoleService;
import com.example.foodkitchen.data.services.UserService;
import com.example.foodkitchen.data.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceUnitTest {

    UserService userService;

    UserRepository userRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;

    List<User> users;

    RoleServiceModel userRole = new RoleServiceModel();
    RoleServiceModel adminRole = new RoleServiceModel();

    private static final String USER_USERNAME = "demoUsername";
    private static final String USER_UPDATE_USERNAME = "updateUsername";
    private static final String USER_PASSWORD = "demoPassword123";
    private static final String USER_UPDATE_PASSWORD = "userUpdatePass";
    private static final String USER_UPDATE_AVATAR_URL = "updateAvatarUrl";


    @BeforeEach
    public void init(){

        users = new ArrayList<>();
        modelMapper = new ModelMapper();

        userRepository = Mockito.mock(UserRepository.class);

        Mockito.when(userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        roleService = Mockito.mock(RoleService.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(passwordEncoder.matches(USER_PASSWORD, USER_PASSWORD))
                .thenReturn(true);

        Mockito.when(passwordEncoder.encode(USER_UPDATE_PASSWORD))
                .thenReturn(USER_UPDATE_PASSWORD);

        userService = new UserServiceImpl(userRepository, roleService, passwordEncoder, modelMapper);

        Mockito.when(roleService.findPlainUserRoles()).thenReturn(Set.of(userRole));
        Mockito.when(roleService.findAdminUserRoles()).thenReturn(Set.of(userRole, adminRole));
    }

    @Test
    public void register_onInvalidData_shouldReturnRecipe(){

        UserServiceModel created =  userService.register(new UserServiceModel());
        assertNull(created.getAuthorities());
    }

    @Test
    public void register_onNoUsers_shouldSetUserAdminRole(){

        UserServiceModel created =  userService.register(new UserServiceModel(){{
            setUsername(USER_USERNAME);
            setPassword(USER_PASSWORD);
        }});

        assertEquals(2, created.getAuthorities().size());
    }

    @Test
    public void register_onSomeUsers_shouldSetOnlyUserRole(){

        users.add(new User());

        Mockito.when(userRepository.count())
                .thenReturn(Long.valueOf(users.size()));

        UserServiceModel created =  userService.register(new UserServiceModel(){{
            setUsername(USER_USERNAME);
            setPassword(USER_PASSWORD);
        }});

        assertEquals(1, created.getAuthorities().size());
    }

    @Test
    public void addRecipe_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.addRecipe("invalidUsername", new Recipe()));
    }

    @Test
    public void addRecipe_onValidUsername_shouldAddIt(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertEquals(1, userService.addRecipe(USER_USERNAME, new Recipe()).getRecipes().size());
    }

    @Test
    public void findByUsername_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.findByUsername("invalidUsername"));
    }

    @Test
    public void editUser_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.editUser("invalidUsername", "updateUsername",
                USER_PASSWORD, "updatePass", "updatePass"));
    }

    @Test
    public void editUser_onValidUsername_shouldUpdateIt(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();

        UserServiceModel updated = userService.editUser(USER_USERNAME, USER_UPDATE_USERNAME,
                USER_PASSWORD, USER_PASSWORD, USER_UPDATE_PASSWORD);

        assertEquals(USER_UPDATE_USERNAME, updated.getUsername());
        assertEquals(USER_UPDATE_PASSWORD, updated.getPassword());
    }

    @Test
    public void editUserUsername_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.editUserUsername("INVALID_USERNAME", USER_UPDATE_USERNAME));
    }

    @Test
    public void editUserUsername_onValidUsername_shouldUpdateIt(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertEquals(USER_UPDATE_USERNAME,
                userService.editUserUsername(USER_USERNAME, USER_UPDATE_USERNAME).getUsername());
    }

    @Test
    public void editUserPassword_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.editUserPassword(
                "INVALID_USERNAME", "1234", USER_UPDATE_PASSWORD));
    }

    @Test
    public void editUserPassword_onValidUsername_shouldUpdateIt(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertEquals(USER_UPDATE_PASSWORD,
                userService.editUserPassword(USER_USERNAME, "1234",
                        USER_UPDATE_PASSWORD).getPassword());
    }

    @Test
    public void editUserProfilePicture_onInvalidUsername_shouldReturnNull(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNull(userService.editUserProfilePicture("INVALID_USERNAME", USER_UPDATE_AVATAR_URL));
    }

    @Test
    public void editUserProfilePicture_onValidUsername_shouldUpdateIt(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertEquals(USER_UPDATE_AVATAR_URL,
                userService.editUserProfilePicture(USER_USERNAME, USER_UPDATE_AVATAR_URL).getAvatarImageUrl());
    }

    @Test
    public void findByUsername_onExistingUsername_shouldReturnUser(){

        users.add(new User(){{
            setUsername(USER_USERNAME);
        }});

        mockUserFindByUsername();
        assertNotNull(userService.findByUsername(USER_USERNAME));
    }

    private void mockUserFindByUsername() {
        Mockito.when(userRepository.findByUsername(USER_USERNAME))
                .thenReturn(users.stream()
                        .filter(u -> u.getUsername().equals(USER_USERNAME))
                        .findFirst()
                        .orElse(null));
    }
}
