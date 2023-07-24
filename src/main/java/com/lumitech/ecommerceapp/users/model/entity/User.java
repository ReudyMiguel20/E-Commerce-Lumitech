package com.lumitech.ecommerceapp.users.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "firstname", "lastname", "address", "email"})
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First Name cannot be empty")
    @NotBlank(message = "First Name cannot be blank")
    @JsonProperty(value = "firstname")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be empty")
    @NotBlank(message = "Last Name cannot be blank")
    @JsonProperty(value = "lastname")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Address cannot be empty")
    @NotBlank(message = "Address cannot be blank")
    @JsonProperty(value = "address")
    @Size(min = 7, message = "Address must be at least 7 character long")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Email cannot be empty")
    @NotBlank(message = "Email cannot be blank")
    @Email(regexp = "^(?i)[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$")
    @Size(min = 7, message = "Email must be at least 7 character long")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 7, message = "Password must be at least 7 character long")
    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<Authorities> roles = new ArrayList<>();

    //Add a cart here

    //Add List of orders

    //This equal method doesn't compare the id of the object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(address, user.address) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, email, password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
