package com.project.wsda.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    //private Integer id;

    @Size(min = 4, max = 45, message = "Username length must be between 4 and 45 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,45}$", message = " Username cannot contain spaces or special character except '_'")
    @NotEmpty(message = "Provide an username")
    private String username;

    @Size(min = 4, max = 20, message = "Password length must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9?!#@*+-]{4,20}$", message = "Password cannot contain spaces or special characters except '_' '?' '!' '@' '+' '#' '-' '*'")
    @NotEmpty(message = "Provide a password")
    private String password;

    @NotEmpty(message = "Select a role")
    @Pattern(regexp = "(shop|company)")
    private String role;

}
