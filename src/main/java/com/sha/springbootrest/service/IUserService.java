package com.sha.springbootrest.service;

import com.sha.springbootrest.model.Role;
import com.sha.springbootrest.model.User;

import java.util.List;

/**
 * @author sa
 * @date 2020-04-11
 * @time 11:40
 */
public interface IUserService
{
    User saveUser(User user);

    User changeRole(Role newRole, String username);

    User findByUsername(String username);

    User deleteUser(Long userId);

    List<User> findAllUsers();
}
