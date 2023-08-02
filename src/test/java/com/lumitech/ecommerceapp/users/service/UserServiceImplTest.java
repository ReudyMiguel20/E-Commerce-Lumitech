package com.lumitech.ecommerceapp.users.service;

import com.lumitech.ecommerceapp.users.model.dto.RegisterRequest;
import com.lumitech.ecommerceapp.users.model.dto.UserNewPasswordDTO;
import com.lumitech.ecommerceapp.users.model.entity.Role;
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
        User newUser = userService.createNewUserAssignRoleAndCart(registerRequest);

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
                .as("Some fields are invalid")
                .hasFieldOrPropertyWithValue("firstName", registerRequest.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", registerRequest.getLastName())
                .hasFieldOrPropertyWithValue("address", registerRequest.getAddress())
                .hasFieldOrPropertyWithValue("email", registerRequest.getEmail())
                .hasFieldOrPropertyWithValue("role", Role.ADMIN)
                .hasFieldOrProperty("password");

        Assertions.assertThat(newUser.getPassword())
                .as("The password should be BCrypt encrypted with a strength of 12 \"$2a$12$\"")
                .startsWith("$2a$12$");
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

        User firstUser = userService.createNewUserAssignRoleAndCart(firstRegisterRequest);
        User secondUser = userService.createNewUserAssignRoleAndCart(secondRegisterRequest);

        //Assert
        Assertions.assertThat(userService.getAllUsers().size())
                .as("Expected the size list of users to be 2.")
                .isEqualTo(2);

        Assertions.assertThat(firstUser)
                .as("First user is null")
                .isNotNull();

        Assertions.assertThat(secondUser)
                .as("Second user is null.")
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

        User firstUser = userService.createNewUserAssignRoleAndCart(firstRegisterRequest);
        User secondUser = userService.createNewUserAssignRoleAndCart(secondRegisterRequest);

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

        Assertions.assertThat(firstUser.getRole().toString())
                .as("First user created should have Admin Role")
                .isEqualTo("ADMIN");

        Assertions.assertThat(secondUser.getRole().toString())
                .as("From the second user onwards, the role should be Customer")
                .isEqualTo("CUSTOMER");
    }

//    @Test
//    @DisplayName("Assigning Admin and Customer Roles to Two Users")
//    void changingOldPassword() {
//        //Arrange
//        RegisterRequest firstRegisterRequest = RegisterRequest.builder()
//                .firstName("Mike")
//                .lastName("Carmine")
//                .address("The green street #56")
//                .email("google@hotmail.com")
//                .password("catchmeifucan")
//                .build();
//
//        User firstUser = userService.createNewUserAssignRoleAndCart(firstRegisterRequest);
//
//        UserNewPasswordDTO userNewPassword = UserNewPasswordDTO.builder()
//                .currentPassword("catchmeifucan")
//                .newPassword("cantcatchme")
//                .build();
//
//        //Act
//        userService.changeUserPassword(firstUser, userNewPassword);
//
//        //Assert
//        Assertions.assertThat(userService.confirmOldPasswordIsCorrect(firstUser, userNewPassword))
//                .as("Old password is incorrect.")
//                .isEqualTo(true);
//    }
}
