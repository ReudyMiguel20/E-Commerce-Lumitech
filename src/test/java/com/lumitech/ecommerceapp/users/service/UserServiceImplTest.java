package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.authorities.model.entity.Authorities;
import com.lumitech.ecommerceapp.users.model.dto.UserDTO;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @AfterEach
    void tearDown() {
        userService.deleteAll();
    }

    @Test
    @DisplayName("Persisting a new user to the database")
    void addUserToDatabase() {
        //Arrange
        UserDTO userDTO = UserDTO.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        //Act
        User newUser = userService.createNewUserAssignRole(userDTO);

        //Assert
        Assertions.assertThat(userService.getAllUsers())
                .as("")
                .isNotEmpty();

        Assertions.assertThat(newUser)
                .as("")
                .isNotNull();

        Assertions.assertThat(newUser)
                .as("The object is not type 'User'")
                .isExactlyInstanceOf(User.class);

        Assertions.assertThat(newUser)
                .as("")
                .hasFieldOrPropertyWithValue("firstName", userDTO.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", userDTO.getLastName())
                .hasFieldOrPropertyWithValue("address", userDTO.getAddress())
                .hasFieldOrPropertyWithValue("email", userDTO.getEmail())
                .hasFieldOrPropertyWithValue("password", userDTO.getPassword());
    }

    @Test
    @DisplayName("Creating new user which already exists")
    void creatingNewUserThatAlreadyExists() {
        //Arrange
        UserDTO firstUserDTO = UserDTO.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        UserDTO secondUserDTO = UserDTO.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("mikecarmine@hotmail.com")
                .password("catchmeifucan")
                .build();

        User firstUser = userService.createNewUserAssignRole(firstUserDTO);
        User secondUser = userService.createNewUserAssignRole(secondUserDTO);

        //Assert
        Assertions.assertThat(userService.getAllUsers().size())
                .as("Expected the size list of users to be 2.")
                .isEqualTo(2);

        Assertions.assertThat(firstUser)
                .as("First user is null")
                .isNotNull();

        Assertions.assertThat(secondUser)
                .as("sECOND user is null.")
                .isNotNull();

        Assertions.assertThat(firstUser)
                .as("The firstUser object is not type 'User'")
                .isExactlyInstanceOf(User.class);

        Assertions.assertThat(secondUser)
                .as("The secondUser object is not type 'User'")
                .isExactlyInstanceOf(User.class);

        Assertions.assertThat(firstUser.getEmail())
                .as("There's already a user with this email")
                .isNotEqualTo(secondUser.getEmail());
    }

    @Test
    @DisplayName("Assigning Admin and Customer Roles to Two Users")
    void assigningRolesToTwoCreatedUsers() {
        //Arrange
        UserDTO firstUserDTO = UserDTO.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        UserDTO secondUserDTO = UserDTO.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("mikecarmine@hotmail.com")
                .password("catchmeifucan")
                .build();

        User firstUser = userService.createNewUserAssignRole(firstUserDTO);
        User secondUser = userService.createNewUserAssignRole(secondUserDTO);

        //Assert
        Assertions.assertThat(userService.getAllUsers().size())
                .as("Expected the size list of users to be 2.")
                .isEqualTo(2);

        Assertions.assertThat(firstUser)
                .as("First user is null.")
                .isNotNull();

        Assertions.assertThat(secondUser)
                .as("Second user is null.")
                .isNotNull();

        Assertions.assertThat(firstUser)
                .as("The object is not type 'User'")
                .isExactlyInstanceOf(User.class);

        Assertions.assertThat(secondUser)
                .as("The object is not type 'User'")
                .isExactlyInstanceOf(User.class);

        Assertions.assertThat(firstUser.getRoles().get(0).getRole().toString())
                .as("First user created should have Admin Role")
                .isEqualTo("ROLE_ADMIN");

        Assertions.assertThat(secondUser.getRoles().get(0).getRole().toString())
                .as("From the second user onwards, the role should be Customer")
                .isEqualTo("ROLE_CUSTOMER");
    }
}
