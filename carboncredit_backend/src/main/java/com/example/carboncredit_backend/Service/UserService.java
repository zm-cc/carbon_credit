package com.example.carboncredit_backend.Service;

public interface UserService {
    String check(String username, String password);
    int create(String user_name, String password,int age ,String email, String image_url);
    Boolean addBalance(int user_id, int type_id, int distance);
    String getUserInfo(int user_id);
    String getUserId(String username);
    String decPoints(int user_id,int points);
}
