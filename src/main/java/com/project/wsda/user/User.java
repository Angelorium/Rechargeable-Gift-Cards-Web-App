package com.project.wsda.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(UserId.class)
@Getter
@Setter
public class User {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Id
    @Column(updatable = false, nullable = false)
    @Size(min = 4, max = 45, message = "Username length must be between 4 and 45 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,45}$", message = " Username cannot contain spaces or special character except '_'")
    private String username;

    @NotNull
    @Size(min = 4, max = 20, message = "Password length must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9?!#@*+-]{4,20}$", message = "Password cannot contain spaces or special characters except '_' '?' '!' '@' '+' '#' '-' '*'")
    private String password;

    @NotNull
    private Integer role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
