package com.lumitech.ecommerceapp.endpointstests.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.lumitech.ecommerceapp.auth.controller.AuthenticationController;
import com.lumitech.ecommerceapp.users.controller.UserController;
import com.lumitech.ecommerceapp.users.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets/auth")
public class UsersEndpointTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnJWTToken() throws Exception {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .address("123 Main St hehe")
                .email("johndoe@luv2code.com")
                .password("password12345")
                .cart(null)
                .build();

        // Convert the User object to JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        // Perform the POST request
        MvcResult result = this.mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andDo(document("register-user", //Name of the documentation section
                requestFields(
                        //Define request fields to be documented with fieldwithPath() method
                        fieldWithPath("firstName").description("First name of the user"),
                        fieldWithPath("lastName").description("Last name of the user"),
                        fieldWithPath("address").description("Address of the user"),
                        fieldWithPath("email").description("Email of the user"),
                        fieldWithPath("password").description("Password of the user")
                ),
                responseFields(
                        // Define the response fields to be documented with fieldWithPath() method
                        fieldWithPath("token").description("JWT Token")
                ))
                )
                .andReturn();

        // Extract JWT Token from response
        String responseContent = result.getResponse().getContentAsString();
        String jwtToken = JsonPath.read(responseContent, "$.token");

    }

}
