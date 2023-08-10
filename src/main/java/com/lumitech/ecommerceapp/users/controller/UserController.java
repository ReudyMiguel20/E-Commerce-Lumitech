package com.lumitech.ecommerceapp.users.controller;

import com.lumitech.ecommerceapp.common.dto.StatusMessage;
import com.lumitech.ecommerceapp.users.model.dto.UserNewPasswordDTO;
import com.lumitech.ecommerceapp.users.model.entity.User;
import com.lumitech.ecommerceapp.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/password")
    public ResponseEntity<StatusMessage> changeUserPassword(Authentication auth, @Valid @RequestBody UserNewPasswordDTO userNewPasswordDTO) {
        User userToChangePassword = userService.findByEmail(auth.getName()).get();

        StatusMessage statusMessage = userService.changeUserPassword(userToChangePassword, userNewPasswordDTO);
        return ResponseEntity.ok(statusMessage);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusMessage> deleteUser(@PathVariable long id) {
        StatusMessage statusMessage = userService.deleteUserById(id);

        return ResponseEntity.ok(statusMessage);
    }


}
