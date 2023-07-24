package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
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
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        //Act
        User newUser = userService.createNewUserAssignRole(registerRequest);

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
                .hasFieldOrPropertyWithValue("firstName", registerRequest.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", registerRequest.getLastName())
                .hasFieldOrPropertyWithValue("address", registerRequest.getAddress())
                .hasFieldOrPropertyWithValue("email", registerRequest.getEmail())
                .hasFieldOrPropertyWithValue("password", registerRequest.getPassword());
    }

    @Test
    @DisplayName("Creating new user which already exists")
    void creatingNewUserThatAlreadyExists() {
        //Arrange
        RegisterRequest firstRegisterRequest = RegisterRequest.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        RegisterRequest secondRegisterRequest = RegisterRequest.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("mikecarmine@hotmail.com")
                .password("catchmeifucan")
                .build();

        User firstUser = userService.createNewUserAssignRole(firstRegisterRequest);
        User secondUser = userService.createNewUserAssignRole(secondRegisterRequest);

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
        RegisterRequest firstRegisterRequest = RegisterRequest.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("google@hotmail.com")
                .password("catchmeifucan")
                .build();

        RegisterRequest secondRegisterRequest = RegisterRequest.builder()
                .firstName("Mike")
                .lastName("Carmine")
                .address("The green street #56")
                .email("mikecarmine@hotmail.com")
                .password("catchmeifucan")
                .build();

        User firstUser = userService.createNewUserAssignRole(firstRegisterRequest);
        User secondUser = userService.createNewUserAssignRole(secondRegisterRequest);

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
