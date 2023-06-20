package com.example.carboncredit_backend.DaoImpl;

import com.example.carboncredit_backend.Dao.UserDao;
import com.example.carboncredit_backend.Entity.User;
import com.example.carboncredit_backend.Repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    public User check(String user_name) {
        return userRepository.findByUsername(user_name);
    }

    public int create(String user_name, String password, int age, String image, String email) {
        User one = userRepository.findByUsername(user_name);
        if (one != null) {
            System.out.println("Meet a same user.");
            return 0;
        }
        User u = new User();
        u.setUsername(user_name);
        u.setPassword(password);
        u.setAge(age);
        u.setGender("male");
        u.setImage(image);
        u.setEmail(email);
        u.setCredit_amount(0);
        u.setExp(0);
        userRepository.save(u);
        return u.getUser_id();
    }

    @Override
    public User getById(int user_id) {
        return userRepository.findByUser_id(user_id);
    }

    @Override
    public User getByName(String username) {
        return userRepository.findByUsername(username);
    }
}
