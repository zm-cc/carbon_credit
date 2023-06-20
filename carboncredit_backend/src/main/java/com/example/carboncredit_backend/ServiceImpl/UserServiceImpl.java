package com.example.carboncredit_backend.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.carboncredit_backend.Dao.CompanyDao;
import com.example.carboncredit_backend.Dao.CompanyTypeDao;
import com.example.carboncredit_backend.Dao.UserDao;
import com.example.carboncredit_backend.Entity.Company;
import com.example.carboncredit_backend.Entity.CompanyType;
import com.example.carboncredit_backend.Entity.User;
import com.example.carboncredit_backend.Repository.CompanyRepository;
import com.example.carboncredit_backend.Repository.UserRepository;
import com.example.carboncredit_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyTypeDao companyTypeDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public String check(String username, String password) {
        User u = userDao.check(username);
        if (u == null)
            return "1";    //找不到用户
        //Base64.Decoder decoder=Base64.getDecoder();
        //byte[] tmp=decoder.decode(u.getPassword());    //解密
        String pwd = u.getPassword();
        if (!Objects.equals(pwd, password))
            return "2";    //密码错误
        String email = u.getEmail();
        System.out.println(email);
        ArrayList<String> con = new ArrayList<>();
        con.add(String.valueOf(u.getUser_id()));    //用户id
        con.add(u.getUsername());    //用户名
        con.add(u.getImage());    //用户头像
        return JSON.toJSONString(con, SerializerFeature.BrowserCompatible);
    }

    public int create(String user_name, String password, int age, String email, String image_url) {
        return userDao.create(user_name, password, age, email, image_url);
    }

    public Boolean addBalance(int user_id, int type_id, int distance) {
        User u = userDao.getById(user_id);
        CompanyType companyType = companyTypeDao.getCompanyType(type_id);
        Company company = companyDao.getByType_id(type_id);
        float distance_rate = companyType.getDistance_rate();
        float credit_rate = companyType.getCredit_rate();
        if ((u == null) || (companyType == null))
            return false;    //找不到用户或交通工具
        int this_emission = (int) (distance * distance_rate);
        company.setTotal_emission(company.getTotal_emission() - this_emission);
        companyRepository.save(company);
        int balance = (int) (this_emission * credit_rate);
        u.setCredit_amount(u.getCredit_amount() + balance);
        u.setExp(u.getExp() + balance);
        userRepository.save(u);
        return true;
    }

    public String getUserInfo(int user_id) {
        User u = userDao.getById(user_id);
        if (u == null) {
            return "1";//找不到用户
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(String.valueOf(u.getUser_id()));
        arrayList.add(String.valueOf(u.getUsername()));
        arrayList.add(String.valueOf(u.getAge()));
        arrayList.add(String.valueOf(u.getGender()));
        arrayList.add(String.valueOf(u.getEmail()));
        arrayList.add(String.valueOf(u.getCredit_amount()));
        arrayList.add(String.valueOf(u.getExp()));
        return JSON.toJSONString(arrayList, SerializerFeature.BrowserCompatible);
    }

    public String getUserId(String username) {
        User u = userDao.getByName(username);
        if (u == null) {
            return "0";//找不到用户
        }
        return String.valueOf(u.getUser_id());
    }

    public String decPoints(int user_id, int points) {
        User u = userDao.getById(user_id);
        int tmp = u.getCredit_amount();
        u.setCredit_amount(tmp - points);
        userRepository.save(u);
        return "1";
    }
}
