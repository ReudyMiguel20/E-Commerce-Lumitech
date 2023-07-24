package com.lumitech.ecommerceapp.users.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    @NotEmpty
    @NotBlank
    private String token;

    @NotEmpty
    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    private String firstName;

    @NotEmpty
    @NotBlank
    private String lastName;

}
