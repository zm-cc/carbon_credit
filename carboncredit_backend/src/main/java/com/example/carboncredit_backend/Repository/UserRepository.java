package com.example.carboncredit_backend.Repository;

import com.example.carboncredit_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "select * from carbon_credits.users where users.username = :username")
    User findByUsername(@Param("username") String username);

    @Query(nativeQuery = true, value = "select * from carbon_credits.users where users.user_id = :user_id")
    User findByUser_id(@Param("user_id") int user_id);
}
