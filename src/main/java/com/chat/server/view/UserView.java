package com.chat.server.view;

import com.chat.server.annotation.password.PasswordMatching;
import com.chat.server.model.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
@PasswordMatching(password = "password", confirmPassword = "confirmPassword")
public class UserView {

    private Long id;
    private Role role;

    @NotNull(message = "Username cannot be null.")
    @Size(min = 4, max = 20, message = "Size should be between 4 and 20 characters")
    private String username;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 4, max = 20, message = "Size should be between 4 and 20 characters.")
    private String password;

    @NotNull(message = "Confirm Password cannot be null.")
    @Size(min = 4, max = 20, message = "Size should be between 4 and 20 characters.")
    private String confirmPassword;

}
