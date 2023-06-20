package com.example.carboncredit_backend.Controller;

import com.example.carboncredit_backend.Service.TravelRecordService;
import com.example.carboncredit_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Component
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TravelRecordService travelRecordService;

    @RequestMapping("/CheckUser")
    @ResponseBody
    public String check(@RequestBody Map map) {     //登录时检测用户名与密码
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        System.out.println(username);
        System.out.println(password);
        return userService.check(username, password);
    }

    @RequestMapping("/CreateUser")
    @ResponseBody
    public String create(@RequestBody Map map) {    //注册新用户
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        String tmp = String.valueOf(map.get("age"));
        int age = Integer.parseInt(tmp);
        String image = String.valueOf(map.get("image"));
        String email = String.valueOf(map.get("email"));
        int uid = userService.create(username, password, age, image, email);
        return String.valueOf(uid);
    }

    @RequestMapping("/AddBalance")
    @ResponseBody
    public String addBalance(@RequestBody Map map) {
        System.out.println(map);
        String tmp1 = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp1);
        String tmp2 = String.valueOf(map.get("type_id"));
        int type_id = Integer.parseInt(tmp2);
        String tmp3 = String.valueOf(map.get("distance"));
        int distance = Integer.parseInt(tmp3);
        String tmp4 = String.valueOf(map.get("duration"));
        int duration = Integer.parseInt(tmp4);
        int travelRecord_id = travelRecordService.addTravelRecord(user_id,  type_id,  distance, duration);
        if (userService.addBalance(user_id, type_id, distance))
            return "1";
        else
            return "0";
    }

    @RequestMapping("/GetUserInfo")
    @ResponseBody
    public String getUserInfo(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        return userService.getUserInfo(user_id);
    }

    @RequestMapping("/GetUserId")
    @ResponseBody
    public String getUserId(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("username"));
        return userService.getUserId(tmp);
    }

    @RequestMapping("/DecPoints")
    @ResponseBody
    public String decPoints(@RequestBody Map map) {
        String tmp = String.valueOf(map.get("user_id"));
        int user_id = Integer.parseInt(tmp);
        String tmp1 = String.valueOf(map.get("points"));
        int points = Integer.parseInt(tmp1);
        System.out.println(user_id);
        System.out.println(points);
        return userService.decPoints(user_id,points);
    }
}
