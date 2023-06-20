package com.example.carboncredit_backend.Dao;

import com.example.carboncredit_backend.Entity.User;

public interface UserDao {
    User check(String user_name);
    int create(String user_name, String password, int age, String image, String email);
    User getById(int user_id);
    User getByName(String username);
}
