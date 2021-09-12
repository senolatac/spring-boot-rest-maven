package com.sha.springbootrest.dto;

import com.sha.springbootrest.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author sa
 * @date 2020-04-11
 * @time 15:25
 */
@Getter //No need setters in here.
@NoArgsConstructor //new UserDto();
public class UserDto //Data Transfer Object
{
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public User convertToUser()
    {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
