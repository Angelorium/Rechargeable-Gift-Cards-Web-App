package com.project.wsda.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 4, max = 45, message = "Username length must be between 4 and 45 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,45}$", message = " Username cannot contain spaces or special character except '_'")
    private String username;

    @NotNull
    @Size(min = 4, max = 20, message = "Password length must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9?!#@*+-]{4,20}$", message = "Password cannot contain spaces or special characters except '_' '?' '!' '@' '+' '#' '-' '*'")
    private String password;

    @NotNull
    private Integer role;
}
