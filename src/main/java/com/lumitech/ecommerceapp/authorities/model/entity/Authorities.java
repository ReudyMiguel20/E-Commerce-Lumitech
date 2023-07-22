package com.lumitech.ecommerceapp.authorities.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lumitech.ecommerceapp.users.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.annotation.processing.Generated;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities {

    public enum Roles {
        ROLE_CUSTOMER,
        ROLE_EMPLOYEE,
        ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;
}
