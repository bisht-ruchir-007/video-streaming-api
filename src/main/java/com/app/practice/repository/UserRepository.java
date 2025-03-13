package com.app.practice.repository;

import com.app.practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the User entity.
 * This interface extends JpaRepository to provide basic database operations such as
 * save, findById, delete, etc., for the User entity.
 * <p>
 * Author: Ruchir Bisht
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Custom query to find a user by their username.
     * <p>
     * This method retrieves a User entity from the database where the username matches
     * the provided username parameter. It returns an Optional<User> to handle the case
     * where the username does not exist in the database.
     *
     * @param username the username of the user to be retrieved.
     * @return an Optional<User> containing the user if found, or an empty Optional if not found.
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
