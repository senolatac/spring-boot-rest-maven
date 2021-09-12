package com.sha.springbootrest.repository;

import com.sha.springbootrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author sa
 * @date 2020-04-05
 * @time 18:07
 */
public interface IUserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUsername(String username); //findBy + fieldName
}
